package co.uco.erzparking.negocio.casouso.estadoespaciofisico.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.ConsultarTodosEstadoEspacioFisicosCasoUso;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosEstadoEspacioFisicosCasoUsoImpl implements ConsultarTodosEstadoEspacioFisicosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosEstadoEspacioFisicosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<EstadoEspacioFisicoDominio> ejecutar(final EstadoEspacioFisicoDominio datos) {
		return consultarTodos(datos);
	}

	private List<EstadoEspacioFisicoDominio> consultarTodos(final EstadoEspacioFisicoDominio filtro) {
		var entidades = daoFactory.getEstadoEspacioFisicoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new EstadoEspacioFisicoEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private EstadoEspacioFisicoDominio mapearAdominio(final EstadoEspacioFisicoEntidad entidad) {
		return new EstadoEspacioFisicoDominio.Builder()
				.id(entidad.getId())
				.nombreEstadoEspacioFisico(entidad.getNombreEstadoEspacioFisico())
				.build();
	}
}
