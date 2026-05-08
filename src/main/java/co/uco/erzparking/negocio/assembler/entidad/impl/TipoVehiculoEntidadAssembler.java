package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class TipoVehiculoEntidadAssembler implements EntidadAssembler<TipoVehiculoDominio, TipoVehiculoEntidad> {

	private static EntidadAssembler<TipoVehiculoDominio, TipoVehiculoEntidad> INSTANCE;

	private TipoVehiculoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<TipoVehiculoDominio, TipoVehiculoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new TipoVehiculoEntidadAssembler();
		}
		return INSTANCE;
	}

	@Override
	public TipoVehiculoEntidad ensamblarEntidad(final TipoVehiculoDominio dominio) {
		var tipoVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new TipoVehiculoDominio.Builder().build());
		return new TipoVehiculoEntidad.Builder()
				.id(tipoVehiculoAEnsamblar.getId())
				.nombreVehiculo(tipoVehiculoAEnsamblar.getNombreVehiculo())
				.build();
	}

	@Override
	public TipoVehiculoDominio ensamblarDominio(final TipoVehiculoEntidad entidad) {
		var tipoVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new TipoVehiculoEntidad.Builder().build());
		return new TipoVehiculoDominio.Builder()
				.id(tipoVehiculoAEnsamblar.getId())
				.nombreVehiculo(tipoVehiculoAEnsamblar.getNombreVehiculo())
				.build();
	}

}
