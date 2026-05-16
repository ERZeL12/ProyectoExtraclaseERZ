package co.uco.erzparking.negocio.fachada.zonaparqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.ActualizarZonaParqueaderoCasoUso;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.impl.ActualizarZonaParqueaderoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ActualizarZonaParqueaderoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarZonaParqueaderoFachadaImpl implements ActualizarZonaParqueaderoFachada {

	private DAOFactory daoFactory;
	private ActualizarZonaParqueaderoCasoUso casoUso;

	public ActualizarZonaParqueaderoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarZonaParqueaderoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ZonaParqueaderoDTO datos) {
		try {

			var dominio = new ZonaParqueaderoDominio.Builder()
					.id(datos.getId())
					.nombreZona(datos.getNombreZona())
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
