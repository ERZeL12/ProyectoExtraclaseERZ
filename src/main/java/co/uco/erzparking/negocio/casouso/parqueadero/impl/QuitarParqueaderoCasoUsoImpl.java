package co.uco.erzparking.negocio.casouso.parqueadero.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.entidad.ZonaParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.parqueadero.QuitarParqueaderoCasoUso;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarParqueaderoCasoUsoImpl implements QuitarParqueaderoCasoUso {

	private DAOFactory daoFactory;

	public QuitarParqueaderoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ParqueaderoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final ParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del parqueadero son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del parqueadero es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getParqueaderoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El parqueadero no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var parqueadero = new ParqueaderoEntidad.Builder().id(id).build();
		var cargos = daoFactory.getCargoDAO().consultarPorFiltro(new CargoEntidad.Builder().parqueadero(parqueadero).build());
		if (!cargos.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el parqueadero porque tiene cargos asociados");
		}
		var operarios = daoFactory.getOperarioDAO().consultarPorFiltro(new OperarioEntidad.Builder().parqueadero(parqueadero).build());
		if (!operarios.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el parqueadero porque tiene operarios asociados");
		}
		var zonas = daoFactory.getZonaParqueaderoDAO().consultarPorFiltro(new ZonaParqueaderoEntidad.Builder().parqueadero(parqueadero).build());
		if (!zonas.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el parqueadero porque tiene zonas asociadas");
		}
		var servicios = daoFactory.getServicioDAO().consultarPorFiltro(new ServicioEntidad.Builder().parqueadero(parqueadero).build());
		if (!servicios.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el parqueadero porque tiene servicios asociados");
		}
		var espacios = daoFactory.getEspacioFisicoDAO().consultarPorFiltro(new EspacioFisicoEntidad.Builder().parqueadero(parqueadero).build());
		if (!espacios.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el parqueadero porque tiene espacios fisicos asociados");
		}
	}

	private void quitar(final ParqueaderoDominio datos) {
		daoFactory.getParqueaderoDAO().eliminar(datos.getId());
	}
}
