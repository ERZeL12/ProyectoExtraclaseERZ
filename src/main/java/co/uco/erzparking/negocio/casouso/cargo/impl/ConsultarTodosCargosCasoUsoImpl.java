package co.uco.erzparking.negocio.casouso.cargo.impl;

import java.util.List;
import java.util.stream.Collectors;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.negocio.casouso.cargo.ConsultarTodosCargosCasoUso;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosCargosCasoUsoImpl implements ConsultarTodosCargosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosCargosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<CargoDominio> ejecutar(final CargoDominio datos) {
		return consultarTodos(datos);
	}

	private List<CargoDominio> consultarTodos(final CargoDominio filtro) {
		var entidades = daoFactory.getCargoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new CargoEntidad.Builder().nombreCargo(filtro.getNombreCargo()).build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(Collectors.toList());
	}

	private CargoDominio mapearAdominio(final CargoEntidad entidad) {
		var parqueadero = new ParqueaderoDominio.Builder()
				.id(entidad.getParqueadero().getId())
				.nombreEstablecimiento(entidad.getParqueadero().getNombreEstablecimiento())
				.build();
		return new CargoDominio.Builder()
				.id(entidad.getId())
				.nombreCargo(entidad.getNombreCargo())
				.descripcion(entidad.getDescripcion())
				.parqueadero(parqueadero)
				.build();
	}

}
