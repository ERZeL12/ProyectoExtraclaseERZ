package co.uco.erzparking.negocio.casouso.vehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.vehiculo.ConsultarVehiculoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarVehiculoPorIdCasoUsoImpl implements ConsultarVehiculoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarVehiculoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public VehiculoDominio ejecutar(final VehiculoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final VehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del vehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del vehiculo es obligatorio para consultar");
		}
	}

	private VehiculoDominio consultar(final VehiculoDominio datos) {
		var entidad = daoFactory.getVehiculoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El vehiculo no existe en el sistema");
		}
		var tipoVehiculo = !UtilObjeto.esNulo(entidad.getTipoVehiculo())
				? new TipoVehiculoDominio.Builder()
						.id(entidad.getTipoVehiculo().getId())
						.nombreVehiculo(entidad.getTipoVehiculo().getNombreVehiculo())
						.descripcion(entidad.getTipoVehiculo().getDescripcion())
						.build()
				: null;
		return new VehiculoDominio.Builder()
				.id(entidad.getId())
				.placaVehiculo(entidad.getPlacaVehiculo())
				.tipoVehiculo(tipoVehiculo)
				.build();
	}
}
