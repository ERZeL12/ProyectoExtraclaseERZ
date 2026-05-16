package co.uco.erzparking.negocio.casouso.parqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.parqueadero.ConsultarParqueaderoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarParqueaderoPorIdCasoUsoImpl implements ConsultarParqueaderoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarParqueaderoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public ParqueaderoDominio ejecutar(final ParqueaderoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final ParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del parqueadero son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del parqueadero es obligatorio para consultar");
		}
	}

	private ParqueaderoDominio consultar(final ParqueaderoDominio datos) {
		var entidad = daoFactory.getParqueaderoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El parqueadero no existe en el sistema");
		}
		var ciudad = !UtilObjeto.esNulo(entidad.getCiudad())
				? new CiudadDominio.Builder().id(entidad.getCiudad().getId()).build()
				: null;
		return new ParqueaderoDominio.Builder()
				.id(entidad.getId())
				.nombreEstablecimiento(entidad.getNombreEstablecimiento())
				.numeroTelefonico(entidad.getNumeroTelefonico())
				.correoElectronico(entidad.getCorreoElectronico())
				.direccionEstablecimiento(entidad.getDireccionEstablecimiento())
				.ciudad(ciudad)
				.build();
	}
}
