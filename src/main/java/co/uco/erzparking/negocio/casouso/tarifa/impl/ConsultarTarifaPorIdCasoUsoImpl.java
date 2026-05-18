package co.uco.erzparking.negocio.casouso.tarifa.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.tarifa.ConsultarTarifaPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTarifaPorIdCasoUsoImpl implements ConsultarTarifaPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTarifaPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public TarifaDominio ejecutar(final TarifaDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final TarifaDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tarifa son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del tarifa es obligatorio para consultar");
		}
	}

	private TarifaDominio consultar(final TarifaDominio datos) {
		var entidad = daoFactory.getTarifaDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El tarifa no existe en el sistema");
		}
		var tipoVehiculo = !UtilObjeto.esNulo(entidad.getTipoVehiculo())
				? new TipoVehiculoDominio.Builder()
						.id(entidad.getTipoVehiculo().getId())
						.nombreVehiculo(entidad.getTipoVehiculo().getNombreVehiculo())
						.descripcion(entidad.getTipoVehiculo().getDescripcion())
						.build()
				: null;
		var servicio = !UtilObjeto.esNulo(entidad.getServicio())
				? new ServicioDominio.Builder()
						.id(entidad.getServicio().getId())
						.nombreServicio(entidad.getServicio().getNombreServicio())
						.build()
				: null;
		return new TarifaDominio.Builder()
				.id(entidad.getId())
				.valorServicio(entidad.getValorServicio())
				.fechaInicioVigenciaTarifa(entidad.getFechaInicioVigenciaTarifa())
				.fechaFinVigenciaTarifa(entidad.getFechaFinVigenciaTarifa())
				.tipoVehiculo(tipoVehiculo)
				.servicio(servicio)
				.build();
	}
}
