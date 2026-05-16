package co.uco.erzparking.negocio.casouso.espaciofisico.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.negocio.casouso.espaciofisico.ConsultarTodosEspacioFisicosCasoUso;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosEspacioFisicosCasoUsoImpl implements ConsultarTodosEspacioFisicosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosEspacioFisicosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<EspacioFisicoDominio> ejecutar(final EspacioFisicoDominio datos) {
		return consultarTodos(datos);
	}

	private List<EspacioFisicoDominio> consultarTodos(final EspacioFisicoDominio filtro) {
		var entidades = daoFactory.getEspacioFisicoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new EspacioFisicoEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private EspacioFisicoDominio mapearAdominio(final EspacioFisicoEntidad entidad) {
		var tipoServicio = !UtilObjeto.esNulo(entidad.getTipoServicio())
				? new TipoServicioDominio.Builder()
						.id(entidad.getTipoServicio().getId())
						.nombreServicio(entidad.getTipoServicio().getNombreServicio())
						.build()
				: null;
		var estadoEspacioFisico = !UtilObjeto.esNulo(entidad.getEstadoEspacioFisico())
				? new EstadoEspacioFisicoDominio.Builder()
						.nombreEstadoEspacioFisico(entidad.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())
						.build()
				: null;
		return new EspacioFisicoDominio.Builder()
				.id(entidad.getId())
				.numeroEspacioFisico(entidad.getNumeroEspacioFisico())
				.tipoServicio(tipoServicio)
				.estadoEspacioFisico(estadoEspacioFisico)
				.build();
	}
}
