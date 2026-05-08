package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.MetodoPagoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class MetodoPagoDTOAssembler implements DTOAssembler<MetodoPagoDominio, MetodoPagoDTO> {

	// Patron Singleton
	private static DTOAssembler<MetodoPagoDominio, MetodoPagoDTO> INSTANCE;

	private MetodoPagoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<MetodoPagoDominio, MetodoPagoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new MetodoPagoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public MetodoPagoDominio ensamblarDominio(final MetodoPagoDTO dto) {
		var metodoPagoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new MetodoPagoDTO.Builder().build());
		return new MetodoPagoDominio.Builder()
				.id(metodoPagoAEnsamblar.getId())
				.nombreMetodoPago(metodoPagoAEnsamblar.getNombreMetodoPago())
				.descripcion(metodoPagoAEnsamblar.getDescripcion())
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public MetodoPagoDTO ensamblarDTO(final MetodoPagoDominio dominio) {
		var metodoPagoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio.Builder().build());
		return new MetodoPagoDTO.Builder()
				.id(metodoPagoAEnsamblar.getId())
				.nombreMetodoPago(metodoPagoAEnsamblar.getNombreMetodoPago())
				.descripcion(metodoPagoAEnsamblar.getDescripcion())
				.build();
	}

}