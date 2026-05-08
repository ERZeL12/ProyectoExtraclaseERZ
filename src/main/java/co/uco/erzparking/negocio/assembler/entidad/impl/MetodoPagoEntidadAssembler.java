package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.MetodoPagoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class MetodoPagoEntidadAssembler implements EntidadAssembler<MetodoPagoDominio, MetodoPagoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<MetodoPagoDominio, MetodoPagoEntidad> INSTANCE;

	private MetodoPagoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<MetodoPagoDominio, MetodoPagoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new MetodoPagoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public MetodoPagoEntidad ensamblarEntidad(final MetodoPagoDominio dominio) {
		var metodoPagoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio.Builder().build());
		return new MetodoPagoEntidad.Builder()
				.id(metodoPagoAEnsamblar.getId())
				.nombreMetodoPago(metodoPagoAEnsamblar.getNombreMetodoPago())
				.descripcion(metodoPagoAEnsamblar.getDescripcion())
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public MetodoPagoDominio ensamblarDominio(final MetodoPagoEntidad entidad) {
		var metodoPagoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new MetodoPagoEntidad.Builder().build());
		return new MetodoPagoDominio.Builder()
				.id(metodoPagoAEnsamblar.getId())
				.nombreMetodoPago(metodoPagoAEnsamblar.getNombreMetodoPago())
				.descripcion(metodoPagoAEnsamblar.getDescripcion())
				.build();
	}

}