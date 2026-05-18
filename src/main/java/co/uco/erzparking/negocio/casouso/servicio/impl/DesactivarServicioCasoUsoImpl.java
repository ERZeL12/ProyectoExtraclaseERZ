package co.uco.erzparking.negocio.casouso.servicio.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.servicio.DesactivarServicioCasoUso;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class DesactivarServicioCasoUsoImpl implements DesactivarServicioCasoUso {

	private DAOFactory daoFactory;

	public DesactivarServicioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ServicioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		desactivar(datos.getId());
	}

	private void validarIntegridadDatos(final ServicioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del servicio son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del servicio es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getServicioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El servicio no existe en el sistema");
		}
	}

	private void desactivar(final UUID id) {
		daoFactory.getServicioDAO().cambiarEstadoActual(id, false);
	}

}
