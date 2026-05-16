package co.uco.erzparking.negocio.casouso.operario.impl;

import java.util.List;
import java.util.stream.Collectors;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.negocio.casouso.operario.ConsultarTodosOperariosCasoUso;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTodosOperariosCasoUsoImpl implements ConsultarTodosOperariosCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosOperariosCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<OperarioDominio> ejecutar(final OperarioDominio datos) {
		return consultarTodos();
	}

	private List<OperarioDominio> consultarTodos() {
		var entidades = daoFactory.getOperarioDAO().consultarTodos();
		if (UtilObjeto.esNulo(entidades)) {
			throw ERZParkingExcepcion.crear("Error al consultar los operarios");
		}
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(Collectors.toList());
	}

	private OperarioDominio mapearAdominio(final OperarioEntidad entidad) {
		var tipoDocumentoIdentificacion = !UtilObjeto.esNulo(entidad.getTipoDocumentoIdentificacion())
				? new TipoDocumentoIdentificacionDominio.Builder()
						.id(entidad.getTipoDocumentoIdentificacion().getId())
						.build()
				: null;
		var cargo = !UtilObjeto.esNulo(entidad.getCargo())
				? new CargoDominio.Builder().id(entidad.getCargo().getId()).build()
				: null;
		var parqueadero = !UtilObjeto.esNulo(entidad.getParqueadero())
				? new ParqueaderoDominio.Builder().id(entidad.getParqueadero().getId()).build()
				: null;
		return new OperarioDominio.Builder()
				.id(entidad.getId())
				.tipoDocumentoIdentificacion(tipoDocumentoIdentificacion)
				.numeroIdentificacion(entidad.getNumeroIdentificacion())
				.primerNombre(entidad.getPrimerNombre())
				.segundoNombre(entidad.getSegundoNombre())
				.primerApellido(entidad.getPrimerApellido())
				.segundoApellido(entidad.getSegundoApellido())
				.numeroTelefonico(entidad.getNumeroTelefonico())
				.cargo(cargo)
				.parqueadero(parqueadero)
				.build();
	}

}