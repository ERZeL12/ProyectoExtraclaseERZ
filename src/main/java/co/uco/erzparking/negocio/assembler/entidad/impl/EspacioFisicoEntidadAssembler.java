package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class EspacioFisicoEntidadAssembler implements EntidadAssembler<EspacioFisicoDominio, EspacioFisicoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<EspacioFisicoDominio, EspacioFisicoEntidad> INSTANCE;

	private EspacioFisicoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<EspacioFisicoDominio, EspacioFisicoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new EspacioFisicoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public EspacioFisicoEntidad ensamblarEntidad(final EspacioFisicoDominio dominio) {
		var espacioFisicoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EspacioFisicoDominio.Builder().build());
		return new EspacioFisicoEntidad.Builder()
				.id(espacioFisicoAEnsamblar.getId())
				.numeroEspacioFisico(espacioFisicoAEnsamblar.getNumeroEspacioFisico())
				.tipoServicio(TipoServicioEntidadAssembler.getInstance().ensamblarEntidad(espacioFisicoAEnsamblar.getTipoServicio()))
				.estadoEspacioFisico(EstadoEspacioFisicoEntidadAssembler.getInstance().ensamblarEntidad(espacioFisicoAEnsamblar.getEstadoEspacioFisico()))
				.zonaEspacioFisico(ZonaParqueaderoEntidadAssembler.getInstance().ensamblarEntidad(espacioFisicoAEnsamblar.getZonaEspacioFisico()))
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarEntidad(espacioFisicoAEnsamblar.getParqueadero()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public EspacioFisicoDominio ensamblarDominio(final EspacioFisicoEntidad entidad) {
		var espacioFisicoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new EspacioFisicoEntidad.Builder().build());
		return new EspacioFisicoDominio.Builder()
				.id(espacioFisicoAEnsamblar.getId())
				.numeroEspacioFisico(espacioFisicoAEnsamblar.getNumeroEspacioFisico())
				.tipoServicio(TipoServicioEntidadAssembler.getInstance().ensamblarDominio(espacioFisicoAEnsamblar.getTipoServicio()))
				.estadoEspacioFisico(EstadoEspacioFisicoEntidadAssembler.getInstance().ensamblarDominio(espacioFisicoAEnsamblar.getEstadoEspacioFisico()))
				.zonaEspacioFisico(ZonaParqueaderoEntidadAssembler.getInstance().ensamblarDominio(espacioFisicoAEnsamblar.getZonaEspacioFisico()))
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarDominio(espacioFisicoAEnsamblar.getParqueadero()))
				.build();
	}

}