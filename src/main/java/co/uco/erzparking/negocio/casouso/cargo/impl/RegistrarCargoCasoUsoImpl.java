package co.uco.erzparking.negocio.casouso.cargo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.cargo.RegistrarCargoCasoUso;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarCargoCasoUsoImpl implements RegistrarCargoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarCargoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final CargoDominio datos) {
		validarIntegridadDatos(datos);
		validarParqueaderoExiste(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final CargoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del cargo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getNombreCargo()) || datos.getNombreCargo().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del cargo es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getParqueadero()) || UtilObjeto.esNulo(datos.getParqueadero().getId())) {
			throw ERZParkingExcepcion.crear("El parqueadero del cargo es obligatorio");
		}
	}

	private void validarParqueaderoExiste(final CargoDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getParqueaderoDAO().consultarPorId(datos.getParqueadero().getId()))) {
			throw ERZParkingExcepcion.crear("El parqueadero asociado al cargo no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getCargoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final CargoDominio datos) {
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(datos.getParqueadero().getId())
				.build();
		var entidad = new CargoEntidad.Builder()
				.id(generarIdUnico())
				.nombreCargo(datos.getNombreCargo())
				.descripcion(datos.getDescripcion())
				.parqueadero(parqueadero)
				.build();
		daoFactory.getCargoDAO().crear(entidad);
	}

}
