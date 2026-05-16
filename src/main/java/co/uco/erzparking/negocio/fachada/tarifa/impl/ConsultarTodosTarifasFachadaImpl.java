package co.uco.erzparking.negocio.fachada.tarifa.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.casouso.tarifa.ConsultarTodosTarifasCasoUso;
import co.uco.erzparking.negocio.casouso.tarifa.impl.ConsultarTodosTarifasCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.fachada.tarifa.ConsultarTodosTarifasFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosTarifasFachadaImpl implements ConsultarTodosTarifasFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosTarifasCasoUso casoUso;

	public ConsultarTodosTarifasFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosTarifasCasoUsoImpl(daoFactory);
	}

	@Override
	public List<TarifaDTO> ejecutar(final TarifaDTO datos) {
		try {

			var dominio = new TarifaDominio.Builder().id(datos.getId()).build();
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

	private TarifaDTO mapearADto(final TarifaDominio d) {
		var tipoVehiculoDTO = d.getTipoVehiculo() != null
				? new TipoVehiculoDTO.Builder()
						.id(d.getTipoVehiculo().getId())
						.nombreVehiculo(d.getTipoVehiculo().getNombreVehiculo())
						.build()
				: null;
		var servicioDTO = d.getServicio() != null
				? new ServicioDTO.Builder()
						.id(d.getServicio().getId())
						.nombreServicio(d.getServicio().getNombreServicio())
						.build()
				: null;
		return new TarifaDTO.Builder()
				.id(d.getId())
				.valorServicio(d.getValorServicio())
				.fechaInicioVigenciaTarifa(d.getFechaInicioVigenciaTarifa())
				.fechaFinVigenciaTarifa(d.getFechaFinVigenciaTarifa())
				.tipoVehiculo(tipoVehiculoDTO)
				.servicio(servicioDTO)
				.build();
	}

}
