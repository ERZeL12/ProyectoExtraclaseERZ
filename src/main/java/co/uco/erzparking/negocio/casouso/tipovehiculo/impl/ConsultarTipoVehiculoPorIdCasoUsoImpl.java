package co.uco.erzparking.negocio.casouso.tipovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.tipovehiculo.ConsultarTipoVehiculoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTipoVehiculoPorIdCasoUsoImpl implements ConsultarTipoVehiculoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTipoVehiculoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public TipoVehiculoDominio ejecutar(final TipoVehiculoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final TipoVehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipoVehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del tipoVehiculo es obligatorio para consultar");
		}
	}

	private TipoVehiculoDominio consultar(final TipoVehiculoDominio datos) {
		var entidad = daoFactory.getTipoVehiculoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El tipoVehiculo no existe en el sistema");
		}
		return new TipoVehiculoDominio.Builder()
				.id(entidad.getId())
				.nombreVehiculo(entidad.getNombreVehiculo())
				.build();
	}
}
