package co.uco.erzparking.negocio.casouso.cargo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.negocio.casouso.cargo.QuitarCargoCasoUso;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarCargoCasoUsoImpl implements QuitarCargoCasoUso {

	private DAOFactory daoFactory;

	public QuitarCargoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final CargoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final CargoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del cargo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del cargo es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getCargoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El cargo no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var cargo = new CargoEntidad.Builder().id(id).build();
		var operarios = daoFactory.getOperarioDAO().consultarPorFiltro(new OperarioEntidad.Builder().cargo(cargo).build());
		if (!operarios.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el cargo porque tiene operarios asociados");
		}
	}

	private void quitar(final CargoDominio datos) {
		daoFactory.getCargoDAO().eliminar(datos.getId());
	}

}
