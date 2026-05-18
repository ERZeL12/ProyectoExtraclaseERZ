package co.uco.erzparking.negocio.fachada.servicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.negocio.casouso.servicio.DesactivarServicioCasoUso;
import co.uco.erzparking.negocio.casouso.servicio.impl.DesactivarServicioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.fachada.servicio.DesactivarServicioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class DesactivarServicioFachadaImpl implements DesactivarServicioFachada {

	private DAOFactory daoFactory;
	private DesactivarServicioCasoUso casoUso;

	public DesactivarServicioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new DesactivarServicioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ServicioDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new ServicioDominio.Builder().id(datos.getId()).build();
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
