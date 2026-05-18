package co.uco.erzparking.negocio.casouso.servicio.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.negocio.casouso.servicio.ConsultarTodosServiciosCasoUso;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosServiciosCasoUsoImpl implements ConsultarTodosServiciosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosServiciosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<ServicioDominio> ejecutar(final ServicioDominio datos) {
		return consultarTodos(datos);
	}

	private List<ServicioDominio> consultarTodos(final ServicioDominio filtro) {
		var entidades = daoFactory.getServicioDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new ServicioEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private ServicioDominio mapearAdominio(final ServicioEntidad entidad) {
		var tipoServicio = !UtilObjeto.esNulo(entidad.getTipoServicio())
				? new TipoServicioDominio.Builder()
						.id(entidad.getTipoServicio().getId())
						.nombreServicio(entidad.getTipoServicio().getNombreServicio())
						.descripcion(entidad.getTipoServicio().getDescripcion())
						.build()
				: null;
		var parqueadero = !UtilObjeto.esNulo(entidad.getParqueadero())
				? new ParqueaderoDominio.Builder()
						.id(entidad.getParqueadero().getId())
						.nombreEstablecimiento(entidad.getParqueadero().getNombreEstablecimiento())
						.build()
				: null;
		return new ServicioDominio.Builder()
				.id(entidad.getId())
				.nombreServicio(entidad.getNombreServicio())
				.tipoServicio(tipoServicio)
				.parqueadero(parqueadero)
				.build();
	}
}
