package co.uco.erzparking.negocio.fachada.tarifa.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.casouso.tarifa.RegistrarTarifaCasoUso;
import co.uco.erzparking.negocio.casouso.tarifa.impl.RegistrarTarifaCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.fachada.tarifa.RegistrarTarifaFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarTarifaFachadaImpl implements RegistrarTarifaFachada {

	private DAOFactory daoFactory;
	private RegistrarTarifaCasoUso casoUso;

	public RegistrarTarifaFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarTarifaCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final TarifaDTO datos) {
		try {

			var dominio = new TarifaDominio.Builder()
					.id(datos.getId())
					.valorServicio(datos.getValorServicio())
					.fechaInicioVigenciaTarifa(datos.getFechaInicioVigenciaTarifa())
					.fechaFinVigenciaTarifa(datos.getFechaFinVigenciaTarifa())
					.tipoVehiculo(datos.getTipoVehiculo() != null ? new TipoVehiculoDominio.Builder()
							.id(datos.getTipoVehiculo().getId())
							.build() : null)
					.servicio(datos.getServicio() != null ? new ServicioDominio.Builder()
							.id(datos.getServicio().getId())
							.build() : null)
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
			var dto = new TarifaDTO.Builder()
					.valorServicio(5000.00)
					.fechaInicioVigenciaTarifa(new java.util.Date())
					.tipoVehiculo(new TipoVehiculoDTO.Builder()
							.id(UUID.fromString("UUID_DE_TIPOVEHICULO"))
							.build())
					.servicio(new ServicioDTO.Builder()
							.id(UUID.fromString("UUID_DE_SERVICIO"))
							.build())
					.build();
			new RegistrarTarifaFachadaImpl().ejecutar(dto);
			System.out.println("Tarifa registrada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
