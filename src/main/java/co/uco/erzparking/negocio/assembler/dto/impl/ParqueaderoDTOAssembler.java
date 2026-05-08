package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class ParqueaderoDTOAssembler implements DTOAssembler<ParqueaderoDominio, ParqueaderoDTO> {

	// Patron Singleton
	private static DTOAssembler<ParqueaderoDominio, ParqueaderoDTO> INSTANCE;

	private ParqueaderoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<ParqueaderoDominio, ParqueaderoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new ParqueaderoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public ParqueaderoDominio ensamblarDominio(final ParqueaderoDTO dto) {
		var parqueaderoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ParqueaderoDTO.Builder().build());
		return new ParqueaderoDominio.Builder()
				.id(parqueaderoAEnsamblar.getId())
				.nombreEstablecimiento(parqueaderoAEnsamblar.getNombreEstablecimiento())
				.numeroTelefonico(parqueaderoAEnsamblar.getNumeroTelefonico())
				.correoElectronico(parqueaderoAEnsamblar.getCorreoElectronico())
				.direccionEstablecimiento(parqueaderoAEnsamblar.getDireccionEstablecimiento())
				.ciudad(CiudadDTOAssembler.getInstance().ensamblarDominio(parqueaderoAEnsamblar.getCiudad()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public ParqueaderoDTO ensamblarDTO(final ParqueaderoDominio dominio) {
		var parqueaderoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ParqueaderoDominio.Builder().build());
		return new ParqueaderoDTO.Builder()
				.id(parqueaderoAEnsamblar.getId())
				.nombreEstablecimiento(parqueaderoAEnsamblar.getNombreEstablecimiento())
				.numeroTelefonico(parqueaderoAEnsamblar.getNumeroTelefonico())
				.correoElectronico(parqueaderoAEnsamblar.getCorreoElectronico())
				.direccionEstablecimiento(parqueaderoAEnsamblar.getDireccionEstablecimiento())
				.ciudad(CiudadDTOAssembler.getInstance().ensamblarDTO(parqueaderoAEnsamblar.getCiudad()))
				.build();
	}

}