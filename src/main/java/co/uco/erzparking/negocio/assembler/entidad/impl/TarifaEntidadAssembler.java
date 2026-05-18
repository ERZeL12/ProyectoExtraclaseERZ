package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.TarifaEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class TarifaEntidadAssembler implements EntidadAssembler<TarifaDominio, TarifaEntidad> {

	// Patron Singleton
	private static EntidadAssembler<TarifaDominio, TarifaEntidad> INSTANCE;

	private TarifaEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<TarifaDominio, TarifaEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new TarifaEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public TarifaEntidad ensamblarEntidad(final TarifaDominio dominio) {
		var tarifaAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new TarifaDominio.Builder().build());
		return new TarifaEntidad.Builder()
				.id(tarifaAEnsamblar.getId())
				.valorServicio(tarifaAEnsamblar.getValorServicio())
				.fechaInicioVigenciaTarifa(tarifaAEnsamblar.getFechaInicioVigenciaTarifa())
				.fechaFinVigenciaTarifa(tarifaAEnsamblar.getFechaFinVigenciaTarifa())
				.tipoVehiculo(TipoVehiculoEntidadAssembler.getInstance().ensamblarEntidad(tarifaAEnsamblar.getTipoVehiculo()))
				.servicio(ServicioEntidadAssembler.getInstance().ensamblarEntidad(tarifaAEnsamblar.getServicio()))
				.estadoActual(tarifaAEnsamblar.isEstadoActual())
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public TarifaDominio ensamblarDominio(final TarifaEntidad entidad) {
		var tarifaAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new TarifaEntidad.Builder().build());
		return new TarifaDominio.Builder()
				.id(tarifaAEnsamblar.getId())
				.valorServicio(tarifaAEnsamblar.getValorServicio())
				.fechaInicioVigenciaTarifa(tarifaAEnsamblar.getFechaInicioVigenciaTarifa())
				.fechaFinVigenciaTarifa(tarifaAEnsamblar.getFechaFinVigenciaTarifa())
				.tipoVehiculo(TipoVehiculoEntidadAssembler.getInstance().ensamblarDominio(tarifaAEnsamblar.getTipoVehiculo()))
				.servicio(ServicioEntidadAssembler.getInstance().ensamblarDominio(tarifaAEnsamblar.getServicio()))
				.estadoActual(tarifaAEnsamblar.isEstadoActual())
				.build();
	}

}