package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class CiudadEntidadAssembler implements EntidadAssembler<CiudadDominio, CiudadEntidad> {

	// Patron Singleton
	private static EntidadAssembler<CiudadDominio, CiudadEntidad> INSTANCE;

	private CiudadEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<CiudadDominio, CiudadEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new CiudadEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public CiudadEntidad ensamblarEntidad(final CiudadDominio dominio) {
		var ciudadAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CiudadDominio.Builder().build());
		return new CiudadEntidad.Builder()
				.id(ciudadAEnsamblar.getId())
				.nombre(ciudadAEnsamblar.getNombre())
				.departamento(DepartamentoEntidadAssembler.getInstance().ensamblarEntidad(ciudadAEnsamblar.getDepartamento()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public CiudadDominio ensamblarDominio(final CiudadEntidad entidad) {
		var ciudadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new CiudadEntidad.Builder().build());
		return new CiudadDominio.Builder()
				.id(ciudadAEnsamblar.getId())
				.nombre(ciudadAEnsamblar.getNombre())
				.departamento(DepartamentoEntidadAssembler.getInstance().ensamblarDominio(ciudadAEnsamblar.getDepartamento()))
				.build();
	}

}