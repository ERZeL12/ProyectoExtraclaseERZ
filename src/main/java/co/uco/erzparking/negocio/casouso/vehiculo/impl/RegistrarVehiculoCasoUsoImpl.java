package co.uco.erzparking.negocio.casouso.vehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.negocio.casouso.vehiculo.RegistrarVehiculoCasoUso;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarVehiculoCasoUsoImpl implements RegistrarVehiculoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarVehiculoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final VehiculoDominio datos) {
		validarIntegridadDatos(datos);
		validarTipoVehiculoExiste(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final VehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del vehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getPlacaVehiculo()) || datos.getPlacaVehiculo().isEmpty()) {
			throw ERZParkingExcepcion.crear("La placa del vehiculo es obligatoria");
		}
		if (UtilObjeto.esNulo(datos.getTipoVehiculo()) || UtilObjeto.esNulo(datos.getTipoVehiculo().getId())) {
			throw ERZParkingExcepcion.crear("El tipo de vehiculo es obligatorio");
		}
	}

	private void validarTipoVehiculoExiste(final VehiculoDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getTipoVehiculoDAO().consultarPorId(datos.getTipoVehiculo().getId()))) {
			throw ERZParkingExcepcion.crear("El tipo de vehiculo asociado no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getVehiculoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final VehiculoDominio datos) {
		var tipoVehiculo = new TipoVehiculoEntidad.Builder()
				.id(datos.getTipoVehiculo().getId())
				.build();
		var entidad = new VehiculoEntidad.Builder()
				.id(generarIdUnico())
				.placaVehiculo(datos.getPlacaVehiculo())
				.tipoVehiculo(tipoVehiculo)
				.build();
		daoFactory.getVehiculoDAO().crear(entidad);
	}

}

