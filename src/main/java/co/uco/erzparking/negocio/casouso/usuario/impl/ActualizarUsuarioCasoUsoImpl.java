package co.uco.erzparking.negocio.casouso.usuario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.negocio.casouso.usuario.ActualizarUsuarioCasoUso;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarUsuarioCasoUsoImpl implements ActualizarUsuarioCasoUso {

	private DAOFactory daoFactory;

	public ActualizarUsuarioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UsuarioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final UsuarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del usuario son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del usuario es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getPrimerNombre()) || datos.getPrimerNombre().isEmpty()) {
			throw ERZParkingExcepcion.crear("El primer nombre del usuario es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getPrimerApellido()) || datos.getPrimerApellido().isEmpty()) {
			throw ERZParkingExcepcion.crear("El primer apellido del usuario es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getCiudad()) || UtilObjeto.esNulo(datos.getCiudad().getId())) {
			throw ERZParkingExcepcion.crear("La ciudad del usuario es obligatoria");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getUsuarioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El usuario que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final UsuarioDominio datos) {
		var ciudad = new CiudadEntidad.Builder()
				.id(datos.getCiudad().getId())
				.build();
		var entidad = new UsuarioEntidad.Builder()
				.id(datos.getId())
				.primerNombre(datos.getPrimerNombre())
				.segundoNombre(datos.getSegundoNombre())
				.primerApellido(datos.getPrimerApellido())
				.segundoApellido(datos.getSegundoApellido())
				.numeroTelefonico(datos.getNumeroTelefonico())
				.correoElectronico(datos.getCorreoElectronico())
				.ciudad(ciudad)
				.build();
		daoFactory.getUsuarioDAO().actualizar(datos.getId(), entidad);
	}
}
