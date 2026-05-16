package co.uco.erzparking.negocio.casouso.usuario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.entidad.TipoDocumentoIdentificacionEntidad;
import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.negocio.casouso.usuario.RegistrarUsuarioCasoUso;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarUsuarioCasoUsoImpl implements RegistrarUsuarioCasoUso {

	private DAOFactory daoFactory;

	public RegistrarUsuarioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UsuarioDominio datos) {
		validarIntegridadDatos(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final UsuarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del usuario son obligatorios");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getUsuarioDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final UsuarioDominio datos) {
		var tipoDoc = new TipoDocumentoIdentificacionEntidad.Builder()
				.id(datos.getTipoDocumentoIdentificacion().getId())
				.build();
		var ciudad = new CiudadEntidad.Builder()
				.id(datos.getCiudad().getId())
				.build();
		var entidad = new UsuarioEntidad.Builder()
				.id(generarIdUnico())
				.tipoDocumentoIdentificacion(tipoDoc)
				.numeroIdentificacion(datos.getNumeroIdentificacion())
				.primerNombre(datos.getPrimerNombre())
				.segundoNombre(datos.getSegundoNombre())
				.primerApellido(datos.getPrimerApellido())
				.segundoApellido(datos.getSegundoApellido())
				.numeroTelefonico(datos.getNumeroTelefonico())
				.correoElectronico(datos.getCorreoElectronico())
				.ciudad(ciudad)
				.build();
		daoFactory.getUsuarioDAO().crear(entidad);
	}
}
