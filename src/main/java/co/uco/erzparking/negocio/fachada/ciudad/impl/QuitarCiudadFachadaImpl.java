package co.uco.erzparking.negocio.fachada.ciudad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.negocio.casouso.ciudad.QuitarCiudadCasoUso;
import co.uco.erzparking.negocio.casouso.ciudad.impl.QuitarCiudadCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.fachada.ciudad.QuitarCiudadFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarCiudadFachadaImpl implements QuitarCiudadFachada {

	private DAOFactory daoFactory;
	private QuitarCiudadCasoUso casoUso;

	public QuitarCiudadFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarCiudadCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final CiudadDTO datos) {
		try {

			var dominio = new CiudadDominio.Builder().id(datos.getId()).build();
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
