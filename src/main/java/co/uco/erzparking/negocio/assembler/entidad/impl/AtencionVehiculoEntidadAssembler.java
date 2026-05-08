package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.AtencionVehiculoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.AtencionVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class AtencionVehiculoEntidadAssembler implements EntidadAssembler<AtencionVehiculoDominio, AtencionVehiculoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<AtencionVehiculoDominio, AtencionVehiculoEntidad> INSTANCE;

	private AtencionVehiculoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<AtencionVehiculoDominio, AtencionVehiculoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new AtencionVehiculoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public AtencionVehiculoEntidad ensamblarEntidad(final AtencionVehiculoDominio dominio) {
		var atencionVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new AtencionVehiculoDominio.Builder().build());
		return new AtencionVehiculoEntidad.Builder()
				.id(atencionVehiculoAEnsamblar.getId())
				.entrada(EntradaEntidadAssembler.getInstance().ensamblarEntidad(atencionVehiculoAEnsamblar.getEntrada()))
				.servicio(ServicioEntidadAssembler.getInstance().ensamblarEntidad(atencionVehiculoAEnsamblar.getServicio()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public AtencionVehiculoDominio ensamblarDominio(final AtencionVehiculoEntidad entidad) {
		var atencionVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new AtencionVehiculoEntidad.Builder().build());
		return new AtencionVehiculoDominio.Builder()
				.id(atencionVehiculoAEnsamblar.getId())
				.entrada(EntradaEntidadAssembler.getInstance().ensamblarDominio(atencionVehiculoAEnsamblar.getEntrada()))
				.servicio(ServicioEntidadAssembler.getInstance().ensamblarDominio(atencionVehiculoAEnsamblar.getServicio()))
				.build();
	}

}