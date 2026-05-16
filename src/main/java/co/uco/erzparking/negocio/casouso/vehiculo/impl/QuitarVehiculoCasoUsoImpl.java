package co.uco.erzparking.negocio.casouso.vehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.vehiculo.QuitarVehiculoCasoUso;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

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

	private void quitar(final VehiculoDominio datos) {
		daoFactory.getVehiculoDAO().eliminar(datos.getId());
	}
}
