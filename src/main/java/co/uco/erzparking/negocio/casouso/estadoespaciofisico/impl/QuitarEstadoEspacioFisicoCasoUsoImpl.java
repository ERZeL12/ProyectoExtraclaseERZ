package co.uco.erzparking.negocio.casouso.estadoespaciofisico.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.QuitarEstadoEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarEstadoEspacioFisicoCasoUsoImpl implements QuitarEstadoEspacioFisicoCasoUso {

	private DAOFactory daoFactory;

	public QuitarEstadoEspacioFisicoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final EstadoEspacioFisicoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final EstadoEspacioFisicoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del estadoEspacioFisico son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del estadoEspacioFisico es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getEstadoEspacioFisicoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El estadoEspacioFisico no existe en el sistema");
		}
	}

	private void quitar(final EstadoEspacioFisicoDominio datos) {
		daoFactory.getEstadoEspacioFisicoDAO().eliminar(datos.getId());
	}
}
