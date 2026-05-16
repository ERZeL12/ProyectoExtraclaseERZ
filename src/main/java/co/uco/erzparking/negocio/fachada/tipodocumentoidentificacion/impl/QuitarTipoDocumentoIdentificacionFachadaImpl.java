package co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.QuitarTipoDocumentoIdentificacionCasoUso;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.impl.QuitarTipoDocumentoIdentificacionCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.QuitarTipoDocumentoIdentificacionFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarTipoDocumentoIdentificacionFachadaImpl implements QuitarTipoDocumentoIdentificacionFachada {

	private DAOFactory daoFactory;
	private QuitarTipoDocumentoIdentificacionCasoUso casoUso;

	public QuitarTipoDocumentoIdentificacionFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarTipoDocumentoIdentificacionCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final TipoDocumentoIdentificacionDTO datos) {
		try {

			var dominio = new TipoDocumentoIdentificacionDominio.Builder().id(datos.getId()).build();
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
