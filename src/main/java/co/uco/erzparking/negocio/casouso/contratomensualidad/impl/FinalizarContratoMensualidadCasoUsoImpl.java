package co.uco.erzparking.negocio.casouso.contratomensualidad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ContratoMensualidadEntidad;
import co.uco.erzparking.negocio.casouso.contratomensualidad.FinalizarContratoMensualidadCasoUso;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class FinalizarContratoMensualidadCasoUsoImpl implements FinalizarContratoMensualidadCasoUso {

	private DAOFactory daoFactory;

	public FinalizarContratoMensualidadCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ContratoMensualidadDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		finalizar(datos);
	}

	private void validarIntegridadDatos(final ContratoMensualidadDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del contratoMensualidad son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del contratoMensualidad es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getContratoMensualidadDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El contratoMensualidad no existe en el sistema");
		}
	}

	private void finalizar(final ContratoMensualidadDominio datos) {
	    var entidad = new ContratoMensualidadEntidad.Builder()
	            .id(datos.getId())
	            .fechaFinContrato(new java.util.Date())
	            .build();
	    daoFactory.getContratoMensualidadDAO().actualizar(datos.getId(), entidad);
	}
}
