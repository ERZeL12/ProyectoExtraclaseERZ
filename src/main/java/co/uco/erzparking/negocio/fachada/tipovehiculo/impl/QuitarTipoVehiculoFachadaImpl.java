package co.uco.erzparking.negocio.fachada.tipovehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.casouso.tipovehiculo.QuitarTipoVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.tipovehiculo.impl.QuitarTipoVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.fachada.tipovehiculo.QuitarTipoVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarTipoVehiculoFachadaImpl implements QuitarTipoVehiculoFachada {

	private DAOFactory daoFactory;
	private QuitarTipoVehiculoCasoUso casoUso;

	public QuitarTipoVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarTipoVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final TipoVehiculoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new TipoVehiculoDominio.Builder().id(datos.getId()).build();
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
			var dto = new TipoVehiculoDTO.Builder()
					.id(UUID.fromString("66b78346-d83d-4bf1-9346-ce04d18d8e27"))
					.build();
			new QuitarTipoVehiculoFachadaImpl().ejecutar(dto);
			System.out.println("TipoVehiculo eliminado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
