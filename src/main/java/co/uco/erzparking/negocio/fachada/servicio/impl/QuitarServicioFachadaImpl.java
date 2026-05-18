package co.uco.erzparking.negocio.fachada.servicio.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.negocio.casouso.servicio.QuitarServicioCasoUso;
import co.uco.erzparking.negocio.casouso.servicio.impl.QuitarServicioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.fachada.servicio.QuitarServicioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarServicioFachadaImpl implements QuitarServicioFachada {

	private DAOFactory daoFactory;
	private QuitarServicioCasoUso casoUso;

	public QuitarServicioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarServicioCasoUsoImpl(daoFactory);
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

	public static void main(final String[] args) {
		try {
			var dto = new ServicioDTO.Builder()
					.id(UUID.fromString("54d2035f-120c-47e4-a54d-06b149349c11"))
					.build();
			new QuitarServicioFachadaImpl().ejecutar(dto);
			System.out.println("Servicio eliminado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
