package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class OperarioEntidadAssembler implements EntidadAssembler<OperarioDominio, OperarioEntidad> {

	private static EntidadAssembler<OperarioDominio, OperarioEntidad> INSTANCE;

	private OperarioEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<OperarioDominio, OperarioEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new OperarioEntidadAssembler();
		}
		return INSTANCE;
	}

	@Override
	public OperarioEntidad ensamblarEntidad(final OperarioDominio dominio) {
		var operarioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new OperarioDominio.Builder().build());
		return new OperarioEntidad.Builder()
				.id(operarioAEnsamblar.getId())
				.tipoDocumentoIdentificacion(TipoDocumentoIdentificacionEntidadAssembler.getInstance().ensamblarEntidad(operarioAEnsamblar.getTipoDocumentoIdentificacion()))
				.numeroIdentificacion(operarioAEnsamblar.getNumeroIdentificacion())
				.primerNombre(operarioAEnsamblar.getPrimerNombre())
				.segundoNombre(operarioAEnsamblar.getSegundoNombre())
				.primerApellido(operarioAEnsamblar.getPrimerApellido())
				.segundoApellido(operarioAEnsamblar.getSegundoApellido())
				.numeroTelefonico(operarioAEnsamblar.getNumeroTelefonico())
				.cargo(CargoEntidadAssembler.getInstance().ensamblarEntidad(operarioAEnsamblar.getCargo()))
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarEntidad(operarioAEnsamblar.getParqueadero()))
				.estadoActual(operarioAEnsamblar.isEstadoActual())
				.build();
	}

	@Override
	public OperarioDominio ensamblarDominio(final OperarioEntidad entidad) {
		var operarioAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new OperarioEntidad.Builder().build());
		return new OperarioDominio.Builder()
				.id(operarioAEnsamblar.getId())
				.tipoDocumentoIdentificacion(TipoDocumentoIdentificacionEntidadAssembler.getInstance().ensamblarDominio(operarioAEnsamblar.getTipoDocumentoIdentificacion()))
				.numeroIdentificacion(operarioAEnsamblar.getNumeroIdentificacion())
				.primerNombre(operarioAEnsamblar.getPrimerNombre())
				.segundoNombre(operarioAEnsamblar.getSegundoNombre())
				.primerApellido(operarioAEnsamblar.getPrimerApellido())
				.segundoApellido(operarioAEnsamblar.getSegundoApellido())
				.numeroTelefonico(operarioAEnsamblar.getNumeroTelefonico())
				.cargo(CargoEntidadAssembler.getInstance().ensamblarDominio(operarioAEnsamblar.getCargo()))
				.parqueadero(ParqueaderoEntidadAssembler.getInstance().ensamblarDominio(operarioAEnsamblar.getParqueadero()))
				.estadoActual(operarioAEnsamblar.isEstadoActual())
				.build();
	}

}
