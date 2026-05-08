package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.TipoServicioEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class TipoServicioEntidadAssembler implements EntidadAssembler<TipoServicioDominio, TipoServicioEntidad> {

	private static EntidadAssembler<TipoServicioDominio, TipoServicioEntidad> INSTANCE;

	private TipoServicioEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<TipoServicioDominio, TipoServicioEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new TipoServicioEntidadAssembler();
		}
		return INSTANCE;
	}

	@Override
	public TipoServicioEntidad ensamblarEntidad(final TipoServicioDominio dominio) {
		var tipoServicioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new TipoServicioDominio.Builder().build());
		return new TipoServicioEntidad.Builder()
				.id(tipoServicioAEnsamblar.getId())
				.nombreServicio(tipoServicioAEnsamblar.getNombreServicio())
				.build();
	}

	@Override
	public TipoServicioDominio ensamblarDominio(final TipoServicioEntidad entidad) {
		var tipoServicioAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new TipoServicioEntidad.Builder().build());
		return new TipoServicioDominio.Builder()
				.id(tipoServicioAEnsamblar.getId())
				.nombreServicio(tipoServicioAEnsamblar.getNombreServicio())
				.build();
	}

}
