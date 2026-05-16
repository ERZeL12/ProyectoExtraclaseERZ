package co.uco.erzparking.negocio.fachada.metodopago.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.MetodoPagoDTO;
import co.uco.erzparking.negocio.casouso.metodopago.QuitarMetodoPagoCasoUso;
import co.uco.erzparking.negocio.casouso.metodopago.impl.QuitarMetodoPagoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.negocio.fachada.metodopago.QuitarMetodoPagoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarMetodoPagoFachadaImpl implements QuitarMetodoPagoFachada {

	private DAOFactory daoFactory;
	private QuitarMetodoPagoCasoUso casoUso;

	public QuitarMetodoPagoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarMetodoPagoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final MetodoPagoDTO datos) {
		try {

			var dominio = new MetodoPagoDominio.Builder().id(datos.getId()).build();
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
