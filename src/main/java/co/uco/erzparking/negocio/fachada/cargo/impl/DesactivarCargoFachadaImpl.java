package co.uco.erzparking.negocio.fachada.cargo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.negocio.casouso.cargo.DesactivarCargoCasoUso;
import co.uco.erzparking.negocio.casouso.cargo.impl.DesactivarCargoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.fachada.cargo.DesactivarCargoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class DesactivarCargoFachadaImpl implements DesactivarCargoFachada {

	private DAOFactory daoFactory;
	private DesactivarCargoCasoUso casoUso;

	public DesactivarCargoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new DesactivarCargoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final CargoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new CargoDominio.Builder().id(datos.getId()).build();
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
