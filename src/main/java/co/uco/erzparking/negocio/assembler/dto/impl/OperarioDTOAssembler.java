package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class OperarioDTOAssembler implements DTOAssembler<OperarioDominio, OperarioDTO> {

	private static DTOAssembler<OperarioDominio, OperarioDTO> INSTANCE;

	private OperarioDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<OperarioDominio, OperarioDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new OperarioDTOAssembler();
		}
		return INSTANCE;
	}

	@Override
	public OperarioDominio ensamblarDominio(final OperarioDTO dto) {
		var operarioAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new OperarioDTO.Builder().build());
		return new OperarioDominio.Builder()
				.id(operarioAEnsamblar.getId())
				.tipoDocumentoIdentificacion(TipoDocumentoIdentificacionDTOAssembler.getInstance().ensamblarDominio(operarioAEnsamblar.getTipoDocumentoIdentificacion()))
				.numeroIdentificacion(operarioAEnsamblar.getNumeroIdentificacion())
				.primerNombre(operarioAEnsamblar.getPrimerNombre())
				.segundoNombre(operarioAEnsamblar.getSegundoNombre())
				.primerApellido(operarioAEnsamblar.getPrimerApellido())
				.segundoApellido(operarioAEnsamblar.getSegundoApellido())
				.numeroTelefonico(operarioAEnsamblar.getNumeroTelefonico())
				.cargo(CargoDTOAssembler.getInstance().ensamblarDominio(operarioAEnsamblar.getCargo()))
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDominio(operarioAEnsamblar.getParqueadero()))
				.build();
	}

	@Override
	public OperarioDTO ensamblarDTO(final OperarioDominio dominio) {
		var operarioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new OperarioDominio.Builder().build());
		return new OperarioDTO.Builder()
				.id(operarioAEnsamblar.getId())
				.tipoDocumentoIdentificacion(TipoDocumentoIdentificacionDTOAssembler.getInstance().ensamblarDTO(operarioAEnsamblar.getTipoDocumentoIdentificacion()))
				.numeroIdentificacion(operarioAEnsamblar.getNumeroIdentificacion())
				.primerNombre(operarioAEnsamblar.getPrimerNombre())
				.segundoNombre(operarioAEnsamblar.getSegundoNombre())
				.primerApellido(operarioAEnsamblar.getPrimerApellido())
				.segundoApellido(operarioAEnsamblar.getSegundoApellido())
				.numeroTelefonico(operarioAEnsamblar.getNumeroTelefonico())
				.cargo(CargoDTOAssembler.getInstance().ensamblarDTO(operarioAEnsamblar.getCargo()))
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDTO(operarioAEnsamblar.getParqueadero()))
				.build();
	}

}
