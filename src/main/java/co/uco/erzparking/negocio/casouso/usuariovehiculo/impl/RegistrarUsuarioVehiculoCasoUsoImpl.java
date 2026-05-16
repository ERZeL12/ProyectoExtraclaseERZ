package co.uco.erzparking.negocio.casouso.usuariovehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.RegistrarUsuarioVehiculoCasoUso;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarUsuarioVehiculoCasoUsoImpl implements RegistrarUsuarioVehiculoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarUsuarioVehiculoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UsuarioVehiculoDominio datos) {
		validarIntegridadDatos(datos);
		validarDependencias(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final UsuarioVehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos de la asociacion usuario vehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getUsuario()) || UtilObjeto.esNulo(datos.getUsuario().getId())) {
			throw ERZParkingExcepcion.crear("El usuario es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getVehiculo()) || UtilObjeto.esNulo(datos.getVehiculo().getId())) {
			throw ERZParkingExcepcion.crear("El vehiculo es obligatorio");
		}
	}

	private void validarDependencias(final UsuarioVehiculoDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getUsuarioDAO().consultarPorId(datos.getUsuario().getId()))) {
			throw ERZParkingExcepcion.crear("El usuario no existe en el sistema");
		}
		if (UtilObjeto.esNulo(daoFactory.getVehiculoDAO().consultarPorId(datos.getVehiculo().getId()))) {
			throw ERZParkingExcepcion.crear("El vehiculo no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getUsuarioVehiculoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final UsuarioVehiculoDominio datos) {
		var usuario = new UsuarioEntidad.Builder()
				.id(datos.getUsuario().getId())
				.build();
		var vehiculo = new VehiculoEntidad.Builder()
				.id(datos.getVehiculo().getId())
				.build();
		var entidad = new UsuarioVehiculoEntidad.Builder()
				.id(generarIdUnico())
				.usuario(usuario)
				.vehiculo(vehiculo)
				.build();
		daoFactory.getUsuarioVehiculoDAO().crear(entidad);
	}

}
