package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class UsuarioVehiculoEntidadAssembler implements EntidadAssembler<UsuarioVehiculoDominio, UsuarioVehiculoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<UsuarioVehiculoDominio, UsuarioVehiculoEntidad> INSTANCE;

	private UsuarioVehiculoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<UsuarioVehiculoDominio, UsuarioVehiculoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new UsuarioVehiculoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public UsuarioVehiculoEntidad ensamblarEntidad(final UsuarioVehiculoDominio dominio) {
		var usuarioVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new UsuarioVehiculoDominio.Builder().build());
		return new UsuarioVehiculoEntidad.Builder()
				.id(usuarioVehiculoAEnsamblar.getId())
				.usuario(UsuarioEntidadAssembler.getInstance().ensamblarEntidad(usuarioVehiculoAEnsamblar.getUsuario()))
				.vehiculo(VehiculoEntidadAssembler.getInstance().ensamblarEntidad(usuarioVehiculoAEnsamblar.getVehiculo()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public UsuarioVehiculoDominio ensamblarDominio(final UsuarioVehiculoEntidad entidad) {
		var usuarioVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new UsuarioVehiculoEntidad.Builder().build());
		return new UsuarioVehiculoDominio.Builder()
				.id(usuarioVehiculoAEnsamblar.getId())
				.usuario(UsuarioEntidadAssembler.getInstance().ensamblarDominio(usuarioVehiculoAEnsamblar.getUsuario()))
				.vehiculo(VehiculoEntidadAssembler.getInstance().ensamblarDominio(usuarioVehiculoAEnsamblar.getVehiculo()))
				.build();
	}

}