package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class ZonaParqueaderoDTOAssembler implements DTOAssembler<ZonaParqueaderoDominio, ZonaParqueaderoDTO> {

	// Patron Singleton
	private static DTOAssembler<ZonaParqueaderoDominio, ZonaParqueaderoDTO> INSTANCE;

	private ZonaParqueaderoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<ZonaParqueaderoDominio, ZonaParqueaderoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new ZonaParqueaderoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public ZonaParqueaderoDominio ensamblarDominio(final ZonaParqueaderoDTO dto) {
		var zonaParqueaderoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ZonaParqueaderoDTO.Builder().build());
		return new ZonaParqueaderoDominio.Builder()
				.id(zonaParqueaderoAEnsamblar.getId())
				.nombreZona(zonaParqueaderoAEnsamblar.getNombreZona())
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDominio(zonaParqueaderoAEnsamblar.getParqueadero()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public ZonaParqueaderoDTO ensamblarDTO(final ZonaParqueaderoDominio dominio) {
		var zonaParqueaderoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ZonaParqueaderoDominio.Builder().build());
		return new ZonaParqueaderoDTO.Builder()
				.id(zonaParqueaderoAEnsamblar.getId())
				.nombreZona(zonaParqueaderoAEnsamblar.getNombreZona())
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDTO(zonaParqueaderoAEnsamblar.getParqueadero()))
				.build();
	}

}