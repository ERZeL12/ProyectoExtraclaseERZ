package co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.ConsultarTipoDocumentoIdentificacionPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.impl.ConsultarTipoDocumentoIdentificacionPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.ConsultarTipoDocumentoIdentificacionPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTipoDocumentoIdentificacionPorIdFachadaImpl implements ConsultarTipoDocumentoIdentificacionPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarTipoDocumentoIdentificacionPorIdCasoUso casoUso;

	public ConsultarTipoDocumentoIdentificacionPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTipoDocumentoIdentificacionPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public TipoDocumentoIdentificacionDTO ejecutar(final TipoDocumentoIdentificacionDTO datos) {
		try {

			var dominio = new TipoDocumentoIdentificacionDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return new TipoDocumentoIdentificacionDTO.Builder()
					.id(resultado.getId())
					.nombreDocumentoIdentificacion(resultado.getNombreDocumentoIdentificacion())
					.descripcion(resultado.getDescripcion())
					.build();
		} catch (ERZParkingExcepcion excepcion) {
						throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
