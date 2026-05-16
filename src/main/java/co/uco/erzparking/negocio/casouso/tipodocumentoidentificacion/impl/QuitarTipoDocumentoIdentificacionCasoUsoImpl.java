package co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.QuitarTipoDocumentoIdentificacionCasoUso;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarTipoDocumentoIdentificacionCasoUsoImpl implements QuitarTipoDocumentoIdentificacionCasoUso {

	private DAOFactory daoFactory;

	public QuitarTipoDocumentoIdentificacionCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TipoDocumentoIdentificacionDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final TipoDocumentoIdentificacionDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipoDocumentoIdentificacion son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del tipoDocumentoIdentificacion es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getTipoDocumentoIdentificacionDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El tipoDocumentoIdentificacion no existe en el sistema");
		}
	}

	private void quitar(final TipoDocumentoIdentificacionDominio datos) {
		daoFactory.getTipoDocumentoIdentificacionDAO().eliminar(datos.getId());
	}
}
