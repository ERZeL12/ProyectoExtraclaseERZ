package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class TipoDocumentoIdentificacionDTOAssembler implements DTOAssembler<TipoDocumentoIdentificacionDominio, TipoDocumentoIdentificacionDTO> {

	// Patron Singleton
	private static DTOAssembler<TipoDocumentoIdentificacionDominio, TipoDocumentoIdentificacionDTO> INSTANCE;

	private TipoDocumentoIdentificacionDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<TipoDocumentoIdentificacionDominio, TipoDocumentoIdentificacionDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new TipoDocumentoIdentificacionDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public TipoDocumentoIdentificacionDominio ensamblarDominio(final TipoDocumentoIdentificacionDTO dto) {
		var tipoDocumentoIdentificacionAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new TipoDocumentoIdentificacionDTO.Builder().build());
		return new TipoDocumentoIdentificacionDominio.Builder()
				.id(tipoDocumentoIdentificacionAEnsamblar.getId())
				.nombreDocumentoIdentificacion(tipoDocumentoIdentificacionAEnsamblar.getNombreDocumentoIdentificacion())
				.descripcion(tipoDocumentoIdentificacionAEnsamblar.getDescripcion())
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public TipoDocumentoIdentificacionDTO ensamblarDTO(final TipoDocumentoIdentificacionDominio dominio) {
		var tipoDocumentoIdentificacionAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new TipoDocumentoIdentificacionDominio.Builder().build());
		return new TipoDocumentoIdentificacionDTO.Builder()
				.id(tipoDocumentoIdentificacionAEnsamblar.getId())
				.nombreDocumentoIdentificacion(tipoDocumentoIdentificacionAEnsamblar.getNombreDocumentoIdentificacion())
				.descripcion(tipoDocumentoIdentificacionAEnsamblar.getDescripcion())
				.build();
	}

}