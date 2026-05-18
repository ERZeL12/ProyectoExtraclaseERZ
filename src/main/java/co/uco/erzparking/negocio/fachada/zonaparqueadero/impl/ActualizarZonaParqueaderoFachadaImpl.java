package co.uco.erzparking.negocio.fachada.zonaparqueadero.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.ActualizarZonaParqueaderoCasoUso;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.impl.ActualizarZonaParqueaderoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ActualizarZonaParqueaderoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarZonaParqueaderoFachadaImpl implements ActualizarZonaParqueaderoFachada {

	private DAOFactory daoFactory;
	private ActualizarZonaParqueaderoCasoUso casoUso;

	public ActualizarZonaParqueaderoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarZonaParqueaderoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ZonaParqueaderoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new ZonaParqueaderoDominio.Builder()
					.id(datos.getId())
					.nombreZona(datos.getNombreZona())
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
			var dto = new ZonaParqueaderoDTO.Builder()
					.id(UUID.fromString("f71b7dc0-306a-4111-a652-3ebb9a619317"))
					.nombreZona("Zona A Actualizada")
					.build();
			new ActualizarZonaParqueaderoFachadaImpl().ejecutar(dto);
			System.out.println("ZonaParqueadero actualizada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
