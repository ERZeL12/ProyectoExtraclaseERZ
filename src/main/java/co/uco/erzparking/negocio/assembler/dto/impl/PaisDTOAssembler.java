package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class PaisDTOAssembler implements DTOAssembler<PaisDominio, PaisDTO> {

	// Patron Singleton
	private static DTOAssembler<PaisDominio, PaisDTO> INSTANCE;

	private PaisDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<PaisDominio, PaisDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new PaisDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public PaisDominio ensamblarDominio(final PaisDTO dto) {
		var paisAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new PaisDTO.Builder().build());
		return new PaisDominio.Builder()
				.id(paisAEnsamblar.getId())
				.nombre(paisAEnsamblar.getNombre())
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public PaisDTO ensamblarDTO(final PaisDominio dominio) {
		var paisAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PaisDominio.Builder().build());
		return new PaisDTO.Builder()
				.id(paisAEnsamblar.getId())
				.nombre(paisAEnsamblar.getNombre())
				.build();
	}

}