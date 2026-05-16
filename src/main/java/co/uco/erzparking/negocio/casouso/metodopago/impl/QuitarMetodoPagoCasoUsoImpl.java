package co.uco.erzparking.negocio.casouso.metodopago.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.metodopago.QuitarMetodoPagoCasoUso;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarMetodoPagoCasoUsoImpl implements QuitarMetodoPagoCasoUso {

	private DAOFactory daoFactory;

	public QuitarMetodoPagoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final MetodoPagoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final MetodoPagoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del metodoPago son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del metodoPago es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getMetodoPagoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El metodoPago no existe en el sistema");
		}
	}

	private void quitar(final MetodoPagoDominio datos) {
		daoFactory.getMetodoPagoDAO().eliminar(datos.getId());
	}
}
