package co.uco.erzparking.negocio.casouso.espaciofisico.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.negocio.casouso.espaciofisico.ConsultarTodosEspacioFisicosCasoUso;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
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
						.descripcion(entidad.getTipoServicio().getDescripcion())
						.build()
				: null;
		var estadoEspacioFisico = !UtilObjeto.esNulo(entidad.getEstadoEspacioFisico())
				? new EstadoEspacioFisicoDominio.Builder()
						.id(entidad.getEstadoEspacioFisico().getId())
						.nombreEstadoEspacioFisico(entidad.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())
						.build()
				: null;
		var zonaEspacioFisico = !UtilObjeto.esNulo(entidad.getZonaEspacioFisico())
				? new ZonaParqueaderoDominio.Builder()
						.id(entidad.getZonaEspacioFisico().getId())
						.nombreZona(entidad.getZonaEspacioFisico().getNombreZona())
						.build()
				: null;
		var parqueadero = !UtilObjeto.esNulo(entidad.getParqueadero())
				? new ParqueaderoDominio.Builder()
						.id(entidad.getParqueadero().getId())
						.nombreEstablecimiento(entidad.getParqueadero().getNombreEstablecimiento())
						.build()
				: null;
		return new EspacioFisicoDominio.Builder()
				.id(entidad.getId())
				.numeroEspacioFisico(entidad.getNumeroEspacioFisico())
				.tipoServicio(tipoServicio)
				.estadoEspacioFisico(estadoEspacioFisico)
				.zonaEspacioFisico(zonaEspacioFisico)
				.parqueadero(parqueadero)
				.build();
	}
}
