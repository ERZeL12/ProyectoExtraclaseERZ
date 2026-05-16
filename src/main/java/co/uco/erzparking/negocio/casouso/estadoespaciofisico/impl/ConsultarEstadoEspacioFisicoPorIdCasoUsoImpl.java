package co.uco.erzparking.negocio.casouso.estadoespaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.ConsultarEstadoEspacioFisicoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarEstadoEspacioFisicoPorIdCasoUsoImpl implements ConsultarEstadoEspacioFisicoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarEstadoEspacioFisicoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public EstadoEspacioFisicoDominio ejecutar(final EstadoEspacioFisicoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final EstadoEspacioFisicoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del estadoEspacioFisico son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del estadoEspacioFisico es obligatorio para consultar");
		}
	}

	private EstadoEspacioFisicoDominio consultar(final EstadoEspacioFisicoDominio datos) {
		var entidad = daoFactory.getEstadoEspacioFisicoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El estadoEspacioFisico no existe en el sistema");
		}
		return new EstadoEspacioFisicoDominio.Builder()
				.id(entidad.getId())
				.nombreEstadoEspacioFisico(entidad.getNombreEstadoEspacioFisico())
				.build();
	}
}
