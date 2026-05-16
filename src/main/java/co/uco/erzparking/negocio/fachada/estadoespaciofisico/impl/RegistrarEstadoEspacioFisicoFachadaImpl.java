package co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.RegistrarEstadoEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.impl.RegistrarEstadoEspacioFisicoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.RegistrarEstadoEspacioFisicoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarEstadoEspacioFisicoFachadaImpl implements RegistrarEstadoEspacioFisicoFachada {

	private DAOFactory daoFactory;
	private RegistrarEstadoEspacioFisicoCasoUso casoUso;

	public RegistrarEstadoEspacioFisicoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarEstadoEspacioFisicoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final EstadoEspacioFisicoDTO datos) {
		try {

			var dominio = new EstadoEspacioFisicoDominio.Builder()
					.id(datos.getId())
					.nombreEstadoEspacioFisico(datos.getNombreEstadoEspacioFisico())
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
			var dto = new EstadoEspacioFisicoDTO.Builder()
					.nombreEstadoEspacioFisico("DISPONIBLE")
					.build();
			new RegistrarEstadoEspacioFisicoFachadaImpl().ejecutar(dto);
			System.out.println("EstadoEspacioFisico registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
