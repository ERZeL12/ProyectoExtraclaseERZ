package co.uco.erzparking.negocio.casouso.vehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.negocio.casouso.vehiculo.ActualizarVehiculoCasoUso;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarVehiculoCasoUsoImpl implements ActualizarVehiculoCasoUso {

	private DAOFactory daoFactory;

	public ActualizarVehiculoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final VehiculoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final VehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del vehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del vehiculo es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getPlacaVehiculo()) || datos.getPlacaVehiculo().isEmpty()) {
			throw ERZParkingExcepcion.crear("La placa del vehiculo es obligatoria");
		}
		if (UtilObjeto.esNulo(datos.getTipoVehiculo()) || UtilObjeto.esNulo(datos.getTipoVehiculo().getId())) {
			throw ERZParkingExcepcion.crear("El tipo del vehiculo es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getVehiculoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El vehiculo que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final VehiculoDominio datos) {
		var tipoVehiculo = new TipoVehiculoEntidad.Builder()
				.id(datos.getTipoVehiculo().getId())
				.build();
		var entidad = new VehiculoEntidad.Builder()
				.id(datos.getId())
				.placaVehiculo(datos.getPlacaVehiculo())
				.tipoVehiculo(tipoVehiculo)
				.build();
		daoFactory.getVehiculoDAO().actualizar(datos.getId(), entidad);
	}
}
