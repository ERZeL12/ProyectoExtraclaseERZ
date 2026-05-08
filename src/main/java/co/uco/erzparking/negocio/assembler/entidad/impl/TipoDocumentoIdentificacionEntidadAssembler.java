package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.TipoDocumentoIdentificacionEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class TipoDocumentoIdentificacionEntidadAssembler implements EntidadAssembler<TipoDocumentoIdentificacionDominio, TipoDocumentoIdentificacionEntidad> {

	// Patron Singleton
	private static EntidadAssembler<TipoDocumentoIdentificacionDominio, TipoDocumentoIdentificacionEntidad> INSTANCE;

	private TipoDocumentoIdentificacionEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<TipoDocumentoIdentificacionDominio, TipoDocumentoIdentificacionEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new TipoDocumentoIdentificacionEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public TipoDocumentoIdentificacionEntidad ensamblarEntidad(final TipoDocumentoIdentificacionDominio dominio) {
		var tipoDocumentoIdentificacionAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new TipoDocumentoIdentificacionDominio.Builder().build());
		return new TipoDocumentoIdentificacionEntidad.Builder()
				.id(tipoDocumentoIdentificacionAEnsamblar.getId())
				.nombreDocumentoIdentificacion(tipoDocumentoIdentificacionAEnsamblar.getNombreDocumentoIdentificacion())
				.descripcion(tipoDocumentoIdentificacionAEnsamblar.getDescripcion())
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public TipoDocumentoIdentificacionDominio ensamblarDominio(final TipoDocumentoIdentificacionEntidad entidad) {
		var tipoDocumentoIdentificacionAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new TipoDocumentoIdentificacionEntidad.Builder().build());
		return new TipoDocumentoIdentificacionDominio.Builder()
				.id(tipoDocumentoIdentificacionAEnsamblar.getId())
				.nombreDocumentoIdentificacion(tipoDocumentoIdentificacionAEnsamblar.getNombreDocumentoIdentificacion())
				.descripcion(tipoDocumentoIdentificacionAEnsamblar.getDescripcion())
				.build();
	}

}