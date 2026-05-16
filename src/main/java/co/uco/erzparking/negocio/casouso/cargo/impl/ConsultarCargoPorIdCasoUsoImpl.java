package co.uco.erzparking.negocio.casouso.cargo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.cargo.ConsultarCargoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarCargoPorIdCasoUsoImpl implements ConsultarCargoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarCargoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public CargoDominio ejecutar(final CargoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final CargoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del cargo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del cargo es obligatorio para consultar");
		}
	}

	private CargoDominio consultar(final CargoDominio datos) {
		var entidad = daoFactory.getCargoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El cargo no existe en el sistema");
		}
		var parqueadero = new ParqueaderoDominio.Builder()
				.id(entidad.getParqueadero().getId())
				.nombreEstablecimiento(entidad.getParqueadero().getNombreEstablecimiento())
				.build();
		return new CargoDominio.Builder()
				.id(entidad.getId())
				.nombreCargo(entidad.getNombreCargo())
				.descripcion(entidad.getDescripcion())
				.parqueadero(parqueadero)
				.build();
	}

}
