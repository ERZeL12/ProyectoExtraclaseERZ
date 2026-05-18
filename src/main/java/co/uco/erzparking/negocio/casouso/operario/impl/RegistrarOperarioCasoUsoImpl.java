package co.uco.erzparking.negocio.casouso.operario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.TipoDocumentoIdentificacionEntidad;
import co.uco.erzparking.negocio.casouso.operario.RegistrarOperarioCasoUso;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarOperarioCasoUsoImpl implements RegistrarOperarioCasoUso {

	private DAOFactory daoFactory;

	public RegistrarOperarioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final OperarioDominio datos) {
		validarIntegridadDatos(datos);
		validarDependencias(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final OperarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del operario son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getTipoDocumentoIdentificacion()) || UtilObjeto.esNulo(datos.getTipoDocumentoIdentificacion().getId())) {
			throw ERZParkingExcepcion.crear("El tipo de documento de identificacion del operario es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getNumeroIdentificacion()) || datos.getNumeroIdentificacion().isEmpty()) {
			throw ERZParkingExcepcion.crear("El numero de identificacion del operario es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getPrimerNombre()) || datos.getPrimerNombre().isEmpty()) {
			throw ERZParkingExcepcion.crear("El primer nombre del operario es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getPrimerApellido()) || datos.getPrimerApellido().isEmpty()) {
			throw ERZParkingExcepcion.crear("El primer apellido del operario es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getCargo()) || UtilObjeto.esNulo(datos.getCargo().getId())) {
			throw ERZParkingExcepcion.crear("El cargo del operario es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getParqueadero()) || UtilObjeto.esNulo(datos.getParqueadero().getId())) {
			throw ERZParkingExcepcion.crear("El parqueadero del operario es obligatorio");
		}
	}

	private void validarDependencias(final OperarioDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getTipoDocumentoIdentificacionDAO().consultarPorId(datos.getTipoDocumentoIdentificacion().getId()))) {
			throw ERZParkingExcepcion.crear("El tipo de documento de identificacion asociado no existe en el sistema");
		}
		var cargo = daoFactory.getCargoDAO().consultarPorId(datos.getCargo().getId());
		if (UtilObjeto.esNulo(cargo)) {
			throw ERZParkingExcepcion.crear("El cargo asociado no existe en el sistema");
		}
		if (!cargo.isEstadoActual()) {
			throw ERZParkingExcepcion.crear("El cargo debe estar activo para asignarlo al operario");
		}
		if (UtilObjeto.esNulo(daoFactory.getParqueaderoDAO().consultarPorId(datos.getParqueadero().getId()))) {
			throw ERZParkingExcepcion.crear("El parqueadero asociado no existe en el sistema");
		}
		if (!UtilObjeto.esNulo(daoFactory.getUsuarioDAO().consultarPorNumeroIdentificacion(datos.getNumeroIdentificacion()))) {
			throw ERZParkingExcepcion.crear("El numero de identificacion ya esta registrado como usuario en el sistema");
		}
		if (!UtilObjeto.esNulo(daoFactory.getOperarioDAO().consultarPorNumeroIdentificacion(datos.getNumeroIdentificacion()))) {
			throw ERZParkingExcepcion.crear("El numero de identificacion ya esta registrado como operario en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getOperarioDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final OperarioDominio datos) {
		var tipoDoc = new TipoDocumentoIdentificacionEntidad.Builder()
				.id(datos.getTipoDocumentoIdentificacion().getId())
				.build();
		var cargo = new CargoEntidad.Builder()
				.id(datos.getCargo().getId())
				.build();
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(datos.getParqueadero().getId())
				.build();
		var entidad = new OperarioEntidad.Builder()
				.id(generarIdUnico())
				.tipoDocumentoIdentificacion(tipoDoc)
				.numeroIdentificacion(datos.getNumeroIdentificacion())
				.primerNombre(datos.getPrimerNombre())
				.segundoNombre(datos.getSegundoNombre())
				.primerApellido(datos.getPrimerApellido())
				.segundoApellido(datos.getSegundoApellido())
				.numeroTelefonico(datos.getNumeroTelefonico())
				.cargo(cargo)
				.parqueadero(parqueadero)
				.build();
		daoFactory.getOperarioDAO().crear(entidad);
	}

}
