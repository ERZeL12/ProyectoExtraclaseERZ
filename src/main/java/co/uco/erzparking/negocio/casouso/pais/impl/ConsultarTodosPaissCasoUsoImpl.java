package co.uco.erzparking.negocio.casouso.pais.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.PaisEntidad;
import co.uco.erzparking.negocio.casouso.pais.ConsultarTodosPaissCasoUso;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosPaissCasoUsoImpl implements ConsultarTodosPaissCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosPaissCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<PaisDominio> ejecutar(final PaisDominio datos) {
		return consultarTodos(datos);
	}

	private List<PaisDominio> consultarTodos(final PaisDominio filtro) {
		var entidades = daoFactory.getPaisDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new PaisEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private PaisDominio mapearAdominio(final PaisEntidad entidad) {
		return new PaisDominio.Builder()
				.id(entidad.getId())
				.nombre(entidad.getNombre())
				.build();
	}
}
