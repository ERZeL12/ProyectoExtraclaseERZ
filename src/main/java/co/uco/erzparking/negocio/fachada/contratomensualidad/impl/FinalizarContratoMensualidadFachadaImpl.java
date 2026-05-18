package co.uco.erzparking.negocio.fachada.contratomensualidad.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.negocio.casouso.contratomensualidad.FinalizarContratoMensualidadCasoUso;
import co.uco.erzparking.negocio.casouso.contratomensualidad.impl.FinalizarContratoMensualidadCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.negocio.fachada.contratomensualidad.FinalizarContratoMensualidadFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class FinalizarContratoMensualidadFachadaImpl implements FinalizarContratoMensualidadFachada {

	private DAOFactory daoFactory;
	private FinalizarContratoMensualidadCasoUso casoUso;

	public FinalizarContratoMensualidadFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new FinalizarContratoMensualidadCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ContratoMensualidadDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new ContratoMensualidadDominio.Builder().id(datos.getId()).build();
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
			var dto = new ContratoMensualidadDTO.Builder()
					.id(UUID.fromString("a40c7a23-0482-49e0-906c-7865b5d34e4e"))
					.build();
			new FinalizarContratoMensualidadFachadaImpl().ejecutar(dto);
			System.out.println("Contrato mensualidad finalizado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
