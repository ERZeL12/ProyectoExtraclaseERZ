package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.ZonaParqueaderoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class ZonaParqueaderoEntidadAssembler implements EntidadAssembler<ZonaParqueaderoDominio, ZonaParqueaderoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<ZonaParqueaderoDominio, ZonaParqueaderoEntidad> INSTANCE;

	private ZonaParqueaderoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<ZonaParqueaderoDominio, ZonaParqueaderoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new ZonaParqueaderoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public ZonaParqueaderoEntidad ensamblarEntidad(final ZonaParqueaderoDominio dominio) {
		var zonaParqueaderoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ZonaParqueaderoDominio.Builder().build());
		return new ZonaParqueaderoEntidad.Builder()
				.id(zonaParqueaderoAEnsamblar.getId())
				.nombreZona(zonaParqueaderoAEnsamblar.getNombreZona())
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarEntidad(zonaParqueaderoAEnsamblar.getParqueadero()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public ZonaParqueaderoDominio ensamblarDominio(final ZonaParqueaderoEntidad entidad) {
		var zonaParqueaderoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new ZonaParqueaderoEntidad.Builder().build());
		return new ZonaParqueaderoDominio.Builder()
				.id(zonaParqueaderoAEnsamblar.getId())
				.nombreZona(zonaParqueaderoAEnsamblar.getNombreZona())
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarDominio(zonaParqueaderoAEnsamblar.getParqueadero()))
				.build();
	}

}