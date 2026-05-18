package co.uco.erzparking.negocio.fachada.tarifa.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.casouso.tarifa.ConsultarTarifaPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.tarifa.impl.ConsultarTarifaPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.fachada.tarifa.ConsultarTarifaPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTarifaPorIdFachadaImpl implements ConsultarTarifaPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarTarifaPorIdCasoUso casoUso;

	public ConsultarTarifaPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTarifaPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public TarifaDTO ejecutar(final TarifaDTO datos) {
		try {

			var dominio = new TarifaDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var tipoVehiculoDTO = resultado.getTipoVehiculo() != null
					? new TipoVehiculoDTO.Builder()
							.id(resultado.getTipoVehiculo().getId())
							.nombreVehiculo(resultado.getTipoVehiculo().getNombreVehiculo())
							.descripcion(resultado.getTipoVehiculo().getDescripcion())
							.build()
					: null;
			var servicioDTO = resultado.getServicio() != null
					? new ServicioDTO.Builder()
							.id(resultado.getServicio().getId())
							.nombreServicio(resultado.getServicio().getNombreServicio())
							.build()
					: null;
				return new TarifaDTO.Builder()
					.id(resultado.getId())
					.valorServicio(resultado.getValorServicio())
					.fechaInicioVigenciaTarifa(resultado.getFechaInicioVigenciaTarifa())
					.fechaFinVigenciaTarifa(resultado.getFechaFinVigenciaTarifa())
					.tipoVehiculo(tipoVehiculoDTO)
					.servicio(servicioDTO)
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
			var filtro = new TarifaDTO.Builder()
					.id(UUID.fromString("2addbabd-1197-48f7-9bec-4b967b51be9c"))
					.build();
			var resultado = new ConsultarTarifaPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("Tarifa consultada: id=" + resultado.getId()
					+ ", valor=" + resultado.getValorServicio()
					+ ", inicio=" + resultado.getFechaInicioVigenciaTarifa()
					+ ", fin=" + resultado.getFechaFinVigenciaTarifa());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
