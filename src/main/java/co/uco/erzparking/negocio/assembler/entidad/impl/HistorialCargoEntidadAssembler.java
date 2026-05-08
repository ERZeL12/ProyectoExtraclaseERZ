package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.HistorialCargoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.HistorialCargoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class HistorialCargoEntidadAssembler implements EntidadAssembler<HistorialCargoDominio, HistorialCargoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<HistorialCargoDominio, HistorialCargoEntidad> INSTANCE;

	private HistorialCargoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<HistorialCargoDominio, HistorialCargoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new HistorialCargoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public HistorialCargoEntidad ensamblarEntidad(final HistorialCargoDominio dominio) {
		var historialCargoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new HistorialCargoDominio.Builder().build());
		return new HistorialCargoEntidad.Builder()
				.id(historialCargoAEnsamblar.getId())
				.operario(OperarioEntidadAssembler.getInstance().ensamblarEntidad(historialCargoAEnsamblar.getOperario()))
				.cargo(CargoEntidadAssembler.getInstance().ensamblarEntidad(historialCargoAEnsamblar.getCargo()))
				.fechaInicioEjercicioCargo(historialCargoAEnsamblar.getFechaInicioEjercicioCargo())
				.fechaFinEjercicioCargo(historialCargoAEnsamblar.getFechaFinEjercicioCargo())
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public HistorialCargoDominio ensamblarDominio(final HistorialCargoEntidad entidad) {
		var historialCargoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new HistorialCargoEntidad.Builder().build());
		return new HistorialCargoDominio.Builder()
				.id(historialCargoAEnsamblar.getId())
				.operario(OperarioEntidadAssembler.getInstance().ensamblarDominio(historialCargoAEnsamblar.getOperario()))
				.cargo(CargoEntidadAssembler.getInstance().ensamblarDominio(historialCargoAEnsamblar.getCargo()))
				.fechaInicioEjercicioCargo(historialCargoAEnsamblar.getFechaInicioEjercicioCargo())
				.fechaFinEjercicioCargo(historialCargoAEnsamblar.getFechaFinEjercicioCargo())
				.build();
	}

}