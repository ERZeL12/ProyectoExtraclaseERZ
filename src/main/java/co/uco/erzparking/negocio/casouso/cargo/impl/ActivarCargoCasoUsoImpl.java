package co.uco.erzparking.negocio.casouso.cargo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.cargo.ActivarCargoCasoUso;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActivarCargoCasoUsoImpl implements ActivarCargoCasoUso {

	private DAOFactory daoFactory;

	public ActivarCargoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final CargoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		activar(datos.getId());
	}

	private void validarIntegridadDatos(final CargoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del cargo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del cargo es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getCargoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El cargo no existe en el sistema");
		}
	}

	private void activar(final UUID id) {
		daoFactory.getCargoDAO().cambiarEstadoActual(id, true);
	}

}
