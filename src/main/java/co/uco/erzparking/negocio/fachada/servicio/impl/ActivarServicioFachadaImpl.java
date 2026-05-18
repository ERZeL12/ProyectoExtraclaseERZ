package co.uco.erzparking.negocio.fachada.servicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.negocio.casouso.servicio.ActivarServicioCasoUso;
import co.uco.erzparking.negocio.casouso.servicio.impl.ActivarServicioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.fachada.servicio.ActivarServicioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActivarServicioFachadaImpl implements ActivarServicioFachada {

	private DAOFactory daoFactory;
	private ActivarServicioCasoUso casoUso;

	public ActivarServicioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActivarServicioCasoUsoImpl(daoFactory);
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
