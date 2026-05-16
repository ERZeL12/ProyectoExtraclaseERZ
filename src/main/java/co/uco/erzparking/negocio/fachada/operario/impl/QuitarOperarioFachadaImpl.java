package co.uco.erzparking.negocio.fachada.operario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.negocio.casouso.operario.QuitarOperarioCasoUso;
import co.uco.erzparking.negocio.casouso.operario.impl.QuitarOperarioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.negocio.fachada.operario.QuitarOperarioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarOperarioFachadaImpl implements QuitarOperarioFachada {

	private DAOFactory daoFactory;
	private QuitarOperarioCasoUso casoUso;

	public QuitarOperarioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarOperarioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final OperarioDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new OperarioDominio.Builder().id(datos.getId()).build();
			casoUso.ejecutar(dominio);
			daoFactory.confirmarTransaccion();
		} catch (ERZParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al quitar el operario", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}