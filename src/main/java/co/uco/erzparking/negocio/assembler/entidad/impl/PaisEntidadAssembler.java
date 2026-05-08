package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.PaisEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class PaisEntidadAssembler implements EntidadAssembler<PaisDominio, PaisEntidad> {

	// Patron Singleton
	private static EntidadAssembler<PaisDominio, PaisEntidad> INSTANCE;

	private PaisEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<PaisDominio, PaisEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new PaisEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public PaisEntidad ensamblarEntidad(final PaisDominio dominio) {
		var paisAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PaisDominio.Builder().build());
		return new PaisEntidad.Builder()
				.id(paisAEnsamblar.getId())
				.nombre(paisAEnsamblar.getNombre())
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public PaisDominio ensamblarDominio(final PaisEntidad entidad) {
		var paisAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new PaisEntidad.Builder().build());
		return new PaisDominio.Builder()
				.id(paisAEnsamblar.getId())
				.nombre(paisAEnsamblar.getNombre())
				.build();
	}

}