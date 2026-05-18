package co.uco.erzparking.negocio.fachada.contratomensualidad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.negocio.casouso.contratomensualidad.DesactivarContratoMensualidadCasoUso;
import co.uco.erzparking.negocio.casouso.contratomensualidad.impl.DesactivarContratoMensualidadCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.negocio.fachada.contratomensualidad.DesactivarContratoMensualidadFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class DesactivarContratoMensualidadFachadaImpl implements DesactivarContratoMensualidadFachada {

	private DAOFactory daoFactory;
	private DesactivarContratoMensualidadCasoUso casoUso;

	public DesactivarContratoMensualidadFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new DesactivarContratoMensualidadCasoUsoImpl(daoFactory);
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

}
