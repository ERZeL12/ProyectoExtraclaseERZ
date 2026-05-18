package co.uco.erzparking.negocio.casouso.contratomensualidad.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.contratomensualidad.DesactivarContratoMensualidadCasoUso;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class DesactivarContratoMensualidadCasoUsoImpl implements DesactivarContratoMensualidadCasoUso {

	private DAOFactory daoFactory;

	public DesactivarContratoMensualidadCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ContratoMensualidadDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		desactivar(datos.getId());
	}

	private void validarIntegridadDatos(final ContratoMensualidadDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del contrato de mensualidad son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del contrato de mensualidad es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getContratoMensualidadDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El contrato de mensualidad no existe en el sistema");
		}
	}

	private void desactivar(final UUID id) {
		daoFactory.getContratoMensualidadDAO().cambiarEstadoActual(id, false);
	}

}
