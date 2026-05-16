package co.uco.erzparking.negocio.casouso.espaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.espaciofisico.ConsultarEspacioFisicoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarEspacioFisicoPorIdCasoUsoImpl implements ConsultarEspacioFisicoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarEspacioFisicoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public EspacioFisicoDominio ejecutar(final EspacioFisicoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final EspacioFisicoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del espacioFisico son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del espacioFisico es obligatorio para consultar");
		}
	}

	private EspacioFisicoDominio consultar(final EspacioFisicoDominio datos) {
		var entidad = daoFactory.getEspacioFisicoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El espacioFisico no existe en el sistema");
		}
		var tipoServicio = !UtilObjeto.esNulo(entidad.getTipoServicio())
				? new TipoServicioDominio.Builder()
						.id(entidad.getTipoServicio().getId())
						.nombreServicio(entidad.getTipoServicio().getNombreServicio())
						.build()
				: null;
		var estadoEspacioFisico = !UtilObjeto.esNulo(entidad.getEstadoEspacioFisico())
				? new EstadoEspacioFisicoDominio.Builder()
						.nombreEstadoEspacioFisico(entidad.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())
						.build()
				: null;
		return new EspacioFisicoDominio.Builder()
				.id(entidad.getId())
				.numeroEspacioFisico(entidad.getNumeroEspacioFisico())
				.tipoServicio(tipoServicio)
				.estadoEspacioFisico(estadoEspacioFisico)
				.build();
	}
}
