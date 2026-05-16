package co.uco.erzparking.negocio.casouso.tipovehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.negocio.casouso.tipovehiculo.RegistrarTipoVehiculoCasoUso;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarTipoVehiculoCasoUsoImpl implements RegistrarTipoVehiculoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarTipoVehiculoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TipoVehiculoDominio datos) {
		validarIntegridadDatos(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final TipoVehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipo de vehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getNombreVehiculo()) || datos.getNombreVehiculo().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del tipo de vehiculo es obligatorio");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getTipoVehiculoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final TipoVehiculoDominio datos) {
		var entidad = new TipoVehiculoEntidad.Builder()
				.id(generarIdUnico())
				.nombreVehiculo(datos.getNombreVehiculo())
				.build();
		daoFactory.getTipoVehiculoDAO().crear(entidad);
	}

}
