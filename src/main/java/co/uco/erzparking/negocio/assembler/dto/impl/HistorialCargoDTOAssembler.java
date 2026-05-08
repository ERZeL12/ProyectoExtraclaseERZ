package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.HistorialCargoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.HistorialCargoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class HistorialCargoDTOAssembler implements DTOAssembler<HistorialCargoDominio, HistorialCargoDTO> {

	// Patron Singleton
	private static DTOAssembler<HistorialCargoDominio, HistorialCargoDTO> INSTANCE;

	private HistorialCargoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<HistorialCargoDominio, HistorialCargoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new HistorialCargoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public HistorialCargoDominio ensamblarDominio(final HistorialCargoDTO dto) {
		var historialCargoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new HistorialCargoDTO.Builder().build());
		return new HistorialCargoDominio.Builder()
				.id(historialCargoAEnsamblar.getId())
				.operario(OperarioDTOAssembler.getInstance().ensamblarDominio(historialCargoAEnsamblar.getOperario()))
				.cargo(CargoDTOAssembler.getInstance().ensamblarDominio(historialCargoAEnsamblar.getCargo()))
				.fechaInicioEjercicioCargo(historialCargoAEnsamblar.getFechaInicioEjercicioCargo())
				.fechaFinEjercicioCargo(historialCargoAEnsamblar.getFechaFinEjercicioCargo())
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public HistorialCargoDTO ensamblarDTO(final HistorialCargoDominio dominio) {
		var historialCargoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new HistorialCargoDominio.Builder().build());
		return new HistorialCargoDTO.Builder()
				.id(historialCargoAEnsamblar.getId())
				.operario(OperarioDTOAssembler.getInstance().ensamblarDTO(historialCargoAEnsamblar.getOperario()))
				.cargo(CargoDTOAssembler.getInstance().ensamblarDTO(historialCargoAEnsamblar.getCargo()))
				.fechaInicioEjercicioCargo(historialCargoAEnsamblar.getFechaInicioEjercicioCargo())
				.fechaFinEjercicioCargo(historialCargoAEnsamblar.getFechaFinEjercicioCargo())
				.build();
	}

}