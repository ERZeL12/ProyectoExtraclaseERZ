package co.uco.erzparking.negocio.fachada.tipovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.casouso.tipovehiculo.ConsultarTodosTipoVehiculosCasoUso;
import co.uco.erzparking.negocio.casouso.tipovehiculo.impl.ConsultarTodosTipoVehiculosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.fachada.tipovehiculo.ConsultarTodosTipoVehiculosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosTipoVehiculosFachadaImpl implements ConsultarTodosTipoVehiculosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosTipoVehiculosCasoUso casoUso;

	public ConsultarTodosTipoVehiculosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosTipoVehiculosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<TipoVehiculoDTO> ejecutar(final TipoVehiculoDTO datos) {
		try {

			var dominio = new TipoVehiculoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return resultado.stream().map(this::mapearADto).collect(java.util.stream.Collectors.toList());
		} catch (ERZParkingExcepcion excepcion) {
						throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	private TipoVehiculoDTO mapearADto(final TipoVehiculoDominio d) {
		return new TipoVehiculoDTO.Builder()
				.id(d.getId())
				.nombreVehiculo(d.getNombreVehiculo())
				.descripcion(d.getDescripcion())
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new TipoVehiculoDTO.Builder().build();
			var resultado = new ConsultarTodosTipoVehiculosFachadaImpl().ejecutar(filtro);
			System.out.println("Total tipos vehiculo encontrados: " + resultado.size());
			resultado.forEach(t -> System.out.println(" - " + t.getId() + " | " + t.getNombreVehiculo()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
