package co.uco.erzparking.negocio.fachada.metodopago.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.MetodoPagoDTO;
import co.uco.erzparking.negocio.casouso.metodopago.ActualizarMetodoPagoCasoUso;
import co.uco.erzparking.negocio.casouso.metodopago.impl.ActualizarMetodoPagoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.negocio.fachada.metodopago.ActualizarMetodoPagoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarMetodoPagoFachadaImpl implements ActualizarMetodoPagoFachada {

	private DAOFactory daoFactory;
	private ActualizarMetodoPagoCasoUso casoUso;

	public ActualizarMetodoPagoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarMetodoPagoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final MetodoPagoDTO datos) {
		try {

			var dominio = new MetodoPagoDominio.Builder()
					.id(datos.getId())
					.nombreMetodoPago(datos.getNombreMetodoPago())
					.descripcion(datos.getDescripcion())
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
