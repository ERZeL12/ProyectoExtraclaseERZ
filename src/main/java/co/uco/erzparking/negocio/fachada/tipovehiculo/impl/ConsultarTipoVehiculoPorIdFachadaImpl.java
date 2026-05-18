package co.uco.erzparking.negocio.fachada.tipovehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.casouso.tipovehiculo.ConsultarTipoVehiculoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.tipovehiculo.impl.ConsultarTipoVehiculoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.fachada.tipovehiculo.ConsultarTipoVehiculoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTipoVehiculoPorIdFachadaImpl implements ConsultarTipoVehiculoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarTipoVehiculoPorIdCasoUso casoUso;

	public ConsultarTipoVehiculoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTipoVehiculoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public TipoVehiculoDTO ejecutar(final TipoVehiculoDTO datos) {
		try {

			var dominio = new TipoVehiculoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return new TipoVehiculoDTO.Builder()
					.id(resultado.getId())
					.nombreVehiculo(resultado.getNombreVehiculo())
					.descripcion(resultado.getDescripcion())
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
			var filtro = new TipoVehiculoDTO.Builder()
					.id(UUID.fromString("66b78346-d83d-4bf1-9346-ce04d18d8e27"))
					.build();
			var resultado = new ConsultarTipoVehiculoPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("TipoVehiculo consultado: id=" + resultado.getId() + ", nombre=" + resultado.getNombreVehiculo());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
