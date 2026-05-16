package co.uco.erzparking.negocio.casouso.estadoespaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.RegistrarEstadoEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarEstadoEspacioFisicoCasoUsoImpl implements RegistrarEstadoEspacioFisicoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarEstadoEspacioFisicoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final EstadoEspacioFisicoDominio datos) {
		validarIntegridadDatos(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final EstadoEspacioFisicoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del estado del espacio fisico son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getNombreEstadoEspacioFisico()) || datos.getNombreEstadoEspacioFisico().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del estado del espacio fisico es obligatorio");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getEstadoEspacioFisicoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final EstadoEspacioFisicoDominio datos) {
		var entidad = new EstadoEspacioFisicoEntidad.Builder()
				.id(generarIdUnico())
				.nombreEstadoEspacioFisico(datos.getNombreEstadoEspacioFisico())
				.build();
		daoFactory.getEstadoEspacioFisicoDAO().crear(entidad);
	}
}
