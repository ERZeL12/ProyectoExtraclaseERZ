package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class UsuarioDTOAssembler implements DTOAssembler<UsuarioDominio, UsuarioDTO> {

	// Patron Singleton
	private static DTOAssembler<UsuarioDominio, UsuarioDTO> INSTANCE;

	private UsuarioDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<UsuarioDominio, UsuarioDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new UsuarioDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public UsuarioDominio ensamblarDominio(final UsuarioDTO dto) {
		var usuarioAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new UsuarioDTO.Builder().build());
		return new UsuarioDominio.Builder()
				.id(usuarioAEnsamblar.getId())
				.tipoDocumentoIdentificacion(TipoDocumentoIdentificacionDTOAssembler.getInstance().ensamblarDominio(usuarioAEnsamblar.getTipoDocumentoIdentificacion()))
				.numeroIdentificacion(usuarioAEnsamblar.getNumeroIdentificacion())
				.primerNombre(usuarioAEnsamblar.getPrimerNombre())
				.segundoNombre(usuarioAEnsamblar.getSegundoNombre())
				.primerApellido(usuarioAEnsamblar.getPrimerApellido())
				.segundoApellido(usuarioAEnsamblar.getSegundoApellido())
				.numeroTelefonico(usuarioAEnsamblar.getNumeroTelefonico())
				.correoElectronico(usuarioAEnsamblar.getCorreoElectronico())
				.ciudad(CiudadDTOAssembler.getInstance().ensamblarDominio(usuarioAEnsamblar.getCiudad()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public UsuarioDTO ensamblarDTO(final UsuarioDominio dominio) {
		var usuarioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new UsuarioDominio.Builder().build());
		return new UsuarioDTO.Builder()
				.id(usuarioAEnsamblar.getId())
				.tipoDocumentoIdentificacion(TipoDocumentoIdentificacionDTOAssembler.getInstance().ensamblarDTO(usuarioAEnsamblar.getTipoDocumentoIdentificacion()))
				.numeroIdentificacion(usuarioAEnsamblar.getNumeroIdentificacion())
				.primerNombre(usuarioAEnsamblar.getPrimerNombre())
				.segundoNombre(usuarioAEnsamblar.getSegundoNombre())
				.primerApellido(usuarioAEnsamblar.getPrimerApellido())
				.segundoApellido(usuarioAEnsamblar.getSegundoApellido())
				.numeroTelefonico(usuarioAEnsamblar.getNumeroTelefonico())
				.correoElectronico(usuarioAEnsamblar.getCorreoElectronico())
				.ciudad(CiudadDTOAssembler.getInstance().ensamblarDTO(usuarioAEnsamblar.getCiudad()))
				.build();
	}

}