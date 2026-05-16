package co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TipoDocumentoIdentificacionEntidad;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.ConsultarTodosTipoDocumentoIdentificacionsCasoUso;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosTipoDocumentoIdentificacionsCasoUsoImpl implements ConsultarTodosTipoDocumentoIdentificacionsCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosTipoDocumentoIdentificacionsCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<TipoDocumentoIdentificacionDominio> ejecutar(final TipoDocumentoIdentificacionDominio datos) {
		return consultarTodos(datos);
	}

	private List<TipoDocumentoIdentificacionDominio> consultarTodos(final TipoDocumentoIdentificacionDominio filtro) {
		var entidades = daoFactory.getTipoDocumentoIdentificacionDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new TipoDocumentoIdentificacionEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private TipoDocumentoIdentificacionDominio mapearAdominio(final TipoDocumentoIdentificacionEntidad entidad) {
		return new TipoDocumentoIdentificacionDominio.Builder()
				.id(entidad.getId())
				.nombreDocumentoIdentificacion(entidad.getNombreDocumentoIdentificacion())
				.descripcion(entidad.getDescripcion())
				.build();
	}
}
