package co.uco.erzparking.negocio.fachada.operario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.negocio.casouso.operario.DesactivarOperarioCasoUso;
import co.uco.erzparking.negocio.casouso.operario.impl.DesactivarOperarioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.negocio.fachada.operario.DesactivarOperarioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class DesactivarOperarioFachadaImpl implements DesactivarOperarioFachada {

	private DAOFactory daoFactory;
	private DesactivarOperarioCasoUso casoUso;

	public DesactivarOperarioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new DesactivarOperarioCasoUsoImpl(daoFactory);
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
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
