package co.uco.erzparking.negocio.casouso.parqueadero.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.parqueadero.RegistrarParqueaderoCasoUso;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarParqueaderoCasoUsoImpl implements RegistrarParqueaderoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarParqueaderoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ParqueaderoDominio datos) {
		validarIntegridadDatos(datos);
		validarCiudadExiste(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final ParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del parqueadero son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getNombreEstablecimiento()) || datos.getNombreEstablecimiento().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del parqueadero es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getCiudad()) || UtilObjeto.esNulo(datos.getCiudad().getId())) {
			throw ERZParkingExcepcion.crear("La ciudad del parqueadero es obligatoria");
		}
	}

	private void validarCiudadExiste(final ParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getCiudadDAO().consultarPorId(datos.getCiudad().getId()))) {
			throw ERZParkingExcepcion.crear("La ciudad asociada al parqueadero no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getParqueaderoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final ParqueaderoDominio datos) {
		var ciudad = new CiudadEntidad.Builder()
				.id(datos.getCiudad().getId())
				.build();
		var entidad = new ParqueaderoEntidad.Builder()
				.id(generarIdUnico())
				.nombreEstablecimiento(datos.getNombreEstablecimiento())
				.numeroTelefonico(datos.getNumeroTelefonico())
				.correoElectronico(datos.getCorreoElectronico())
				.direccionEstablecimiento(datos.getDireccionEstablecimiento())
				.ciudad(ciudad)
				.build();
		daoFactory.getParqueaderoDAO().crear(entidad);
	}

}