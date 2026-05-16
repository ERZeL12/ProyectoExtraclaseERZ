package co.uco.erzparking.negocio.casouso.contratomensualidad.impl;

import java.util.List;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ContratoMensualidadEntidad;
import co.uco.erzparking.negocio.casouso.contratomensualidad.ConsultarTodosContratoMensualidadsCasoUso;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
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
