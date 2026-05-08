package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class VehiculoEntidadAssembler implements EntidadAssembler<VehiculoDominio, VehiculoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<VehiculoDominio, VehiculoEntidad> INSTANCE;

	private VehiculoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<VehiculoDominio, VehiculoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new VehiculoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public VehiculoEntidad ensamblarEntidad(final VehiculoDominio dominio) {
		var vehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new VehiculoDominio.Builder().build());
		return new VehiculoEntidad.Builder()
				.id(vehiculoAEnsamblar.getId())
				.placaVehiculo(vehiculoAEnsamblar.getPlacaVehiculo())
				.tipoVehiculo(TipoVehiculoEntidadAssembler.getInstance().ensamblarEntidad(vehiculoAEnsamblar.getTipoVehiculo()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public VehiculoDominio ensamblarDominio(final VehiculoEntidad entidad) {
		var vehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new VehiculoEntidad.Builder().build());
		return new VehiculoDominio.Builder()
				.id(vehiculoAEnsamblar.getId())
				.placaVehiculo(vehiculoAEnsamblar.getPlacaVehiculo())
				.tipoVehiculo(TipoVehiculoEntidadAssembler.getInstance().ensamblarDominio(vehiculoAEnsamblar.getTipoVehiculo()))
				.build();
	}

}