package co.uco.erzparking.negocio.casouso.vehiculo.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.negocio.casouso.vehiculo.ConsultarTodosVehiculosCasoUso;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosVehiculosCasoUsoImpl implements ConsultarTodosVehiculosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosVehiculosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<VehiculoDominio> ejecutar(final VehiculoDominio datos) {
		return consultarTodos(datos);
	}

	private List<VehiculoDominio> consultarTodos(final VehiculoDominio filtro) {
		var entidades = daoFactory.getVehiculoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new VehiculoEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private VehiculoDominio mapearAdominio(final VehiculoEntidad entidad) {
		var tipoVehiculo = !UtilObjeto.esNulo(entidad.getTipoVehiculo())
				? new TipoVehiculoDominio.Builder()
						.id(entidad.getTipoVehiculo().getId())
						.nombreVehiculo(entidad.getTipoVehiculo().getNombreVehiculo())
						.build()
				: null;
		return new VehiculoDominio.Builder()
				.id(entidad.getId())
				.placaVehiculo(entidad.getPlacaVehiculo())
				.tipoVehiculo(tipoVehiculo)
				.build();
	}
}
