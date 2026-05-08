package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class EstadoEspacioFisicoEntidadAssembler implements EntidadAssembler<EstadoEspacioFisicoDominio, EstadoEspacioFisicoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<EstadoEspacioFisicoDominio, EstadoEspacioFisicoEntidad> INSTANCE;

	private EstadoEspacioFisicoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<EstadoEspacioFisicoDominio, EstadoEspacioFisicoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new EstadoEspacioFisicoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public EstadoEspacioFisicoEntidad ensamblarEntidad(final EstadoEspacioFisicoDominio dominio) {
		var estadoEspacioFisicoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EstadoEspacioFisicoDominio.Builder().build());
		return new EstadoEspacioFisicoEntidad.Builder()
				.nombreEstadoEspacioFisico(estadoEspacioFisicoAEnsamblar.getNombreEstadoEspacioFisico())
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public EstadoEspacioFisicoDominio ensamblarDominio(final EstadoEspacioFisicoEntidad entidad) {
		var estadoEspacioFisicoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new EstadoEspacioFisicoEntidad.Builder().build());
		return new EstadoEspacioFisicoDominio.Builder()
				.nombreEstadoEspacioFisico(estadoEspacioFisicoAEnsamblar.getNombreEstadoEspacioFisico())
				.build();
	}

}