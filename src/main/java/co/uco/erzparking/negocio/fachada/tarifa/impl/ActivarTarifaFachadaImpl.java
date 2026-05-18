package co.uco.erzparking.negocio.fachada.tarifa.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.negocio.casouso.tarifa.ActivarTarifaCasoUso;
import co.uco.erzparking.negocio.casouso.tarifa.impl.ActivarTarifaCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.fachada.tarifa.ActivarTarifaFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActivarTarifaFachadaImpl implements ActivarTarifaFachada {

	private DAOFactory daoFactory;
	private ActivarTarifaCasoUso casoUso;

	public ActivarTarifaFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActivarTarifaCasoUsoImpl(daoFactory);
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

}
