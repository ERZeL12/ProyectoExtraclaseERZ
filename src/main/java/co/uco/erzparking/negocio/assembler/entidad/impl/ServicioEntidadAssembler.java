package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class ServicioEntidadAssembler implements EntidadAssembler<ServicioDominio, ServicioEntidad> {

	// Patron Singleton
	private static EntidadAssembler<ServicioDominio, ServicioEntidad> INSTANCE;

	private ServicioEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<ServicioDominio, ServicioEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new ServicioEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public ServicioEntidad ensamblarEntidad(final ServicioDominio dominio) {
		var servicioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ServicioDominio.Builder().build());
		return new ServicioEntidad.Builder()
				.id(servicioAEnsamblar.getId())
				.nombreServicio(servicioAEnsamblar.getNombreServicio())
				.tipoServicio(TipoServicioEntidadAssembler.getInstance().ensamblarEntidad(servicioAEnsamblar.getTipoServicio()))
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarEntidad(servicioAEnsamblar.getParqueadero()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public ServicioDominio ensamblarDominio(final ServicioEntidad entidad) {
		var servicioAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new ServicioEntidad.Builder().build());
		return new ServicioDominio.Builder()
				.id(servicioAEnsamblar.getId())
				.nombreServicio(servicioAEnsamblar.getNombreServicio())
				.tipoServicio(TipoServicioEntidadAssembler.getInstance().ensamblarDominio(servicioAEnsamblar.getTipoServicio()))
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarDominio(servicioAEnsamblar.getParqueadero()))
				.build();
	}

}