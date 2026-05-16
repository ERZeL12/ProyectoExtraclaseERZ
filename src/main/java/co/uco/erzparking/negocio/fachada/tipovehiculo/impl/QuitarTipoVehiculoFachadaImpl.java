package co.uco.erzparking.negocio.fachada.tipovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.casouso.tipovehiculo.QuitarTipoVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.tipovehiculo.impl.QuitarTipoVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.fachada.tipovehiculo.QuitarTipoVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarTipoVehiculoFachadaImpl implements QuitarTipoVehiculoFachada {

	private DAOFactory daoFactory;
	private QuitarTipoVehiculoCasoUso casoUso;

	public QuitarTipoVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarTipoVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final TipoVehiculoDTO datos) {
		try {

			var dominio = new TipoVehiculoDominio.Builder().id(datos.getId()).build();
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
