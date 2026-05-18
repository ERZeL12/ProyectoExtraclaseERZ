package co.uco.erzparking.negocio.casouso.tarifa.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.tarifa.ActivarTarifaCasoUso;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActivarTarifaCasoUsoImpl implements ActivarTarifaCasoUso {

	private DAOFactory daoFactory;

	public ActivarTarifaCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TarifaDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		activar(datos.getId());
	}

	private void validarIntegridadDatos(final TarifaDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos de la tarifa son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador de la tarifa es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getTarifaDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("La tarifa no existe en el sistema");
		}
	}

	private void activar(final UUID id) {
		daoFactory.getTarifaDAO().cambiarEstadoActual(id, true);
	}

}
