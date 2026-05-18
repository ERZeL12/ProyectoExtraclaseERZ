package co.uco.erzparking.negocio.fachada.vehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.vehiculo.ActualizarVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.vehiculo.impl.ActualizarVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.negocio.fachada.vehiculo.ActualizarVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarVehiculoFachadaImpl implements ActualizarVehiculoFachada {

	private DAOFactory daoFactory;
	private ActualizarVehiculoCasoUso casoUso;

	public ActualizarVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final VehiculoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var tipoVehiculo = datos.getTipoVehiculo() != null
					? new TipoVehiculoDominio.Builder().id(datos.getTipoVehiculo().getId()).build()
					: null;
				var dominio = new VehiculoDominio.Builder()
					.id(datos.getId())
					.placaVehiculo(datos.getPlacaVehiculo())
					.tipoVehiculo(tipoVehiculo)
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
			var dto = new VehiculoDTO.Builder()
					.id(UUID.fromString("be6c0e80-dcea-4fb0-b443-3b0d728a08a9"))
					.placaVehiculo("ABC123")
					.tipoVehiculo(new TipoVehiculoDTO.Builder()
							.id(UUID.fromString("66b78346-d83d-4bf1-9346-ce04d18d8e27"))
							.build())
					.build();
			new ActualizarVehiculoFachadaImpl().ejecutar(dto);
			System.out.println("Vehiculo actualizado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
