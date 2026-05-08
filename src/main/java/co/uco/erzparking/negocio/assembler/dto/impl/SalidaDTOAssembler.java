package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.SalidaDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.SalidaDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class SalidaDTOAssembler implements DTOAssembler<SalidaDominio, SalidaDTO> {

	private static DTOAssembler<SalidaDominio, SalidaDTO> INSTANCE;

	private SalidaDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<SalidaDominio, SalidaDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new SalidaDTOAssembler();
		}
		return INSTANCE;
	}

	@Override
	public SalidaDominio ensamblarDominio(final SalidaDTO dto) {
		var salidaAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new SalidaDTO.Builder().build());
		return new SalidaDominio.Builder()
				.id(salidaAEnsamblar.getId())
				.fechaHoraSalida(salidaAEnsamblar.getFechaHoraSalida())
				.entrada(EntradaDTOAssembler.getInstance().ensamblarDominio(salidaAEnsamblar.getEntrada()))
				.build();
	}

	@Override
	public SalidaDTO ensamblarDTO(final SalidaDominio dominio) {
		var salidaAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new SalidaDominio.Builder().build());
		return new SalidaDTO.Builder()
				.id(salidaAEnsamblar.getId())
				.fechaHoraSalida(salidaAEnsamblar.getFechaHoraSalida())
				.entrada(EntradaDTOAssembler.getInstance().ensamblarDTO(salidaAEnsamblar.getEntrada()))
				.build();
	}

}
