package co.uco.erzparking.negocio.fachada.usuariovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.QuitarUsuarioVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.impl.QuitarUsuarioVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.QuitarUsuarioVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarUsuarioVehiculoFachadaImpl implements QuitarUsuarioVehiculoFachada {

	private DAOFactory daoFactory;
	private QuitarUsuarioVehiculoCasoUso casoUso;

	public QuitarUsuarioVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarUsuarioVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final UsuarioVehiculoDTO datos) {
		try {

			var dominio = new UsuarioVehiculoDominio.Builder().id(datos.getId()).build();
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
