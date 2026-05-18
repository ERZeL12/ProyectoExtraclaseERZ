package co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.QuitarEstadoEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.impl.QuitarEstadoEspacioFisicoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.QuitarEstadoEspacioFisicoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarEstadoEspacioFisicoFachadaImpl implements QuitarEstadoEspacioFisicoFachada {

	private DAOFactory daoFactory;
	private QuitarEstadoEspacioFisicoCasoUso casoUso;

	public QuitarEstadoEspacioFisicoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarEstadoEspacioFisicoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final EstadoEspacioFisicoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new EstadoEspacioFisicoDominio.Builder().id(datos.getId()).build();
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
			var dto = new EstadoEspacioFisicoDTO.Builder()
					.id(UUID.fromString("75db75b1-3f27-4e47-bf10-4f2e42764cf8"))
					.build();
			new QuitarEstadoEspacioFisicoFachadaImpl().ejecutar(dto);
			System.out.println("EstadoEspacioFisico eliminado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
