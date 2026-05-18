package co.uco.erzparking.negocio.fachada.tarifa.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.negocio.casouso.tarifa.FinalizarVigenciaTarifaCasoUso;
import co.uco.erzparking.negocio.casouso.tarifa.impl.FinalizarVigenciaTarifaCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.fachada.tarifa.FinalizarVigenciaTarifaFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class FinalizarVigenciaTarifaFachadaImpl implements FinalizarVigenciaTarifaFachada {

	private DAOFactory daoFactory;
	private FinalizarVigenciaTarifaCasoUso casoUso;

	public FinalizarVigenciaTarifaFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new FinalizarVigenciaTarifaCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final TarifaDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new TarifaDominio.Builder().id(datos.getId()).build();
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
					.id(UUID.fromString("2addbabd-1197-48f7-9bec-4b967b51be9c"))
					.build();
			new FinalizarVigenciaTarifaFachadaImpl().ejecutar(dto);
			System.out.println("Vigencia de tarifa finalizada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
