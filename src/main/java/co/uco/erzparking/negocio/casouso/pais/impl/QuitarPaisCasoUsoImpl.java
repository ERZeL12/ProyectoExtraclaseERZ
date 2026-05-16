package co.uco.erzparking.negocio.casouso.pais.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.pais.QuitarPaisCasoUso;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarPaisCasoUsoImpl implements QuitarPaisCasoUso {

	private DAOFactory daoFactory;

	public QuitarPaisCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final PaisDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final PaisDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del pais son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del pais es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getPaisDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El pais no existe en el sistema");
		}
	}

	private void quitar(final PaisDominio datos) {
		daoFactory.getPaisDAO().eliminar(datos.getId());
	}
}
