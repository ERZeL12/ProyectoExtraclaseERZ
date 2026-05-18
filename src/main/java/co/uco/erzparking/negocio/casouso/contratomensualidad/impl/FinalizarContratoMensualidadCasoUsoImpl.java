package co.uco.erzparking.negocio.casouso.contratomensualidad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ContratoMensualidadEntidad;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;
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
		var contratoExistente = obtenerContratoExistente(datos.getId());
		finalizar(datos);
		marcarEspacioDisponible(contratoExistente.getEspacioFisico().getId());
	}

	private void validarIntegridadDatos(final ContratoMensualidadDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del contratoMensualidad son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del contratoMensualidad es obligatorio");
		}
	}

	private ContratoMensualidadEntidad obtenerContratoExistente(final UUID id) {
		var contrato = daoFactory.getContratoMensualidadDAO().consultarPorId(id);
		if (UtilObjeto.esNulo(contrato)) {
			throw ERZParkingExcepcion.crear("El contratoMensualidad no existe en el sistema");
		}
		return contrato;
	}

	private void finalizar(final ContratoMensualidadDominio datos) {
		var entidad = new ContratoMensualidadEntidad.Builder()
				.id(datos.getId())
				.fechaFinContrato(new java.util.Date())
				.build();
		daoFactory.getContratoMensualidadDAO().actualizar(datos.getId(), entidad);
	}

	private void marcarEspacioDisponible(final UUID espacioFisicoId) {
		var idDisponible = obtenerIdEstadoPorNombre("DISPONIBLE");
		var estado = new EstadoEspacioFisicoEntidad.Builder().id(idDisponible).build();
		var entidad = new EspacioFisicoEntidad.Builder()
				.id(espacioFisicoId)
				.estadoEspacioFisico(estado)
				.build();
		daoFactory.getEspacioFisicoDAO().actualizar(espacioFisicoId, entidad);
	}

	private UUID obtenerIdEstadoPorNombre(final String nombre) {
		var estados = daoFactory.getEstadoEspacioFisicoDAO().consultarTodos();
		for (var estado : estados) {
			if (nombre.equalsIgnoreCase(estado.getNombreEstadoEspacioFisico())) {
				return estado.getId();
			}
		}
		throw ERZParkingExcepcion.crear("El estado '" + nombre + "' no existe en el sistema");
	}
}
