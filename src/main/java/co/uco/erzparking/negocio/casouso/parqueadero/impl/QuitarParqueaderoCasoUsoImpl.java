package co.uco.erzparking.negocio.casouso.parqueadero.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.parqueadero.QuitarParqueaderoCasoUso;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarParqueaderoCasoUsoImpl implements QuitarParqueaderoCasoUso {

	private DAOFactory daoFactory;

	public QuitarParqueaderoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ParqueaderoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final ParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del parqueadero son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del parqueadero es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getParqueaderoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El parqueadero no existe en el sistema");
		}
	}

	private void quitar(final ParqueaderoDominio datos) {
		daoFactory.getParqueaderoDAO().eliminar(datos.getId());
	}
}
