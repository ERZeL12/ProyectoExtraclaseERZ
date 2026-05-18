package co.uco.erzparking.negocio.fachada.cargo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.negocio.casouso.cargo.QuitarCargoCasoUso;
import co.uco.erzparking.negocio.casouso.cargo.impl.QuitarCargoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.fachada.cargo.QuitarCargoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarCargoFachadaImpl implements QuitarCargoFachada {

	private DAOFactory daoFactory;
	private QuitarCargoCasoUso casoUso;

	public QuitarCargoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarCargoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final CargoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new CargoDominio.Builder().id(datos.getId()).build();
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
					.id(UUID.fromString("19108fe6-14ef-4e90-8baa-5a607def5b3d"))
					.build();
			new QuitarCargoFachadaImpl().ejecutar(dto);
			System.out.println("Cargo eliminado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
