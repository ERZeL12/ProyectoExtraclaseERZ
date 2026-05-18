package co.uco.erzparking.negocio.casouso.tipovehiculo.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.negocio.casouso.tipovehiculo.ConsultarTodosTipoVehiculosCasoUso;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosTipoVehiculosCasoUsoImpl implements ConsultarTodosTipoVehiculosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosTipoVehiculosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<TipoVehiculoDominio> ejecutar(final TipoVehiculoDominio datos) {
		return consultarTodos(datos);
	}

	private List<TipoVehiculoDominio> consultarTodos(final TipoVehiculoDominio filtro) {
		var entidades = daoFactory.getTipoVehiculoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new TipoVehiculoEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private TipoVehiculoDominio mapearAdominio(final TipoVehiculoEntidad entidad) {
		return new TipoVehiculoDominio.Builder()
				.id(entidad.getId())
				.nombreVehiculo(entidad.getNombreVehiculo())
				.descripcion(entidad.getDescripcion())
				.build();
	}
}
