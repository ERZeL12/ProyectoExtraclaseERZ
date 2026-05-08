package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class CargoDTOAssembler implements DTOAssembler<CargoDominio, CargoDTO> {

	// Patron Singleton
	private static DTOAssembler<CargoDominio, CargoDTO> INSTANCE;

	private CargoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<CargoDominio, CargoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new CargoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public CargoDominio ensamblarDominio(final CargoDTO dto) {
		var cargoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new CargoDTO.Builder().build());
		return new CargoDominio.Builder()
				.id(cargoAEnsamblar.getId())
				.nombreCargo(cargoAEnsamblar.getNombreCargo())
				.descripcion(cargoAEnsamblar.getDescripcion())
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDominio(cargoAEnsamblar.getParqueadero()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public CargoDTO ensamblarDTO(final CargoDominio dominio) {
		var cargoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CargoDominio.Builder().build());
		return new CargoDTO.Builder()
				.id(cargoAEnsamblar.getId())
				.nombreCargo(cargoAEnsamblar.getNombreCargo())
				.descripcion(cargoAEnsamblar.getDescripcion())
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDTO(cargoAEnsamblar.getParqueadero()))
				.build();
	}

}