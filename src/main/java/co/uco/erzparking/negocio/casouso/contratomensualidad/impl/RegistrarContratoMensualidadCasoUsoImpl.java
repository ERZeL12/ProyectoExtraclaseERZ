package co.uco.erzparking.negocio.casouso.contratomensualidad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ContratoMensualidadEntidad;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.TarifaEntidad;
import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;
import co.uco.erzparking.negocio.casouso.contratomensualidad.RegistrarContratoMensualidadCasoUso;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarContratoMensualidadCasoUsoImpl implements RegistrarContratoMensualidadCasoUso {

	private DAOFactory daoFactory;

	public RegistrarContratoMensualidadCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ContratoMensualidadDominio datos) {
		validarIntegridadDatos(datos);
		validarDependencias(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final ContratoMensualidadDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del contrato de mensualidad son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getFechaInicioContrato())) {
			throw ERZParkingExcepcion.crear("La fecha de inicio del contrato es obligatoria");
		}
		if (UtilObjeto.esNulo(datos.getFechaFinContrato())) {
			throw ERZParkingExcepcion.crear("La fecha de fin del contrato es obligatoria");
		}
		if (UtilObjeto.esNulo(datos.getTarifa()) || UtilObjeto.esNulo(datos.getTarifa().getId())) {
			throw ERZParkingExcepcion.crear("La tarifa del contrato es obligatoria");
		}
		if (UtilObjeto.esNulo(datos.getUsuarioVehiculo()) || UtilObjeto.esNulo(datos.getUsuarioVehiculo().getId())) {
			throw ERZParkingExcepcion.crear("El usuarioVehiculo del contrato es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getEspacioFisico()) || UtilObjeto.esNulo(datos.getEspacioFisico().getId())) {
			throw ERZParkingExcepcion.crear("El espacio fisico del contrato es obligatorio");
		}
	}

	private void validarDependencias(final ContratoMensualidadDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getTarifaDAO().consultarPorId(datos.getTarifa().getId()))) {
			throw ERZParkingExcepcion.crear("La tarifa asociada no existe en el sistema");
		}
		if (UtilObjeto.esNulo(daoFactory.getUsuarioVehiculoDAO().consultarPorId(datos.getUsuarioVehiculo().getId()))) {
			throw ERZParkingExcepcion.crear("El usuarioVehiculo asociado no existe en el sistema");
		}
		if (UtilObjeto.esNulo(daoFactory.getEspacioFisicoDAO().consultarPorId(datos.getEspacioFisico().getId()))) {
			throw ERZParkingExcepcion.crear("El espacio fisico asociado no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getContratoMensualidadDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final ContratoMensualidadDominio datos) {
		var tarifa = new TarifaEntidad.Builder()
				.id(datos.getTarifa().getId())
				.build();
		var usuarioVehiculo = new UsuarioVehiculoEntidad.Builder()
				.id(datos.getUsuarioVehiculo().getId())
				.build();
		var espacioFisico = new EspacioFisicoEntidad.Builder()
				.id(datos.getEspacioFisico().getId())
				.build();
		var entidad = new ContratoMensualidadEntidad.Builder()
				.id(generarIdUnico())
				.fechaInicioContrato(datos.getFechaInicioContrato())
				.fechaFinContrato(datos.getFechaFinContrato())
				.tarifa(tarifa)
				.usuarioVehiculo(usuarioVehiculo)
				.espacioFisico(espacioFisico)
				.build();
		daoFactory.getContratoMensualidadDAO().crear(entidad);
	}
}
