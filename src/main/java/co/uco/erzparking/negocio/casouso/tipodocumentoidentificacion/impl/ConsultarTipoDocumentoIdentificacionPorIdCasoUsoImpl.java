package co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.ConsultarTipoDocumentoIdentificacionPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTipoDocumentoIdentificacionPorIdCasoUsoImpl implements ConsultarTipoDocumentoIdentificacionPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTipoDocumentoIdentificacionPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public TipoDocumentoIdentificacionDominio ejecutar(final TipoDocumentoIdentificacionDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final TipoDocumentoIdentificacionDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipoDocumentoIdentificacion son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del tipoDocumentoIdentificacion es obligatorio para consultar");
		}
	}

	private TipoDocumentoIdentificacionDominio consultar(final TipoDocumentoIdentificacionDominio datos) {
		var entidad = daoFactory.getTipoDocumentoIdentificacionDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El tipoDocumentoIdentificacion no existe en el sistema");
		}
		return new TipoDocumentoIdentificacionDominio.Builder()
				.id(entidad.getId())
				.nombreDocumentoIdentificacion(entidad.getNombreDocumentoIdentificacion())
				.descripcion(entidad.getDescripcion())
				.build();
	}
}
