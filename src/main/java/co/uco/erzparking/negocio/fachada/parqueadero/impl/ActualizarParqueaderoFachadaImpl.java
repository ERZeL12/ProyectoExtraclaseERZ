package co.uco.erzparking.negocio.fachada.parqueadero.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.parqueadero.ActualizarParqueaderoCasoUso;
import co.uco.erzparking.negocio.casouso.parqueadero.impl.ActualizarParqueaderoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.parqueadero.ActualizarParqueaderoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarParqueaderoFachadaImpl implements ActualizarParqueaderoFachada {

	private DAOFactory daoFactory;
	private ActualizarParqueaderoCasoUso casoUso;

	public ActualizarParqueaderoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarParqueaderoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ParqueaderoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new ParqueaderoDominio.Builder()
					.id(datos.getId())
					.nombreEstablecimiento(datos.getNombreEstablecimiento())
					.numeroTelefonico(datos.getNumeroTelefonico())
					.correoElectronico(datos.getCorreoElectronico())
					.direccionEstablecimiento(datos.getDireccionEstablecimiento())
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
			var dto = new ParqueaderoDTO.Builder()
					.id(UUID.fromString("6f34728b-d0ee-4440-bd24-38b29b2ed427"))
					.nombreEstablecimiento("Parqueadero ERZ Actualizado")
					.numeroTelefonico(3000000000L)
					.correoElectronico("contacto@erzparking.com")
					.direccionEstablecimiento("Calle 10 # 20-30 actualizada")
					.build();
			new ActualizarParqueaderoFachadaImpl().ejecutar(dto);
			System.out.println("Parqueadero actualizado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
