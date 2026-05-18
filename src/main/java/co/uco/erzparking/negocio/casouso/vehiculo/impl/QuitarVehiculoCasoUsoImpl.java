package co.uco.erzparking.negocio.casouso.vehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EntradaEntidad;
import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.negocio.casouso.vehiculo.QuitarVehiculoCasoUso;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarVehiculoCasoUsoImpl implements QuitarVehiculoCasoUso {

	private DAOFactory daoFactory;

	public QuitarVehiculoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final VehiculoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final VehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del vehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del vehiculo es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getVehiculoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El vehiculo no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var vehiculo = new VehiculoEntidad.Builder().id(id).build();
		var asociaciones = daoFactory.getUsuarioVehiculoDAO().consultarPorFiltro(new UsuarioVehiculoEntidad.Builder().vehiculo(vehiculo).build());
		if (!asociaciones.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el vehiculo porque tiene asociaciones de propietario registradas");
		}
		var entradas = daoFactory.getEntradaDAO().consultarPorFiltro(new EntradaEntidad.Builder().vehiculo(vehiculo).build());
		if (!entradas.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el vehiculo porque tiene entradas registradas");
		}
	}

	private void quitar(final VehiculoDominio datos) {
		daoFactory.getVehiculoDAO().eliminar(datos.getId());
	}
}
