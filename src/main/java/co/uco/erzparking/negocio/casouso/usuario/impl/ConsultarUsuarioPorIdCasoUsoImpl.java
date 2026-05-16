package co.uco.erzparking.negocio.casouso.usuario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.usuario.ConsultarUsuarioPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarUsuarioPorIdCasoUsoImpl implements ConsultarUsuarioPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarUsuarioPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public UsuarioDominio ejecutar(final UsuarioDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final UsuarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del usuario son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del usuario es obligatorio para consultar");
		}
	}

	private UsuarioDominio consultar(final UsuarioDominio datos) {
		var entidad = daoFactory.getUsuarioDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El usuario no existe en el sistema");
		}
		var tipoDocumentoIdentificacion = !UtilObjeto.esNulo(entidad.getTipoDocumentoIdentificacion())
				? new TipoDocumentoIdentificacionDominio.Builder()
						.id(entidad.getTipoDocumentoIdentificacion().getId())
						.build()
				: null;
		var ciudad = !UtilObjeto.esNulo(entidad.getCiudad())
				? new CiudadDominio.Builder().id(entidad.getCiudad().getId()).build()
				: null;
		return new UsuarioDominio.Builder()
				.id(entidad.getId())
				.tipoDocumentoIdentificacion(tipoDocumentoIdentificacion)
				.numeroIdentificacion(entidad.getNumeroIdentificacion())
				.primerNombre(entidad.getPrimerNombre())
				.segundoNombre(entidad.getSegundoNombre())
				.primerApellido(entidad.getPrimerApellido())
				.segundoApellido(entidad.getSegundoApellido())
				.numeroTelefonico(entidad.getNumeroTelefonico())
				.correoElectronico(entidad.getCorreoElectronico())
				.ciudad(ciudad)
				.build();
	}
}
