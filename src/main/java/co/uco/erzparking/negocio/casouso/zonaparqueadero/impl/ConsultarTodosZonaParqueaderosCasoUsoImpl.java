package co.uco.erzparking.negocio.casouso.zonaparqueadero.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ZonaParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.ConsultarTodosZonaParqueaderosCasoUso;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosZonaParqueaderosCasoUsoImpl implements ConsultarTodosZonaParqueaderosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosZonaParqueaderosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<ZonaParqueaderoDominio> ejecutar(final ZonaParqueaderoDominio datos) {
		return consultarTodos(datos);
	}

	private List<ZonaParqueaderoDominio> consultarTodos(final ZonaParqueaderoDominio filtro) {
		var entidades = daoFactory.getZonaParqueaderoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new ZonaParqueaderoEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private ZonaParqueaderoDominio mapearAdominio(final ZonaParqueaderoEntidad entidad) {
		var parqueadero = !UtilObjeto.esNulo(entidad.getParqueadero())
				? new ParqueaderoDominio.Builder().id(entidad.getParqueadero().getId()).build()
				: null;
		return new ZonaParqueaderoDominio.Builder()
				.id(entidad.getId())
				.nombreZona(entidad.getNombreZona())
				.parqueadero(parqueadero)
				.build();
	}
}
