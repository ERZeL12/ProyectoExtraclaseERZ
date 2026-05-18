package co.uco.erzparking.negocio.casouso.contratomensualidad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.contratomensualidad.ConsultarContratoMensualidadPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarContratoMensualidadPorIdCasoUsoImpl implements ConsultarContratoMensualidadPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarContratoMensualidadPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public ContratoMensualidadDominio ejecutar(final ContratoMensualidadDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final ContratoMensualidadDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del contratoMensualidad son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del contratoMensualidad es obligatorio para consultar");
		}
	}

	private ContratoMensualidadDominio consultar(final ContratoMensualidadDominio datos) {
		var entidad = daoFactory.getContratoMensualidadDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El contratoMensualidad no existe en el sistema");
		}
		var tarifa = !UtilObjeto.esNulo(entidad.getTarifa())
				? new TarifaDominio.Builder()
						.id(entidad.getTarifa().getId())
						.valorServicio(entidad.getTarifa().getValorServicio())
						.build()
				: null;
		var espacioFisico = !UtilObjeto.esNulo(entidad.getEspacioFisico())
				? new EspacioFisicoDominio.Builder()
						.id(entidad.getEspacioFisico().getId())
						.numeroEspacioFisico(entidad.getEspacioFisico().getNumeroEspacioFisico())
						.build()
				: null;
		UsuarioVehiculoDominio usuarioVehiculo = null;
		if (!UtilObjeto.esNulo(entidad.getUsuarioVehiculo())) {
			var usuario = !UtilObjeto.esNulo(entidad.getUsuarioVehiculo().getUsuario())
					? new UsuarioDominio.Builder()
							.id(entidad.getUsuarioVehiculo().getUsuario().getId())
							.primerNombre(entidad.getUsuarioVehiculo().getUsuario().getPrimerNombre())
							.primerApellido(entidad.getUsuarioVehiculo().getUsuario().getPrimerApellido())
							.build()
					: null;
			var vehiculo = !UtilObjeto.esNulo(entidad.getUsuarioVehiculo().getVehiculo())
					? new VehiculoDominio.Builder()
							.id(entidad.getUsuarioVehiculo().getVehiculo().getId())
							.placaVehiculo(entidad.getUsuarioVehiculo().getVehiculo().getPlacaVehiculo())
							.build()
					: null;
			usuarioVehiculo = new UsuarioVehiculoDominio.Builder()
					.id(entidad.getUsuarioVehiculo().getId())
					.usuario(usuario)
					.vehiculo(vehiculo)
					.build();
		}
		return new ContratoMensualidadDominio.Builder()
				.id(entidad.getId())
				.fechaInicioContrato(entidad.getFechaInicioContrato())
				.fechaFinContrato(entidad.getFechaFinContrato())
				.tarifa(tarifa)
				.espacioFisico(espacioFisico)
				.usuarioVehiculo(usuarioVehiculo)
				.build();
	}
}
