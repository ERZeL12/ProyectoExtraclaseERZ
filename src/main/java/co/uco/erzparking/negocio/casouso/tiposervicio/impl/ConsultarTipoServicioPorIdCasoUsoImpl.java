package co.uco.erzparking.negocio.casouso.tiposervicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.tiposervicio.ConsultarTipoServicioPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTipoServicioPorIdCasoUsoImpl implements ConsultarTipoServicioPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTipoServicioPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public TipoServicioDominio ejecutar(final TipoServicioDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final TipoServicioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipoServicio son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del tipoServicio es obligatorio para consultar");
		}
	}

	private TipoServicioDominio consultar(final TipoServicioDominio datos) {
		var entidad = daoFactory.getTipoServicioDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El tipoServicio no existe en el sistema");
		}
		return new TipoServicioDominio.Builder()
				.id(entidad.getId())
				.nombreServicio(entidad.getNombreServicio())
				.descripcion(entidad.getDescripcion())
				.build();
	}
}
