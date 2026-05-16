package co.uco.erzparking.negocio.casouso.usuariovehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.QuitarUsuarioVehiculoCasoUso;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarUsuarioVehiculoCasoUsoImpl implements QuitarUsuarioVehiculoCasoUso {

	private DAOFactory daoFactory;

	public QuitarUsuarioVehiculoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UsuarioVehiculoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final UsuarioVehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del usuarioVehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del usuarioVehiculo es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getUsuarioVehiculoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El usuarioVehiculo no existe en el sistema");
		}
	}

	private void quitar(final UsuarioVehiculoDominio datos) {
		daoFactory.getUsuarioVehiculoDAO().eliminar(datos.getId());
	}
}
