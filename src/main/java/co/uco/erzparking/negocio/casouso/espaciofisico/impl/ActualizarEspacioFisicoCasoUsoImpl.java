package co.uco.erzparking.negocio.casouso.espaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.datos.dao.sql.sqlserver.ContratoMensualidadSQLServerDAO;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;
import co.uco.erzparking.negocio.casouso.espaciofisico.ActualizarEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarEspacioFisicoCasoUsoImpl implements ActualizarEspacioFisicoCasoUso {

	private DAOFactory daoFactory;

	public ActualizarEspacioFisicoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final EspacioFisicoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinVehiculoAsignado(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final EspacioFisicoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del espacio fisico son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del espacio fisico es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getEstadoEspacioFisico()) || UtilObjeto.esNulo(datos.getEstadoEspacioFisico().getId())) {
			throw ERZParkingExcepcion.crear("El estado del espacio fisico es obligatorio para actualizar");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getEspacioFisicoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El espacio fisico que intenta actualizar no existe en el sistema");
		}
	}

	private void validarSinVehiculoAsignado(final UUID espacioFisicoId) {
		var espacio = daoFactory.getEspacioFisicoDAO().consultarPorId(espacioFisicoId);
		if (!UtilObjeto.esNulo(espacio.getEstadoEspacioFisico())
				&& "OCUPADO".equalsIgnoreCase(espacio.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())) {
			throw ERZParkingExcepcion.crear("No se puede cambiar el estado porque el espacio tiene un vehiculo asignado");
		}
		var contrato = ((ContratoMensualidadSQLServerDAO) daoFactory.getContratoMensualidadDAO())
				.consultarContratoVigentePorEspacioFisico(espacioFisicoId);
		if (!UtilObjeto.esNulo(contrato)) {
			throw ERZParkingExcepcion.crear("No se puede cambiar el estado porque el espacio tiene un contrato mensual vigente");
		}
	}

	private void actualizar(final EspacioFisicoDominio datos) {
		var estado = new EstadoEspacioFisicoEntidad.Builder()
				.id(datos.getEstadoEspacioFisico().getId())
				.nombreEstadoEspacioFisico(datos.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())
				.build();
		var entidad = new EspacioFisicoEntidad.Builder()
				.id(datos.getId())
				.estadoEspacioFisico(estado)
				.build();
		daoFactory.getEspacioFisicoDAO().actualizar(datos.getId(), entidad);
	}
}
