package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class DepartamentoDTOAssembler implements DTOAssembler<DepartamentoDominio, DepartamentoDTO> {

	// Patron Singleton
	private static DTOAssembler<DepartamentoDominio, DepartamentoDTO> INSTANCE;

	private DepartamentoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<DepartamentoDominio, DepartamentoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new DepartamentoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public DepartamentoDominio ensamblarDominio(final DepartamentoDTO dto) {
		var departamentoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new DepartamentoDTO.Builder().build());
		return new DepartamentoDominio.Builder()
				.id(departamentoAEnsamblar.getId())
				.nombre(departamentoAEnsamblar.getNombre())
				.pais(PaisDTOAssembler.getInstance().ensamblarDominio(departamentoAEnsamblar.getPais()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public DepartamentoDTO ensamblarDTO(final DepartamentoDominio dominio) {
		var departamentoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new DepartamentoDominio.Builder().build());
		return new DepartamentoDTO.Builder()
				.id(departamentoAEnsamblar.getId())
				.nombre(departamentoAEnsamblar.getNombre())
				.pais(PaisDTOAssembler.getInstance().ensamblarDTO(departamentoAEnsamblar.getPais()))
				.build();
	}

}