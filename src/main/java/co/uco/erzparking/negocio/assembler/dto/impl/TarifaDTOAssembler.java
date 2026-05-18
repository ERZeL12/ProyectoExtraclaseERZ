package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class TarifaDTOAssembler implements DTOAssembler<TarifaDominio, TarifaDTO> {

	// Patron Singleton
	private static DTOAssembler<TarifaDominio, TarifaDTO> INSTANCE;

	private TarifaDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<TarifaDominio, TarifaDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new TarifaDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public TarifaDominio ensamblarDominio(final TarifaDTO dto) {
		var tarifaAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new TarifaDTO.Builder().build());
		return new TarifaDominio.Builder()
				.id(tarifaAEnsamblar.getId())
				.valorServicio(tarifaAEnsamblar.getValorServicio())
				.fechaInicioVigenciaTarifa(tarifaAEnsamblar.getFechaInicioVigenciaTarifa())
				.fechaFinVigenciaTarifa(tarifaAEnsamblar.getFechaFinVigenciaTarifa())
				.tipoVehiculo(TipoVehiculoDTOAssembler.getInstance().ensamblarDominio(tarifaAEnsamblar.getTipoVehiculo()))
				.servicio(ServicioDTOAssembler.getInstance().ensamblarDominio(tarifaAEnsamblar.getServicio()))
				.estadoActual(tarifaAEnsamblar.isEstadoActual())
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public TarifaDTO ensamblarDTO(final TarifaDominio dominio) {
		var tarifaAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new TarifaDominio.Builder().build());
		return new TarifaDTO.Builder()
				.id(tarifaAEnsamblar.getId())
				.valorServicio(tarifaAEnsamblar.getValorServicio())
				.fechaInicioVigenciaTarifa(tarifaAEnsamblar.getFechaInicioVigenciaTarifa())
				.fechaFinVigenciaTarifa(tarifaAEnsamblar.getFechaFinVigenciaTarifa())
				.tipoVehiculo(TipoVehiculoDTOAssembler.getInstance().ensamblarDTO(tarifaAEnsamblar.getTipoVehiculo()))
				.servicio(ServicioDTOAssembler.getInstance().ensamblarDTO(tarifaAEnsamblar.getServicio()))
				.estadoActual(tarifaAEnsamblar.isEstadoActual())
				.build();
	}

}