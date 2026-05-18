package co.uco.erzparking.negocio.casouso.contratomensualidad.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ContratoMensualidadEntidad;
import co.uco.erzparking.negocio.casouso.contratomensualidad.ConsultarTodosContratoMensualidadsCasoUso;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;

public class ConsultarTodosContratoMensualidadsCasoUsoImpl implements ConsultarTodosContratoMensualidadsCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosContratoMensualidadsCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<ContratoMensualidadDominio> ejecutar(final ContratoMensualidadDominio datos) {
		return consultarTodos(datos);
	}

	private List<ContratoMensualidadDominio> consultarTodos(final ContratoMensualidadDominio filtro) {
		var entidades = daoFactory.getContratoMensualidadDAO().consultarPorFiltro(UtilObjeto.esNulo(filtro) ? null : new ContratoMensualidadEntidad.Builder().build());
		return entidades.stream()
				.map(this::mapearAdominio)
				.collect(java.util.stream.Collectors.toList());
	}

	private ContratoMensualidadDominio mapearAdominio(final ContratoMensualidadEntidad entidad) {
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
