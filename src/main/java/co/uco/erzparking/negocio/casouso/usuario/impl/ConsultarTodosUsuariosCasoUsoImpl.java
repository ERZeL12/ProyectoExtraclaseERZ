package co.uco.erzparking.negocio.casouso.usuario.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.negocio.casouso.usuario.ConsultarTodosUsuariosCasoUso;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosUsuariosCasoUsoImpl implements ConsultarTodosUsuariosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosUsuariosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<UsuarioDominio> ejecutar(final UsuarioDominio datos) {
		return consultarTodos(datos);
	}

	private List<UsuarioDominio> consultarTodos(final UsuarioDominio filtro) {
		var entidades = daoFactory.getUsuarioDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new UsuarioEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private UsuarioDominio mapearAdominio(final UsuarioEntidad entidad) {
		var tipoDocumentoIdentificacion = !UtilObjeto.esNulo(entidad.getTipoDocumentoIdentificacion())
				? new TipoDocumentoIdentificacionDominio.Builder()
						.id(entidad.getTipoDocumentoIdentificacion().getId())
						.nombreDocumentoIdentificacion(entidad.getTipoDocumentoIdentificacion().getNombreDocumentoIdentificacion())
						.descripcion(entidad.getTipoDocumentoIdentificacion().getDescripcion())
						.build()
				: null;
		var ciudad = !UtilObjeto.esNulo(entidad.getCiudad())
				? new CiudadDominio.Builder()
						.id(entidad.getCiudad().getId())
						.nombre(entidad.getCiudad().getNombre())
						.build()
				: null;
		return new UsuarioDominio.Builder()
				.id(entidad.getId())
				.tipoDocumentoIdentificacion(tipoDocumentoIdentificacion)
				.numeroIdentificacion(entidad.getNumeroIdentificacion())
				.primerNombre(entidad.getPrimerNombre())
				.segundoNombre(entidad.getSegundoNombre())
				.primerApellido(entidad.getPrimerApellido())
				.segundoApellido(entidad.getSegundoApellido())
				.numeroTelefonico(entidad.getNumeroTelefonico())
				.correoElectronico(entidad.getCorreoElectronico())
				.ciudad(ciudad)
				.build();
	}
}
