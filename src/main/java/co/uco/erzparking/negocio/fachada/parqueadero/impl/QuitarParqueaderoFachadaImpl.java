package co.uco.erzparking.negocio.fachada.parqueadero.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.parqueadero.QuitarParqueaderoCasoUso;
import co.uco.erzparking.negocio.casouso.parqueadero.impl.QuitarParqueaderoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.parqueadero.QuitarParqueaderoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarParqueaderoFachadaImpl implements QuitarParqueaderoFachada {

	private DAOFactory daoFactory;
	private QuitarParqueaderoCasoUso casoUso;

	public QuitarParqueaderoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarParqueaderoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ParqueaderoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new ParqueaderoDominio.Builder().id(datos.getId()).build();
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
			var dto = new ParqueaderoDTO.Builder()
					.id(UUID.fromString("6f34728b-d0ee-4440-bd24-38b29b2ed427"))
					.build();
			new QuitarParqueaderoFachadaImpl().ejecutar(dto);
			System.out.println("Parqueadero eliminado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
