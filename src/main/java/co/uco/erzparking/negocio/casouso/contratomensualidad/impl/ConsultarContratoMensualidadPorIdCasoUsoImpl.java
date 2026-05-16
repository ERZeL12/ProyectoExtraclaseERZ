package co.uco.erzparking.negocio.casouso.contratomensualidad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.contratomensualidad.ConsultarContratoMensualidadPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
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
		var espacioFisico = !UtilObjeto.esNulo(entidad.getEspacioFisico())
				? new EspacioFisicoDominio.Builder().id(entidad.getEspacioFisico().getId()).build()
				: null;
		var usuarioVehiculo = !UtilObjeto.esNulo(entidad.getUsuarioVehiculo())
				? new UsuarioVehiculoDominio.Builder().id(entidad.getUsuarioVehiculo().getId()).build()
				: null;
		return new ContratoMensualidadDominio.Builder()
				.id(entidad.getId())
				.fechaInicioContrato(entidad.getFechaInicioContrato())
				.fechaFinContrato(entidad.getFechaFinContrato())
				.espacioFisico(espacioFisico)
				.usuarioVehiculo(usuarioVehiculo)
				.build();
	}
}
