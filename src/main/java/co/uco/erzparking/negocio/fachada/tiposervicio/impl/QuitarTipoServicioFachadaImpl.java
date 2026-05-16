package co.uco.erzparking.negocio.fachada.tiposervicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.casouso.tiposervicio.QuitarTipoServicioCasoUso;
import co.uco.erzparking.negocio.casouso.tiposervicio.impl.QuitarTipoServicioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.negocio.fachada.tiposervicio.QuitarTipoServicioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarTipoServicioFachadaImpl implements QuitarTipoServicioFachada {

	private DAOFactory daoFactory;
	private QuitarTipoServicioCasoUso casoUso;

	public QuitarTipoServicioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarTipoServicioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final TipoServicioDTO datos) {
		try {

			var dominio = new TipoServicioDominio.Builder().id(datos.getId()).build();
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
