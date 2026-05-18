package co.uco.erzparking.negocio.fachada.tarifa.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.negocio.casouso.tarifa.DesactivarTarifaCasoUso;
import co.uco.erzparking.negocio.casouso.tarifa.impl.DesactivarTarifaCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.fachada.tarifa.DesactivarTarifaFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class DesactivarTarifaFachadaImpl implements DesactivarTarifaFachada {

	private DAOFactory daoFactory;
	private DesactivarTarifaCasoUso casoUso;

	public DesactivarTarifaFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new DesactivarTarifaCasoUsoImpl(daoFactory);
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
