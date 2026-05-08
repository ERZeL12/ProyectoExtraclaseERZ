package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class VehiculoDTOAssembler implements DTOAssembler<VehiculoDominio, VehiculoDTO> {

	// Patron Singleton
	private static DTOAssembler<VehiculoDominio, VehiculoDTO> INSTANCE;

	private VehiculoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<VehiculoDominio, VehiculoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new VehiculoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public VehiculoDominio ensamblarDominio(final VehiculoDTO dto) {
		var vehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new VehiculoDTO.Builder().build());
		return new VehiculoDominio.Builder()
				.id(vehiculoAEnsamblar.getId())
				.placaVehiculo(vehiculoAEnsamblar.getPlacaVehiculo())
				.tipoVehiculo(TipoVehiculoDTOAssembler.getInstance().ensamblarDominio(vehiculoAEnsamblar.getTipoVehiculo()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public VehiculoDTO ensamblarDTO(final VehiculoDominio dominio) {
		var vehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new VehiculoDominio.Builder().build());
		return new VehiculoDTO.Builder()
				.id(vehiculoAEnsamblar.getId())
				.placaVehiculo(vehiculoAEnsamblar.getPlacaVehiculo())
				.tipoVehiculo(TipoVehiculoDTOAssembler.getInstance().ensamblarDTO(vehiculoAEnsamblar.getTipoVehiculo()))
				.build();
	}

}