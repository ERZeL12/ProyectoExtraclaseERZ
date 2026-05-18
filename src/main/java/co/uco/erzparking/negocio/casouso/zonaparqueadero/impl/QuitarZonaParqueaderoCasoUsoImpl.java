package co.uco.erzparking.negocio.casouso.zonaparqueadero.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.ZonaParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.QuitarZonaParqueaderoCasoUso;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarZonaParqueaderoCasoUsoImpl implements QuitarZonaParqueaderoCasoUso {

	private DAOFactory daoFactory;

	public QuitarZonaParqueaderoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ZonaParqueaderoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final ZonaParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del zonaParqueadero son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del zonaParqueadero es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getZonaParqueaderoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El zonaParqueadero no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var zona = new ZonaParqueaderoEntidad.Builder().id(id).build();
		var espacios = daoFactory.getEspacioFisicoDAO().consultarPorFiltro(new EspacioFisicoEntidad.Builder().zonaEspacioFisico(zona).build());
		if (!espacios.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar la zona porque tiene espacios fisicos asociados");
		}
	}

	private void quitar(final ZonaParqueaderoDominio datos) {
		daoFactory.getZonaParqueaderoDAO().eliminar(datos.getId());
	}
}
