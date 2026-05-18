package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.ContratoMensualidadEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class ContratoMensualidadEntidadAssembler implements EntidadAssembler<ContratoMensualidadDominio, ContratoMensualidadEntidad> {

	// Patron Singleton
	private static EntidadAssembler<ContratoMensualidadDominio, ContratoMensualidadEntidad> INSTANCE;

	private ContratoMensualidadEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<ContratoMensualidadDominio, ContratoMensualidadEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new ContratoMensualidadEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public ContratoMensualidadEntidad ensamblarEntidad(final ContratoMensualidadDominio dominio) {
		var contratoMensualidadAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ContratoMensualidadDominio.Builder().build());
		return new ContratoMensualidadEntidad.Builder()
				.id(contratoMensualidadAEnsamblar.getId())
				.fechaInicioContrato(contratoMensualidadAEnsamblar.getFechaInicioContrato())
				.fechaFinContrato(contratoMensualidadAEnsamblar.getFechaFinContrato())
				.tarifa(TarifaEntidadAssembler.getInstance().ensamblarEntidad(contratoMensualidadAEnsamblar.getTarifa()))
				.usuarioVehiculo(UsuarioVehiculoEntidadAssembler.getInstance().ensamblarEntidad(contratoMensualidadAEnsamblar.getUsuarioVehiculo()))
				.espacioFisico(EspacioFisicoEntidadAssembler.getInstance().ensamblarEntidad(contratoMensualidadAEnsamblar.getEspacioFisico()))
				.estadoActual(contratoMensualidadAEnsamblar.isEstadoActual())
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public ContratoMensualidadDominio ensamblarDominio(final ContratoMensualidadEntidad entidad) {
		var contratoMensualidadAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new ContratoMensualidadEntidad.Builder().build());
		return new ContratoMensualidadDominio.Builder()
				.id(contratoMensualidadAEnsamblar.getId())
				.fechaInicioContrato(contratoMensualidadAEnsamblar.getFechaInicioContrato())
				.fechaFinContrato(contratoMensualidadAEnsamblar.getFechaFinContrato())
				.tarifa(TarifaEntidadAssembler.getInstance().ensamblarDominio(contratoMensualidadAEnsamblar.getTarifa()))
				.usuarioVehiculo(UsuarioVehiculoEntidadAssembler.getInstance().ensamblarDominio(contratoMensualidadAEnsamblar.getUsuarioVehiculo()))
				.espacioFisico(EspacioFisicoEntidadAssembler.getInstance().ensamblarDominio(contratoMensualidadAEnsamblar.getEspacioFisico()))
				.estadoActual(contratoMensualidadAEnsamblar.isEstadoActual())
				.build();
	}

}