package co.uco.erzparking.negocio.casouso.ciudad.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.negocio.casouso.ciudad.ConsultarTodosCiudadsCasoUso;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosCiudadsCasoUsoImpl implements ConsultarTodosCiudadsCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosCiudadsCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<CiudadDominio> ejecutar(final CiudadDominio datos) {
		return consultarTodos(datos);
	}

	private List<CiudadDominio> consultarTodos(final CiudadDominio filtro) {
		var entidades = daoFactory.getCiudadDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new CiudadEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private CiudadDominio mapearAdominio(final CiudadEntidad entidad) {
		var pais = new PaisDominio.Builder()
				.id(entidad.getDepartamento().getPais().getId())
				.nombre(entidad.getDepartamento().getPais().getNombre())
				.build();
		var departamento = new DepartamentoDominio.Builder()
				.id(entidad.getDepartamento().getId())
				.nombre(entidad.getDepartamento().getNombre())
				.pais(pais)
				.build();
		return new CiudadDominio.Builder()
				.id(entidad.getId())
				.nombre(entidad.getNombre())
				.departamento(departamento)
				.build();
	}
}
