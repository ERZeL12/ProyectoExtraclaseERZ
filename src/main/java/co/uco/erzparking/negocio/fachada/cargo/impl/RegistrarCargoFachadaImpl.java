package co.uco.erzparking.negocio.fachada.cargo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.cargo.RegistrarCargoCasoUso;
import co.uco.erzparking.negocio.casouso.cargo.impl.RegistrarCargoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.cargo.RegistrarCargoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarCargoFachadaImpl implements RegistrarCargoFachada {

	private DAOFactory daoFactory;
	private RegistrarCargoCasoUso casoUso;

	public RegistrarCargoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarCargoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final CargoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new CargoDominio.Builder()
					.id(datos.getId())
					.nombreCargo(datos.getNombreCargo())
					.descripcion(datos.getDescripcion())
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
			var dto = new CargoDTO.Builder()
					.nombreCargo("Cajero")
					.descripcion("Encargado de recibir los pagos en el parqueadero")
					.parqueadero(new ParqueaderoDTO.Builder()
							.id(UUID.fromString("6f34728b-d0ee-4440-bd24-38b29b2ed427"))
							.build())
					.build();
			new RegistrarCargoFachadaImpl().ejecutar(dto);
			System.out.println("Cargo registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
