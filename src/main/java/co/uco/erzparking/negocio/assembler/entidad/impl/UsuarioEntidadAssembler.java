package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class UsuarioEntidadAssembler implements EntidadAssembler<UsuarioDominio, UsuarioEntidad> {

	// Patron Singleton
	private static EntidadAssembler<UsuarioDominio, UsuarioEntidad> INSTANCE;

	private UsuarioEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<UsuarioDominio, UsuarioEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new UsuarioEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public UsuarioEntidad ensamblarEntidad(final UsuarioDominio dominio) {
		var usuarioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new UsuarioDominio.Builder().build());
		return new UsuarioEntidad.Builder()
				.id(usuarioAEnsamblar.getId())
				.tipoDocumentoIdentificacion(TipoDocumentoIdentificacionEntidadAssembler.getInstance().ensamblarEntidad(usuarioAEnsamblar.getTipoDocumentoIdentificacion()))
				.numeroIdentificacion(usuarioAEnsamblar.getNumeroIdentificacion())
				.primerNombre(usuarioAEnsamblar.getPrimerNombre())
				.segundoNombre(usuarioAEnsamblar.getSegundoNombre())
				.primerApellido(usuarioAEnsamblar.getPrimerApellido())
				.segundoApellido(usuarioAEnsamblar.getSegundoApellido())
				.numeroTelefonico(usuarioAEnsamblar.getNumeroTelefonico())
				.correoElectronico(usuarioAEnsamblar.getCorreoElectronico())
				.ciudad(CiudadEntidadAssembler.getInstance().ensamblarEntidad(usuarioAEnsamblar.getCiudad()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public UsuarioDominio ensamblarDominio(final UsuarioEntidad entidad) {
		var usuarioAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new UsuarioEntidad.Builder().build());
		return new UsuarioDominio.Builder()
				.id(usuarioAEnsamblar.getId())
				.tipoDocumentoIdentificacion(TipoDocumentoIdentificacionEntidadAssembler.getInstance().ensamblarDominio(usuarioAEnsamblar.getTipoDocumentoIdentificacion()))
				.numeroIdentificacion(usuarioAEnsamblar.getNumeroIdentificacion())
				.primerNombre(usuarioAEnsamblar.getPrimerNombre())
				.segundoNombre(usuarioAEnsamblar.getSegundoNombre())
				.primerApellido(usuarioAEnsamblar.getPrimerApellido())
				.segundoApellido(usuarioAEnsamblar.getSegundoApellido())
				.numeroTelefonico(usuarioAEnsamblar.getNumeroTelefonico())
				.correoElectronico(usuarioAEnsamblar.getCorreoElectronico())
				.ciudad(CiudadEntidadAssembler.getInstance().ensamblarDominio(usuarioAEnsamblar.getCiudad()))
				.build();
	}

}