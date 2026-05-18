package co.uco.erzparking.negocio.fachada.vehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.vehiculo.ConsultarVehiculoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.vehiculo.impl.ConsultarVehiculoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.negocio.fachada.vehiculo.ConsultarVehiculoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarVehiculoPorIdFachadaImpl implements ConsultarVehiculoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarVehiculoPorIdCasoUso casoUso;

	public ConsultarVehiculoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarVehiculoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public VehiculoDTO ejecutar(final VehiculoDTO datos) {
		try {

			var dominio = new VehiculoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var tipoVehiculoDTO = resultado.getTipoVehiculo() != null
					? new TipoVehiculoDTO.Builder()
							.id(resultado.getTipoVehiculo().getId())
							.nombreVehiculo(resultado.getTipoVehiculo().getNombreVehiculo())
							.descripcion(resultado.getTipoVehiculo().getDescripcion())
							.build()
					: null;
				return new VehiculoDTO.Builder()
					.id(resultado.getId())
					.placaVehiculo(resultado.getPlacaVehiculo())
					.tipoVehiculo(tipoVehiculoDTO)
					.build();
		} catch (ERZParkingExcepcion excepcion) {
						throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	public static void main(final String[] args) {
		try {
			var filtro = new VehiculoDTO.Builder()
					.id(UUID.fromString("be6c0e80-dcea-4fb0-b443-3b0d728a08a9"))
					.build();
			var resultado = new ConsultarVehiculoPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("Vehiculo consultado: id=" + resultado.getId()
					+ ", placa=" + resultado.getPlacaVehiculo()
					+ ", tipoVehiculo=" + (resultado.getTipoVehiculo() != null ? resultado.getTipoVehiculo().getNombreVehiculo() : "(sin tipo)"));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
