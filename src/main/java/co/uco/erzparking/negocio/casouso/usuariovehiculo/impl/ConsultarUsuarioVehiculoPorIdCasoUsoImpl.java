package co.uco.erzparking.negocio.casouso.usuariovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.ConsultarUsuarioVehiculoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarUsuarioVehiculoPorIdCasoUsoImpl implements ConsultarUsuarioVehiculoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarUsuarioVehiculoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public UsuarioVehiculoDominio ejecutar(final UsuarioVehiculoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final UsuarioVehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del usuarioVehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del usuarioVehiculo es obligatorio para consultar");
		}
	}

	private UsuarioVehiculoDominio consultar(final UsuarioVehiculoDominio datos) {
		var entidad = daoFactory.getUsuarioVehiculoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El usuarioVehiculo no existe en el sistema");
		}
		var usuario = !UtilObjeto.esNulo(entidad.getUsuario())
				? new UsuarioDominio.Builder().id(entidad.getUsuario().getId()).build()
				: null;
		var vehiculo = !UtilObjeto.esNulo(entidad.getVehiculo())
				? new VehiculoDominio.Builder().id(entidad.getVehiculo().getId()).build()
				: null;
		return new UsuarioVehiculoDominio.Builder()
				.id(entidad.getId())
				.usuario(usuario)
				.vehiculo(vehiculo)
				.build();
	}
}
