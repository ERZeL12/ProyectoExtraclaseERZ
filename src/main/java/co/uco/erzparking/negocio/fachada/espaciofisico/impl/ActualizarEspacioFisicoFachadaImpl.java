package co.uco.erzparking.negocio.fachada.espaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EspacioFisicoDTO;
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

}
