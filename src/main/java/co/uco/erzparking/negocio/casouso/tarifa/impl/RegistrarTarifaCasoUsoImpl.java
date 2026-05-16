package co.uco.erzparking.negocio.casouso.tarifa.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.entidad.TarifaEntidad;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.negocio.casouso.tarifa.RegistrarTarifaCasoUso;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarTarifaCasoUsoImpl implements RegistrarTarifaCasoUso {

	private DAOFactory daoFactory;

	public RegistrarTarifaCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TarifaDominio datos) {
		validarIntegridadDatos(datos);
		validarDependencias(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final TarifaDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos de la tarifa son obligatorios");
		}
		if (datos.getValorServicio() <= 0) {
			throw ERZParkingExcepcion.crear("El valor del servicio debe ser mayor a cero");
		}
		if (UtilObjeto.esNulo(datos.getFechaInicioVigenciaTarifa())) {
			throw ERZParkingExcepcion.crear("La fecha de inicio de vigencia de la tarifa es obligatoria");
		}
		if (UtilObjeto.esNulo(datos.getTipoVehiculo()) || UtilObjeto.esNulo(datos.getTipoVehiculo().getId())) {
			throw ERZParkingExcepcion.crear("El tipo de vehiculo de la tarifa es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getServicio()) || UtilObjeto.esNulo(datos.getServicio().getId())) {
			throw ERZParkingExcepcion.crear("El servicio de la tarifa es obligatorio");
		}
	}

	private void validarDependencias(final TarifaDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getTipoVehiculoDAO().consultarPorId(datos.getTipoVehiculo().getId()))) {
			throw ERZParkingExcepcion.crear("El tipo de vehiculo asociado no existe en el sistema");
		}
		if (UtilObjeto.esNulo(daoFactory.getServicioDAO().consultarPorId(datos.getServicio().getId()))) {
			throw ERZParkingExcepcion.crear("El servicio asociado no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getTarifaDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final TarifaDominio datos) {
		var tipoVehiculo = new TipoVehiculoEntidad.Builder()
				.id(datos.getTipoVehiculo().getId())
				.build();
		var servicio = new ServicioEntidad.Builder()
				.id(datos.getServicio().getId())
				.build();
		var entidad = new TarifaEntidad.Builder()
				.id(generarIdUnico())
				.valorServicio(datos.getValorServicio())
				.fechaInicioVigenciaTarifa(datos.getFechaInicioVigenciaTarifa())
				.fechaFinVigenciaTarifa(datos.getFechaFinVigenciaTarifa())
				.tipoVehiculo(tipoVehiculo)
				.servicio(servicio)
				.build();
		daoFactory.getTarifaDAO().crear(entidad);
	}
}
