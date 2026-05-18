package co.uco.erzparking.negocio.casouso.usuariovehiculo.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.ConsultarTodosUsuarioVehiculosCasoUso;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosUsuarioVehiculosCasoUsoImpl implements ConsultarTodosUsuarioVehiculosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosUsuarioVehiculosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<UsuarioVehiculoDominio> ejecutar(final UsuarioVehiculoDominio datos) {
		return consultarTodos(datos);
	}

	private List<UsuarioVehiculoDominio> consultarTodos(final UsuarioVehiculoDominio filtro) {
		var entidades = daoFactory.getUsuarioVehiculoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new UsuarioVehiculoEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private UsuarioVehiculoDominio mapearAdominio(final UsuarioVehiculoEntidad entidad) {
		var usuario = !UtilObjeto.esNulo(entidad.getUsuario())
				? new UsuarioDominio.Builder()
						.id(entidad.getUsuario().getId())
						.primerNombre(entidad.getUsuario().getPrimerNombre())
						.primerApellido(entidad.getUsuario().getPrimerApellido())
						.numeroIdentificacion(entidad.getUsuario().getNumeroIdentificacion())
						.build()
				: null;
		var vehiculo = !UtilObjeto.esNulo(entidad.getVehiculo())
				? new VehiculoDominio.Builder()
						.id(entidad.getVehiculo().getId())
						.placaVehiculo(entidad.getVehiculo().getPlacaVehiculo())
						.build()
				: null;
		return new UsuarioVehiculoDominio.Builder()
				.id(entidad.getId())
				.usuario(usuario)
				.vehiculo(vehiculo)
				.build();
	}
}
