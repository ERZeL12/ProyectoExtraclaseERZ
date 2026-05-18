package co.uco.erzparking.negocio.fachada.espaciofisico.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.negocio.casouso.espaciofisico.ActualizarEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.casouso.espaciofisico.impl.ActualizarEspacioFisicoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.negocio.fachada.espaciofisico.ActualizarEspacioFisicoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarEspacioFisicoFachadaImpl implements ActualizarEspacioFisicoFachada {

	private DAOFactory daoFactory;
	private ActualizarEspacioFisicoCasoUso casoUso;

	public ActualizarEspacioFisicoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarEspacioFisicoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final EspacioFisicoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var estado = datos.getEstadoEspacioFisico() != null
					? new EstadoEspacioFisicoDominio.Builder()
							.id(datos.getEstadoEspacioFisico().getId())
							.nombreEstadoEspacioFisico(datos.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())
							.build()
					: null;
				var dominio = new EspacioFisicoDominio.Builder()
					.id(datos.getId())
					.estadoEspacioFisico(estado)
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
			var dto = new EspacioFisicoDTO.Builder()
					.id(UUID.fromString("046a876f-ff0a-4e78-9147-78fef43b9669"))
					.estadoEspacioFisico(new EstadoEspacioFisicoDTO.Builder()
							.id(UUID.fromString("75db75b1-3f27-4e47-bf10-4f2e42764cf8"))
							.build())
					.build();
			new ActualizarEspacioFisicoFachadaImpl().ejecutar(dto);
			System.out.println("EspacioFisico actualizado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
