package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class TipoVehiculoDTOAssembler implements DTOAssembler<TipoVehiculoDominio, TipoVehiculoDTO> {

	private static DTOAssembler<TipoVehiculoDominio, TipoVehiculoDTO> INSTANCE;

	private TipoVehiculoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<TipoVehiculoDominio, TipoVehiculoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new TipoVehiculoDTOAssembler();
		}
		return INSTANCE;
	}

	@Override
	public TipoVehiculoDominio ensamblarDominio(final TipoVehiculoDTO dto) {
		var tipoVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new TipoVehiculoDTO.Builder().build());
		return new TipoVehiculoDominio.Builder()
				.id(tipoVehiculoAEnsamblar.getId())
				.nombreVehiculo(tipoVehiculoAEnsamblar.getNombreVehiculo())
				.build();
	}

	@Override
	public TipoVehiculoDTO ensamblarDTO(final TipoVehiculoDominio dominio) {
		var tipoVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new TipoVehiculoDominio.Builder().build());
		return new TipoVehiculoDTO.Builder()
				.id(tipoVehiculoAEnsamblar.getId())
				.nombreVehiculo(tipoVehiculoAEnsamblar.getNombreVehiculo())
				.build();
	}

}
