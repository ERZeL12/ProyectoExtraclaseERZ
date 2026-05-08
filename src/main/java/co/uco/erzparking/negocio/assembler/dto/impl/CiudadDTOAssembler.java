package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class CiudadDTOAssembler implements DTOAssembler<CiudadDominio, CiudadDTO> {

	// Patron Singleton
	private static DTOAssembler<CiudadDominio, CiudadDTO> INSTANCE;

	private CiudadDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<CiudadDominio, CiudadDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new CiudadDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public CiudadDominio ensamblarDominio(final CiudadDTO dto) {
		var ciudadAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new CiudadDTO.Builder().build());
		return new CiudadDominio.Builder()
				.id(ciudadAEnsamblar.getId())
				.nombre(ciudadAEnsamblar.getNombre())
				.departamento(DepartamentoDTOAssembler.getInstance().ensamblarDominio(ciudadAEnsamblar.getDepartamento()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public CiudadDTO ensamblarDTO(final CiudadDominio dominio) {
		var ciudadAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CiudadDominio.Builder().build());
		return new CiudadDTO.Builder()
				.id(ciudadAEnsamblar.getId())
				.nombre(ciudadAEnsamblar.getNombre())
				.departamento(DepartamentoDTOAssembler.getInstance().ensamblarDTO(ciudadAEnsamblar.getDepartamento()))
				.build();
	}

}