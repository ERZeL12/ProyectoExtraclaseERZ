package co.uco.erzparking.negocio.casouso.tarifa.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.tarifa.DesactivarTarifaCasoUso;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class DesactivarTarifaCasoUsoImpl implements DesactivarTarifaCasoUso {

	private DAOFactory daoFactory;

	public DesactivarTarifaCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TarifaDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		desactivar(datos.getId());
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

	private void desactivar(final UUID id) {
		daoFactory.getTarifaDAO().cambiarEstadoActual(id, false);
	}

}
