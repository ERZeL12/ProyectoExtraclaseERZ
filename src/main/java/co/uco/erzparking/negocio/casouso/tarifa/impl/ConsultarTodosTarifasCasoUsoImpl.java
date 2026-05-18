package co.uco.erzparking.negocio.casouso.tarifa.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TarifaEntidad;
import co.uco.erzparking.negocio.casouso.tarifa.ConsultarTodosTarifasCasoUso;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosTarifasCasoUsoImpl implements ConsultarTodosTarifasCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosTarifasCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<TarifaDominio> ejecutar(final TarifaDominio datos) {
		return consultarTodos(datos);
	}

	private List<TarifaDominio> consultarTodos(final TarifaDominio filtro) {
		var entidades = daoFactory.getTarifaDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new TarifaEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private TarifaDominio mapearAdominio(final TarifaEntidad entidad) {
		var tipoVehiculo = !UtilObjeto.esNulo(entidad.getTipoVehiculo())
				? new TipoVehiculoDominio.Builder()
						.id(entidad.getTipoVehiculo().getId())
						.nombreVehiculo(entidad.getTipoVehiculo().getNombreVehiculo())
						.descripcion(entidad.getTipoVehiculo().getDescripcion())
						.build()
				: null;
		var servicio = !UtilObjeto.esNulo(entidad.getServicio())
				? new ServicioDominio.Builder()
						.id(entidad.getServicio().getId())
						.nombreServicio(entidad.getServicio().getNombreServicio())
						.build()
				: null;
		return new TarifaDominio.Builder()
				.id(entidad.getId())
				.valorServicio(entidad.getValorServicio())
				.fechaInicioVigenciaTarifa(entidad.getFechaInicioVigenciaTarifa())
				.fechaFinVigenciaTarifa(entidad.getFechaFinVigenciaTarifa())
				.tipoVehiculo(tipoVehiculo)
				.servicio(servicio)
				.build();
	}
}
