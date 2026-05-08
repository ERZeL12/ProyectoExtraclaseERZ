package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class UsuarioVehiculoDTOAssembler implements DTOAssembler<UsuarioVehiculoDominio, UsuarioVehiculoDTO> {

	// Patron Singleton
	private static DTOAssembler<UsuarioVehiculoDominio, UsuarioVehiculoDTO> INSTANCE;

	private UsuarioVehiculoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<UsuarioVehiculoDominio, UsuarioVehiculoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new UsuarioVehiculoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public UsuarioVehiculoDominio ensamblarDominio(final UsuarioVehiculoDTO dto) {
		var usuarioVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new UsuarioVehiculoDTO.Builder().build());
		return new UsuarioVehiculoDominio.Builder()
				.id(usuarioVehiculoAEnsamblar.getId())
				.usuario(UsuarioDTOAssembler.getInstance().ensamblarDominio(usuarioVehiculoAEnsamblar.getUsuario()))
				.vehiculo(VehiculoDTOAssembler.getInstance().ensamblarDominio(usuarioVehiculoAEnsamblar.getVehiculo()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public UsuarioVehiculoDTO ensamblarDTO(final UsuarioVehiculoDominio dominio) {
		var usuarioVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new UsuarioVehiculoDominio.Builder().build());
		return new UsuarioVehiculoDTO.Builder()
				.id(usuarioVehiculoAEnsamblar.getId())
				.usuario(UsuarioDTOAssembler.getInstance().ensamblarDTO(usuarioVehiculoAEnsamblar.getUsuario()))
				.vehiculo(VehiculoDTOAssembler.getInstance().ensamblarDTO(usuarioVehiculoAEnsamblar.getVehiculo()))
				.build();
	}

}