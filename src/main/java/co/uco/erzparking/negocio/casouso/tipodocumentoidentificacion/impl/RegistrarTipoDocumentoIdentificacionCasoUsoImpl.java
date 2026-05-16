package co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TipoDocumentoIdentificacionEntidad;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.RegistrarTipoDocumentoIdentificacionCasoUso;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarTipoDocumentoIdentificacionCasoUsoImpl implements RegistrarTipoDocumentoIdentificacionCasoUso {

	private DAOFactory daoFactory;

	public RegistrarTipoDocumentoIdentificacionCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TipoDocumentoIdentificacionDominio datos) {
		validarIntegridadDatos(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final TipoDocumentoIdentificacionDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipoDocumentoIdentificacion son obligatorios");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getTipoDocumentoIdentificacionDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final TipoDocumentoIdentificacionDominio datos) {
		var entidad = new TipoDocumentoIdentificacionEntidad.Builder()
				.id(generarIdUnico())
				.nombreDocumentoIdentificacion(datos.getNombreDocumentoIdentificacion())
				.descripcion(datos.getDescripcion())
				.build();
		daoFactory.getTipoDocumentoIdentificacionDAO().crear(entidad);
	}
}
