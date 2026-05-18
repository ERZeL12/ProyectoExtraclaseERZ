package co.uco.erzparking.negocio.fachada.espaciofisico.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.negocio.casouso.espaciofisico.QuitarEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.casouso.espaciofisico.impl.QuitarEspacioFisicoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.fachada.espaciofisico.QuitarEspacioFisicoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarEspacioFisicoFachadaImpl implements QuitarEspacioFisicoFachada {

	private DAOFactory daoFactory;
	private QuitarEspacioFisicoCasoUso casoUso;

	public QuitarEspacioFisicoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarEspacioFisicoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final EspacioFisicoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new EspacioFisicoDominio.Builder().id(datos.getId()).build();
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
			var dto = new EspacioFisicoDTO.Builder()
					.id(UUID.fromString("046a876f-ff0a-4e78-9147-78fef43b9669"))
					.build();
			new QuitarEspacioFisicoFachadaImpl().ejecutar(dto);
			System.out.println("EspacioFisico eliminado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
