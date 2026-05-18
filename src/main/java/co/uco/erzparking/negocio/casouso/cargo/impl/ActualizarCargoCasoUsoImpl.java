package co.uco.erzparking.negocio.casouso.cargo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.negocio.casouso.cargo.ActualizarCargoCasoUso;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarCargoCasoUsoImpl implements ActualizarCargoCasoUso {

	private DAOFactory daoFactory;

	public ActualizarCargoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final CargoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final CargoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del cargo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del cargo es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getNombreCargo()) || datos.getNombreCargo().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del cargo es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getCargoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El cargo que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final CargoDominio datos) {
		var existente = daoFactory.getCargoDAO().consultarPorId(datos.getId());
		var entidad = new CargoEntidad.Builder()
				.id(datos.getId())
				.nombreCargo(datos.getNombreCargo())
				.descripcion(datos.getDescripcion())
				.estadoActual(existente.isEstadoActual())
				.build();
		daoFactory.getCargoDAO().actualizar(datos.getId(), entidad);
	}

}
