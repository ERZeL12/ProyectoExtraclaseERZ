package co.uco.erzparking.negocio.casouso.ciudad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.negocio.casouso.ciudad.ActualizarCiudadCasoUso;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarCiudadCasoUsoImpl implements ActualizarCiudadCasoUso {

	private DAOFactory daoFactory;

	public ActualizarCiudadCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final CiudadDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final CiudadDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos de la ciudad son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador de la ciudad es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getNombre()) || datos.getNombre().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre de la ciudad es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getCiudadDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("La ciudad que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final CiudadDominio datos) {
		var entidad = new CiudadEntidad.Builder()
				.id(datos.getId())
				.nombre(datos.getNombre())
				.build();
		daoFactory.getCiudadDAO().actualizar(datos.getId(), entidad);
	}
}
