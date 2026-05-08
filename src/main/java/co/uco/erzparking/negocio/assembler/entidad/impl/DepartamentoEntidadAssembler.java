package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.DepartamentoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class DepartamentoEntidadAssembler implements EntidadAssembler<DepartamentoDominio, DepartamentoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<DepartamentoDominio, DepartamentoEntidad> INSTANCE;

	private DepartamentoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<DepartamentoDominio, DepartamentoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new DepartamentoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public DepartamentoEntidad ensamblarEntidad(final DepartamentoDominio dominio) {
		var departamentoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new DepartamentoDominio.Builder().build());
		return new DepartamentoEntidad.Builder()
				.id(departamentoAEnsamblar.getId())
				.nombre(departamentoAEnsamblar.getNombre())
				.pais(PaisEntidadAssembler.getInstance().ensamblarEntidad(departamentoAEnsamblar.getPais()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public DepartamentoDominio ensamblarDominio(final DepartamentoEntidad entidad) {
		var departamentoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new DepartamentoEntidad.Builder().build());
		return new DepartamentoDominio.Builder()
				.id(departamentoAEnsamblar.getId())
				.nombre(departamentoAEnsamblar.getNombre())
				.pais(PaisEntidadAssembler.getInstance().ensamblarDominio(departamentoAEnsamblar.getPais()))
				.build();
	}

}