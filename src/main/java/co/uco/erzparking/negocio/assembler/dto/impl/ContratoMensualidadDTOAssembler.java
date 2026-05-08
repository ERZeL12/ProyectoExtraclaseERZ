package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class ContratoMensualidadDTOAssembler implements DTOAssembler<ContratoMensualidadDominio, ContratoMensualidadDTO> {

	// Patron Singleton
	private static DTOAssembler<ContratoMensualidadDominio, ContratoMensualidadDTO> INSTANCE;

	private ContratoMensualidadDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<ContratoMensualidadDominio, ContratoMensualidadDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new ContratoMensualidadDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public ContratoMensualidadDominio ensamblarDominio(final ContratoMensualidadDTO dto) {
		var contratoMensualidadAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ContratoMensualidadDTO.Builder().build());
		return new ContratoMensualidadDominio.Builder()
				.id(contratoMensualidadAEnsamblar.getId())
				.fechaInicioContrato(contratoMensualidadAEnsamblar.getFechaInicioContrato())
				.fechaFinContrato(contratoMensualidadAEnsamblar.getFechaFinContrato())
				.tarifa(TarifaDTOAssembler.getInstance().ensamblarDominio(contratoMensualidadAEnsamblar.getTarifa()))
				.usuarioVehiculo(UsuarioVehiculoDTOAssembler.getInstance().ensamblarDominio(contratoMensualidadAEnsamblar.getUsuarioVehiculo()))
				.espacioFisico(EspacioFisicoDTOAssembler.getInstance().ensamblarDominio(contratoMensualidadAEnsamblar.getEspacioFisico()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public ContratoMensualidadDTO ensamblarDTO(final ContratoMensualidadDominio dominio) {
		var contratoMensualidadAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ContratoMensualidadDominio.Builder().build());
		return new ContratoMensualidadDTO.Builder()
				.id(contratoMensualidadAEnsamblar.getId())
				.fechaInicioContrato(contratoMensualidadAEnsamblar.getFechaInicioContrato())
				.fechaFinContrato(contratoMensualidadAEnsamblar.getFechaFinContrato())
				.tarifa(TarifaDTOAssembler.getInstance().ensamblarDTO(contratoMensualidadAEnsamblar.getTarifa()))
				.usuarioVehiculo(UsuarioVehiculoDTOAssembler.getInstance().ensamblarDTO(contratoMensualidadAEnsamblar.getUsuarioVehiculo()))
				.espacioFisico(EspacioFisicoDTOAssembler.getInstance().ensamblarDTO(contratoMensualidadAEnsamblar.getEspacioFisico()))
				.build();
	}

}