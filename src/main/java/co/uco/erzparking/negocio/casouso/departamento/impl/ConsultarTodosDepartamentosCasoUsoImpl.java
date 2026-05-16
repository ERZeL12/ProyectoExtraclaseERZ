package co.uco.erzparking.negocio.casouso.departamento.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.DepartamentoEntidad;
import co.uco.erzparking.negocio.casouso.departamento.ConsultarTodosDepartamentosCasoUso;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosDepartamentosCasoUsoImpl implements ConsultarTodosDepartamentosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosDepartamentosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<DepartamentoDominio> ejecutar(final DepartamentoDominio datos) {
		return consultarTodos(datos);
	}

	private List<DepartamentoDominio> consultarTodos(final DepartamentoDominio filtro) {
		var entidades = daoFactory.getDepartamentoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new DepartamentoEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private DepartamentoDominio mapearAdominio(final DepartamentoEntidad entidad) {
		var pais = new PaisDominio.Builder()
				.id(entidad.getPais().getId())
				.nombre(entidad.getPais().getNombre())
				.build();
		return new DepartamentoDominio.Builder()
				.id(entidad.getId())
				.nombre(entidad.getNombre())
				.pais(pais)
				.build();
	}
}
