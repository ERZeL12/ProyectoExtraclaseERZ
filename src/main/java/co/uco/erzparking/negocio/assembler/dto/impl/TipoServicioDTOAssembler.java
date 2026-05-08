package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class TipoServicioDTOAssembler implements DTOAssembler<TipoServicioDominio, TipoServicioDTO> {

	private static DTOAssembler<TipoServicioDominio, TipoServicioDTO> INSTANCE;

	private TipoServicioDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<TipoServicioDominio, TipoServicioDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new TipoServicioDTOAssembler();
		}
		return INSTANCE;
	}

	@Override
	public TipoServicioDominio ensamblarDominio(final TipoServicioDTO dto) {
		var tipoServicioAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new TipoServicioDTO.Builder().build());
		return new TipoServicioDominio.Builder()
				.id(tipoServicioAEnsamblar.getId())
				.nombreServicio(tipoServicioAEnsamblar.getNombreServicio())
				.build();
	}

	@Override
	public TipoServicioDTO ensamblarDTO(final TipoServicioDominio dominio) {
		var tipoServicioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new TipoServicioDominio.Builder().build());
		return new TipoServicioDTO.Builder()
				.id(tipoServicioAEnsamblar.getId())
				.nombreServicio(tipoServicioAEnsamblar.getNombreServicio())
				.build();
	}

}
