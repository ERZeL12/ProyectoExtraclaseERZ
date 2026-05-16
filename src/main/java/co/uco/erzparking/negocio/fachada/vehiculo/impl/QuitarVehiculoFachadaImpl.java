package co.uco.erzparking.negocio.fachada.vehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.vehiculo.QuitarVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.vehiculo.impl.QuitarVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.negocio.fachada.vehiculo.QuitarVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarVehiculoFachadaImpl implements QuitarVehiculoFachada {

	private DAOFactory daoFactory;
	private QuitarVehiculoCasoUso casoUso;

	public QuitarVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final VehiculoDTO datos) {
		try {

			var dominio = new VehiculoDominio.Builder().id(datos.getId()).build();
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
