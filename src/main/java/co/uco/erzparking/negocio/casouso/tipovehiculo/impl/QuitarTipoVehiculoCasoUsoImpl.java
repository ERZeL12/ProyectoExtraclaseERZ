package co.uco.erzparking.negocio.casouso.tipovehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TarifaEntidad;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.negocio.casouso.tipovehiculo.QuitarTipoVehiculoCasoUso;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarTipoVehiculoCasoUsoImpl implements QuitarTipoVehiculoCasoUso {

	private DAOFactory daoFactory;

	public QuitarTipoVehiculoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TipoVehiculoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final TipoVehiculoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipoVehiculo son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del tipoVehiculo es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getTipoVehiculoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El tipoVehiculo no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var tipoVehiculo = new TipoVehiculoEntidad.Builder().id(id).build();
		var vehiculos = daoFactory.getVehiculoDAO().consultarPorFiltro(new VehiculoEntidad.Builder().tipoVehiculo(tipoVehiculo).build());
		if (!vehiculos.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el tipo de vehiculo porque tiene vehiculos asociados");
		}
		var tarifas = daoFactory.getTarifaDAO().consultarPorFiltro(new TarifaEntidad.Builder().tipoVehiculo(tipoVehiculo).build());
		if (!tarifas.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el tipo de vehiculo porque tiene tarifas asociadas");
		}
	}

	private void quitar(final TipoVehiculoDominio datos) {
		daoFactory.getTipoVehiculoDAO().eliminar(datos.getId());
	}
}
