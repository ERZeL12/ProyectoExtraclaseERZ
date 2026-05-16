package co.uco.erzparking.negocio.casouso.operario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.operario.ConsultarOperarioPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarOperarioPorIdCasoUsoImpl implements ConsultarOperarioPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarOperarioPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public OperarioDominio ejecutar(final OperarioDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final OperarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del operario son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del operario es obligatorio para consultar");
		}
	}

	private OperarioDominio consultar(final OperarioDominio datos) {
		var entidad = daoFactory.getOperarioDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El operario no existe en el sistema");
		}
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