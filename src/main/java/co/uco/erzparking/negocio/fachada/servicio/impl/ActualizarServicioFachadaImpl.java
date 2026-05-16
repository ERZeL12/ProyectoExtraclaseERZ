package co.uco.erzparking.negocio.fachada.servicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.negocio.casouso.servicio.ActualizarServicioCasoUso;
import co.uco.erzparking.negocio.casouso.servicio.impl.ActualizarServicioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.fachada.servicio.ActualizarServicioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarServicioFachadaImpl implements ActualizarServicioFachada {

	private DAOFactory daoFactory;
	private ActualizarServicioCasoUso casoUso;

	public ActualizarServicioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarServicioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ServicioDTO datos) {
		try {

			var dominio = new ServicioDominio.Builder()
					.id(datos.getId())
					.nombreServicio(datos.getNombreServicio())
					.build();
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
