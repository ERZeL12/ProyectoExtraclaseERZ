package co.uco.erzparking.negocio.casouso.parqueadero.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.parqueadero.ConsultarTodosParqueaderosCasoUso;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosParqueaderosCasoUsoImpl implements ConsultarTodosParqueaderosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosParqueaderosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<ParqueaderoDominio> ejecutar(final ParqueaderoDominio datos) {
		return consultarTodos(datos);
	}

	private List<ParqueaderoDominio> consultarTodos(final ParqueaderoDominio filtro) {
		var entidades = daoFactory.getParqueaderoDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new ParqueaderoEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private ParqueaderoDominio mapearAdominio(final ParqueaderoEntidad entidad) {
		var ciudad = !UtilObjeto.esNulo(entidad.getCiudad())
				? new CiudadDominio.Builder()
						.id(entidad.getCiudad().getId())
						.nombre(entidad.getCiudad().getNombre())
						.build()
				: null;
		return new ParqueaderoDominio.Builder()
				.id(entidad.getId())
				.nombreEstablecimiento(entidad.getNombreEstablecimiento())
				.numeroTelefonico(entidad.getNumeroTelefonico())
				.correoElectronico(entidad.getCorreoElectronico())
				.direccionEstablecimiento(entidad.getDireccionEstablecimiento())
				.ciudad(ciudad)
				.build();
	}
}
