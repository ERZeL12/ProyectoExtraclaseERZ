package co.uco.erzparking.negocio.fachada.zonaparqueadero.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.RegistrarZonaParqueaderoCasoUso;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.impl.RegistrarZonaParqueaderoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.RegistrarZonaParqueaderoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarZonaParqueaderoFachadaImpl implements RegistrarZonaParqueaderoFachada {

	private DAOFactory daoFactory;
	private RegistrarZonaParqueaderoCasoUso casoUso;

	public RegistrarZonaParqueaderoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarZonaParqueaderoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ZonaParqueaderoDTO datos) {
		try {

			var dominio = new ZonaParqueaderoDominio.Builder()
					.id(datos.getId())
					.nombreZona(datos.getNombreZona())
					.parqueadero(datos.getParqueadero() != null ? new ParqueaderoDominio.Builder()
							.id(datos.getParqueadero().getId())
							.build() : null)
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
					.nombreZona("Zona Norte")
					.parqueadero(new ParqueaderoDTO.Builder()
							.id(UUID.fromString("UUID_DE_PARQUEADERO"))
							.build())
					.build();
			new RegistrarZonaParqueaderoFachadaImpl().ejecutar(dto);
			System.out.println("ZonaParqueadero registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
