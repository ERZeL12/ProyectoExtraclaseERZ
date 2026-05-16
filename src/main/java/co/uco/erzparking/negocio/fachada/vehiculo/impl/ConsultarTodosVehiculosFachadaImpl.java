package co.uco.erzparking.negocio.fachada.vehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.vehiculo.ConsultarTodosVehiculosCasoUso;
import co.uco.erzparking.negocio.casouso.vehiculo.impl.ConsultarTodosVehiculosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.negocio.fachada.vehiculo.ConsultarTodosVehiculosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosVehiculosFachadaImpl implements ConsultarTodosVehiculosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosVehiculosCasoUso casoUso;

	public ConsultarTodosVehiculosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosVehiculosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<VehiculoDTO> ejecutar(final VehiculoDTO datos) {
		try {

			var dominio = new VehiculoDominio.Builder().id(datos.getId()).build();
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

	private VehiculoDTO mapearADto(final VehiculoDominio d) {
		var tipoVehiculoDTO = d.getTipoVehiculo() != null
				? new TipoVehiculoDTO.Builder()
						.id(d.getTipoVehiculo().getId())
						.nombreVehiculo(d.getTipoVehiculo().getNombreVehiculo())
						.build()
				: null;
		return new VehiculoDTO.Builder()
				.id(d.getId())
				.placaVehiculo(d.getPlacaVehiculo())
				.tipoVehiculo(tipoVehiculoDTO)
				.build();
	}

}
