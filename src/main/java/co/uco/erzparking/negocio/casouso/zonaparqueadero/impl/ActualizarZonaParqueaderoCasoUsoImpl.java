package co.uco.erzparking.negocio.casouso.zonaparqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ZonaParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.ActualizarZonaParqueaderoCasoUso;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarZonaParqueaderoCasoUsoImpl implements ActualizarZonaParqueaderoCasoUso {

	private DAOFactory daoFactory;

	public ActualizarZonaParqueaderoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ZonaParqueaderoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final ZonaParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos de la zona del parqueadero son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador de la zona es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getNombreZona()) || datos.getNombreZona().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre de la zona es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getZonaParqueaderoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("La zona del parqueadero que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final ZonaParqueaderoDominio datos) {
		var entidad = new ZonaParqueaderoEntidad.Builder()
				.id(datos.getId())
				.nombreZona(datos.getNombreZona())
				.build();
		daoFactory.getZonaParqueaderoDAO().actualizar(datos.getId(), entidad);
	}
}
