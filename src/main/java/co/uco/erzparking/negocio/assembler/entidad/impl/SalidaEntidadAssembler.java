package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.SalidaEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.SalidaDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class SalidaEntidadAssembler implements EntidadAssembler<SalidaDominio, SalidaEntidad> {

	private static EntidadAssembler<SalidaDominio, SalidaEntidad> INSTANCE;

	private SalidaEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<SalidaDominio, SalidaEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new SalidaEntidadAssembler();
		}
		return INSTANCE;
	}

	@Override
	public SalidaEntidad ensamblarEntidad(final SalidaDominio dominio) {
		var salidaAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new SalidaDominio.Builder().build());
		return new SalidaEntidad.Builder()
				.id(salidaAEnsamblar.getId())
				.fechaHoraSalida(salidaAEnsamblar.getFechaHoraSalida())
				.entrada(EntradaEntidadAssembler.getInstance().ensamblarEntidad(salidaAEnsamblar.getEntrada()))
				.build();
	}

	@Override
	public SalidaDominio ensamblarDominio(final SalidaEntidad entidad) {
		var salidaAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new SalidaEntidad.Builder().build());
		return new SalidaDominio.Builder()
				.id(salidaAEnsamblar.getId())
				.fechaHoraSalida(salidaAEnsamblar.getFechaHoraSalida())
				.entrada(EntradaEntidadAssembler.getInstance().ensamblarDominio(salidaAEnsamblar.getEntrada()))
				.build();
	}

}
