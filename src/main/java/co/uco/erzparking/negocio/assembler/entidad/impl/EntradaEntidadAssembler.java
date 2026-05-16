package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.EntradaEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.EntradaDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public final class EntradaEntidadAssembler implements EntidadAssembler<EntradaDominio, EntradaEntidad> {

	private static EntidadAssembler<EntradaDominio, EntradaEntidad> INSTANCE;

	private EntradaEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<EntradaDominio, EntradaEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new EntradaEntidadAssembler();
		}
		return INSTANCE;
	}

	@Override
	public EntradaEntidad ensamblarEntidad(final EntradaDominio dominio) {
		var entradaAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EntradaDominio.Builder().build());
		return new EntradaEntidad.Builder()
				.id(entradaAEnsamblar.getId())
				.fechaHoraEntrada(entradaAEnsamblar.getFechaHoraEntrada())
				.vehiculo(VehiculoEntidadAssembler.getInstance().ensamblarEntidad(entradaAEnsamblar.getVehiculo()))
				.servicio(ServicioEntidadAssembler.getInstance().ensamblarEntidad(entradaAEnsamblar.getServicio()))
				.operario(OperarioEntidadAssembler.getInstance().ensamblarEntidad(entradaAEnsamblar.getOperario()))
				.build();
	}

	@Override
	public EntradaDominio ensamblarDominio(final EntradaEntidad entidad) {
		var entradaAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new EntradaEntidad.Builder().build());
		return new EntradaDominio.Builder()
				.id(entradaAEnsamblar.getId())
				.fechaHoraEntrada(entradaAEnsamblar.getFechaHoraEntrada())
				.vehiculo(VehiculoEntidadAssembler.getInstance().ensamblarDominio(entradaAEnsamblar.getVehiculo()))
				.servicio(ServicioEntidadAssembler.getInstance().ensamblarDominio(entradaAEnsamblar.getServicio()))
				.operario(OperarioEntidadAssembler.getInstance().ensamblarDominio(entradaAEnsamblar.getOperario()))
				.build();
	}

}