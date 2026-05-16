package co.uco.erzparking.negocio.casouso.tiposervicio.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TipoServicioEntidad;
import co.uco.erzparking.negocio.casouso.tiposervicio.ConsultarTodosTipoServiciosCasoUso;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosTipoServiciosCasoUsoImpl implements ConsultarTodosTipoServiciosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosTipoServiciosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<TipoServicioDominio> ejecutar(final TipoServicioDominio datos) {
		return consultarTodos(datos);
	}

	private List<TipoServicioDominio> consultarTodos(final TipoServicioDominio filtro) {
		var entidades = daoFactory.getTipoServicioDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new TipoServicioEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private TipoServicioDominio mapearAdominio(final TipoServicioEntidad entidad) {
		return new TipoServicioDominio.Builder()
				.id(entidad.getId())
				.nombreServicio(entidad.getNombreServicio())
				.build();
	}
}
