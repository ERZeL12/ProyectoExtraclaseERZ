package co.uco.erzparking.negocio.casouso.operario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.negocio.casouso.operario.ActualizarOperarioCasoUso;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarOperarioCasoUsoImpl implements ActualizarOperarioCasoUso {

	private DAOFactory daoFactory;

	public ActualizarOperarioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final OperarioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final OperarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del operario son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del operario es obligatorio para actualizar");
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
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getOperarioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El operario que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final OperarioDominio datos) {
		var cargo = new CargoEntidad.Builder()
				.id(datos.getCargo().getId())
				.build();
		var entidad = new OperarioEntidad.Builder()
				.id(datos.getId())
				.primerNombre(datos.getPrimerNombre())
				.segundoNombre(datos.getSegundoNombre())
				.primerApellido(datos.getPrimerApellido())
				.segundoApellido(datos.getSegundoApellido())
				.numeroTelefonico(datos.getNumeroTelefonico())
				.cargo(cargo)
				.build();
		daoFactory.getOperarioDAO().actualizar(datos.getId(), entidad);
	}

}
