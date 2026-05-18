package co.uco.erzparking.negocio.fachada.tipovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.casouso.tipovehiculo.RegistrarTipoVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.tipovehiculo.impl.RegistrarTipoVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.fachada.tipovehiculo.RegistrarTipoVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarTipoVehiculoFachadaImpl implements RegistrarTipoVehiculoFachada {

	private DAOFactory daoFactory;
	private RegistrarTipoVehiculoCasoUso casoUso;

	public RegistrarTipoVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarTipoVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final TipoVehiculoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new TipoVehiculoDominio.Builder()
					.id(datos.getId())
					.nombreVehiculo(datos.getNombreVehiculo())
					.descripcion(datos.getDescripcion())
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
			var dto = new co.uco.erzparking.dto.TipoVehiculoDTO.Builder()
					.nombreVehiculo("Carro")
					.build();
			new RegistrarTipoVehiculoFachadaImpl().ejecutar(dto);
			System.out.println("Tipo vehiculo registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
