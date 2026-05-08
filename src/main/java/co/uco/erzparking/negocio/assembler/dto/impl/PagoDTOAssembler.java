package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.PagoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.PagoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class PagoDTOAssembler implements DTOAssembler<PagoDominio, PagoDTO> {

	private static DTOAssembler<PagoDominio, PagoDTO> INSTANCE;

	private PagoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<PagoDominio, PagoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new PagoDTOAssembler();
		}
		return INSTANCE;
	}

	@Override
	public PagoDominio ensamblarDominio(final PagoDTO dto) {
		var pagoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new PagoDTO.Builder().build());
		return new PagoDominio.Builder()
				.id(pagoAEnsamblar.getId())
				.montoTotal(pagoAEnsamblar.getMontoTotal())
				.fechaHoraPago(pagoAEnsamblar.getFechaHoraPago())
				.metodoPago(MetodoPagoDTOAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getMetodoPago()))
				.salida(SalidaDTOAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getSalida()))
				.operario(OperarioDTOAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getOperario()))
				.tarifa(TarifaDTOAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getTarifa()))
				.atencionVehiculo(AtencionVehiculoDTOAssembler.getInstance().ensamblarDominio(pagoAEnsamblar.getAtencionVehiculo()))
				.build();
	}

	@Override
	public PagoDTO ensamblarDTO(final PagoDominio dominio) {
		var pagoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio.Builder().build());
		return new PagoDTO.Builder()
				.id(pagoAEnsamblar.getId())
				.montoTotal(pagoAEnsamblar.getMontoTotal())
				.fechaHoraPago(pagoAEnsamblar.getFechaHoraPago())
				.metodoPago(MetodoPagoDTOAssembler.getInstance().ensamblarDTO(pagoAEnsamblar.getMetodoPago()))
				.salida(SalidaDTOAssembler.getInstance().ensamblarDTO(pagoAEnsamblar.getSalida()))
				.operario(OperarioDTOAssembler.getInstance().ensamblarDTO(pagoAEnsamblar.getOperario()))
				.tarifa(TarifaDTOAssembler.getInstance().ensamblarDTO(pagoAEnsamblar.getTarifa()))
				.atencionVehiculo(AtencionVehiculoDTOAssembler.getInstance().ensamblarDTO(pagoAEnsamblar.getAtencionVehiculo()))
				.build();
	}

}
