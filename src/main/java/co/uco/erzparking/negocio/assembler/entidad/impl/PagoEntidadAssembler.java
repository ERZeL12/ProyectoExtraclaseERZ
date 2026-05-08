package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.PagoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.PagoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class PagoEntidadAssembler implements EntidadAssembler<PagoDominio, PagoEntidad> {

	private static EntidadAssembler<PagoDominio, PagoEntidad> INSTANCE;

	private PagoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<PagoDominio, PagoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new PagoEntidadAssembler();
		}
		return INSTANCE;
	}

	@Override
	public PagoEntidad ensamblarEntidad(final PagoDominio dominio) {
		var pagoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio.Builder().build());
		return new PagoEntidad.Builder()
				.id(pagoAEnsamblar.getId())
				.montoTotal(pagoAEnsamblar.getMontoTotal())
				.fechaHoraPago(pagoAEnsamblar.getFechaHoraPago())
				.metodoPago(MetodoPagoEntidadAssembler.getInstance().ensamblarEntidad(pagoAEnsamblar.getMetodoPago()))
				.salida(SalidaEntidadAssembler.getInstance().ensamblarEntidad(pagoAEnsamblar.getSalida()))
				.operario(OperarioEntidadAssembler.getInstance().ensamblarEntidad(pagoAEnsamblar.getOperario()))
				.tarifa(TarifaEntidadAssembler.getInstance().ensamblarEntidad(pagoAEnsamblar.getTarifa()))
				.atencionVehiculo(AtencionVehiculoEntidadAssembler.getInstance().ensamblarEntidad(pagoAEnsamblar.getAtencionVehiculo()))
				.build();
	}

	@Override
	public PagoDominio ensamblarDominio(final PagoEntidad entidad) {
		var pagoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new PagoEntidad.Builder().build());
		return new PagoDominio.Builder()
				.id(pagoAEnsamblar.getId())
				.montoTotal(pagoAEnsamblar.getMontoTotal())
				.fechaHoraPago(pagoAEnsamblar.getFechaHoraPago())
				.metodoPago(MetodoPagoEntidadAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getMetodoPago()))
				.salida(SalidaEntidadAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getSalida()))
				.operario(OperarioEntidadAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getOperario()))
				.tarifa(TarifaEntidadAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getTarifa()))
				.atencionVehiculo(AtencionVehiculoEntidadAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getAtencionVehiculo()))
				.build();
	}

}
