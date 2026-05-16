package co.uco.erzparking.negocio.fachada.metodopago.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.MetodoPagoDTO;
import co.uco.erzparking.negocio.casouso.metodopago.RegistrarMetodoPagoCasoUso;
import co.uco.erzparking.negocio.casouso.metodopago.impl.RegistrarMetodoPagoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.negocio.fachada.metodopago.RegistrarMetodoPagoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarMetodoPagoFachadaImpl implements RegistrarMetodoPagoFachada {

	private DAOFactory daoFactory;
	private RegistrarMetodoPagoCasoUso casoUso;

	public RegistrarMetodoPagoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarMetodoPagoCasoUsoImpl(daoFactory);
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

	public static void main(final String[] args) {
		try {
			var dto = new MetodoPagoDTO.Builder()
					.nombreMetodoPago("Efectivo")
					.descripcion("Pago en efectivo en la caja del parqueadero")
					.build();
			new RegistrarMetodoPagoFachadaImpl().ejecutar(dto);
			System.out.println("MetodoPago registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
