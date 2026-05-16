package co.uco.erzparking.negocio.fachada.cargo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.negocio.casouso.cargo.ActualizarCargoCasoUso;
import co.uco.erzparking.negocio.casouso.cargo.impl.ActualizarCargoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.fachada.cargo.ActualizarCargoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarCargoFachadaImpl implements ActualizarCargoFachada {

	private DAOFactory daoFactory;
	private ActualizarCargoCasoUso casoUso;

	public ActualizarCargoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarCargoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final CargoDTO datos) {
		try {

			var dominio = new CargoDominio.Builder()
					.id(datos.getId())
					.nombreCargo(datos.getNombreCargo())
					.descripcion(datos.getDescripcion())
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
