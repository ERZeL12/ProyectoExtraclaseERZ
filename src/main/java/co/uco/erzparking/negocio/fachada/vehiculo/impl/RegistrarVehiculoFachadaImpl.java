package co.uco.erzparking.negocio.fachada.vehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.vehiculo.RegistrarVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.vehiculo.impl.RegistrarVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.negocio.fachada.vehiculo.RegistrarVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarVehiculoFachadaImpl implements RegistrarVehiculoFachada {

	private DAOFactory daoFactory;
	private RegistrarVehiculoCasoUso casoUso;

	public RegistrarVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final VehiculoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new VehiculoDominio.Builder()
					.id(datos.getId())
					.placaVehiculo(datos.getPlacaVehiculo())
					.tipoVehiculo(datos.getTipoVehiculo() != null ? new co.uco.erzparking.negocio.dominio.TipoVehiculoDominio.Builder().id(datos.getTipoVehiculo().getId()).build() : null)
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
			var dto = new co.uco.erzparking.dto.VehiculoDTO.Builder()
					.placaVehiculo("ABC123")
					.tipoVehiculo(new co.uco.erzparking.dto.TipoVehiculoDTO.Builder()
							.id(java.util.UUID.fromString("66b78346-d83d-4bf1-9346-ce04d18d8e27"))
							.build())
					.build();
			new RegistrarVehiculoFachadaImpl().ejecutar(dto);
			System.out.println("Vehiculo registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}

