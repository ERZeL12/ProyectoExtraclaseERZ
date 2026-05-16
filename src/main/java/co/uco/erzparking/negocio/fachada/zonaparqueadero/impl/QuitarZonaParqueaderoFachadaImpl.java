package co.uco.erzparking.negocio.fachada.zonaparqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.QuitarZonaParqueaderoCasoUso;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.impl.QuitarZonaParqueaderoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.QuitarZonaParqueaderoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarZonaParqueaderoFachadaImpl implements QuitarZonaParqueaderoFachada {

	private DAOFactory daoFactory;
	private QuitarZonaParqueaderoCasoUso casoUso;

	public QuitarZonaParqueaderoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarZonaParqueaderoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ZonaParqueaderoDTO datos) {
		try {

			var dominio = new ZonaParqueaderoDominio.Builder().id(datos.getId()).build();
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
