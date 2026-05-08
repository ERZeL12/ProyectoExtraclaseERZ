package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class EstadoEspacioFisicoDTOAssembler implements DTOAssembler<EstadoEspacioFisicoDominio, EstadoEspacioFisicoDTO> {

	// Patron Singleton
	private static DTOAssembler<EstadoEspacioFisicoDominio, EstadoEspacioFisicoDTO> INSTANCE;

	private EstadoEspacioFisicoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<EstadoEspacioFisicoDominio, EstadoEspacioFisicoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new EstadoEspacioFisicoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public EstadoEspacioFisicoDominio ensamblarDominio(final EstadoEspacioFisicoDTO dto) {
		var estadoEspacioFisicoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new EstadoEspacioFisicoDTO.Builder().build());
		return new EstadoEspacioFisicoDominio.Builder()
				.nombreEstadoEspacioFisico(estadoEspacioFisicoAEnsamblar.getNombreEstadoEspacioFisico())
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public EstadoEspacioFisicoDTO ensamblarDTO(final EstadoEspacioFisicoDominio dominio) {
		var estadoEspacioFisicoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EstadoEspacioFisicoDominio.Builder().build());
		return new EstadoEspacioFisicoDTO.Builder()
				.nombreEstadoEspacioFisico(estadoEspacioFisicoAEnsamblar.getNombreEstadoEspacioFisico())
				.build();
	}

}