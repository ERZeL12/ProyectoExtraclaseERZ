package co.uco.erzparking.negocio.fachada.zonaparqueadero.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.QuitarZonaParqueaderoCasoUso;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.impl.QuitarZonaParqueaderoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.QuitarZonaParqueaderoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarZonaParqueaderoFachadaImpl implements QuitarZonaParqueaderoFachada {

	private DAOFactory daoFactory;
	private QuitarZonaParqueaderoCasoUso casoUso;

	public QuitarZonaParqueaderoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarZonaParqueaderoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ZonaParqueaderoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new ZonaParqueaderoDominio.Builder().id(datos.getId()).build();
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
			var dto = new ZonaParqueaderoDTO.Builder()
					.id(UUID.fromString("f71b7dc0-306a-4111-a652-3ebb9a619317"))
					.build();
			new QuitarZonaParqueaderoFachadaImpl().ejecutar(dto);
			System.out.println("ZonaParqueadero eliminada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
