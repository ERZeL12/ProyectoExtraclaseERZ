package co.uco.erzparking.negocio.casouso.metodopago.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.MetodoPagoEntidad;
import co.uco.erzparking.negocio.casouso.metodopago.ConsultarTodosMetodoPagosCasoUso;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosMetodoPagosCasoUsoImpl implements ConsultarTodosMetodoPagosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosMetodoPagosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<MetodoPagoDominio> ejecutar(final MetodoPagoDominio datos) {
		return consultarTodos(datos);
	}

	private List<MetodoPagoDominio> consultarTodos(final MetodoPagoDominio filtro) {
		var entidades = daoFactory.getMetodoPagoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new MetodoPagoEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private MetodoPagoDominio mapearAdominio(final MetodoPagoEntidad entidad) {
		return new MetodoPagoDominio.Builder()
				.id(entidad.getId())
				.nombreMetodoPago(entidad.getNombreMetodoPago())
				.descripcion(entidad.getDescripcion())
				.build();
	}
}
