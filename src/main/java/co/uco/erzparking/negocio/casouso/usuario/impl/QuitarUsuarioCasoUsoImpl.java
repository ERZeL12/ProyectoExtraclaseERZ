package co.uco.erzparking.negocio.casouso.usuario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;
import co.uco.erzparking.negocio.casouso.usuario.QuitarUsuarioCasoUso;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarUsuarioCasoUsoImpl implements QuitarUsuarioCasoUso {

	private DAOFactory daoFactory;

	public QuitarUsuarioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UsuarioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final UsuarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del usuario son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del usuario es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getUsuarioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El usuario no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var usuario = new UsuarioEntidad.Builder().id(id).build();
		var asociaciones = daoFactory.getUsuarioVehiculoDAO().consultarPorFiltro(new UsuarioVehiculoEntidad.Builder().usuario(usuario).build());
		if (!asociaciones.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el usuario porque tiene vehiculos asociados");
		}
	}

	private void quitar(final UsuarioDominio datos) {
		daoFactory.getUsuarioDAO().eliminar(datos.getId());
	}

}
