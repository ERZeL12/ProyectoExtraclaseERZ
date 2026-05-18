package co.uco.erzparking.negocio.casouso.operario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.operario.ActivarOperarioCasoUso;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActivarOperarioCasoUsoImpl implements ActivarOperarioCasoUso {

	private DAOFactory daoFactory;

	public ActivarOperarioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final OperarioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		activar(datos.getId());
	}

	private void validarIntegridadDatos(final OperarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del operario son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del operario es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getOperarioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El operario no existe en el sistema");
		}
	}

	private void activar(final UUID id) {
		daoFactory.getOperarioDAO().cambiarEstadoActual(id, true);
	}

}
