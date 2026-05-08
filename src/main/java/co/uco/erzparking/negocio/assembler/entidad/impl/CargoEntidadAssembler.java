package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class CargoEntidadAssembler implements EntidadAssembler<CargoDominio, CargoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<CargoDominio, CargoEntidad> INSTANCE;

	private CargoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<CargoDominio, CargoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new CargoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public CargoEntidad ensamblarEntidad(final CargoDominio dominio) {
		var cargoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CargoDominio.Builder().build());
		return new CargoEntidad.Builder()
				.id(cargoAEnsamblar.getId())
				.nombreCargo(cargoAEnsamblar.getNombreCargo())
				.descripcion(cargoAEnsamblar.getDescripcion())
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarEntidad(cargoAEnsamblar.getParqueadero()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public CargoDominio ensamblarDominio(final CargoEntidad entidad) {
		var cargoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new CargoEntidad.Builder().build());
		return new CargoDominio.Builder()
				.id(cargoAEnsamblar.getId())
				.nombreCargo(cargoAEnsamblar.getNombreCargo())
				.descripcion(cargoAEnsamblar.getDescripcion())
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarDominio(cargoAEnsamblar.getParqueadero()))
				.build();
	}

}