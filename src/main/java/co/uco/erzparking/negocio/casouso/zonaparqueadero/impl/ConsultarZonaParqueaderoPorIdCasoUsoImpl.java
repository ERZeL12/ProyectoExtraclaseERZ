package co.uco.erzparking.negocio.casouso.zonaparqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.ConsultarZonaParqueaderoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarZonaParqueaderoPorIdCasoUsoImpl implements ConsultarZonaParqueaderoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarZonaParqueaderoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public ZonaParqueaderoDominio ejecutar(final ZonaParqueaderoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final ZonaParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del zonaParqueadero son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del zonaParqueadero es obligatorio para consultar");
		}
	}

	private ZonaParqueaderoDominio consultar(final ZonaParqueaderoDominio datos) {
		var entidad = daoFactory.getZonaParqueaderoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El zonaParqueadero no existe en el sistema");
		}
		var parqueadero = !UtilObjeto.esNulo(entidad.getParqueadero())
				? new ParqueaderoDominio.Builder().id(entidad.getParqueadero().getId()).build()
				: null;
		return new ZonaParqueaderoDominio.Builder()
				.id(entidad.getId())
				.nombreZona(entidad.getNombreZona())
				.parqueadero(parqueadero)
				.build();
	}
}
