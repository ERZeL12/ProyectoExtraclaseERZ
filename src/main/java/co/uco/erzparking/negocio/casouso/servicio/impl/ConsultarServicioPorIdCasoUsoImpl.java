package co.uco.erzparking.negocio.casouso.servicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.servicio.ConsultarServicioPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarServicioPorIdCasoUsoImpl implements ConsultarServicioPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarServicioPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public ServicioDominio ejecutar(final ServicioDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final ServicioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del servicio son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del servicio es obligatorio para consultar");
		}
	}

	private ServicioDominio consultar(final ServicioDominio datos) {
		var entidad = daoFactory.getServicioDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El servicio no existe en el sistema");
		}
		var tipoServicio = !UtilObjeto.esNulo(entidad.getTipoServicio())
				? new TipoServicioDominio.Builder()
						.id(entidad.getTipoServicio().getId())
						.nombreServicio(entidad.getTipoServicio().getNombreServicio())
						.descripcion(entidad.getTipoServicio().getDescripcion())
						.build()
				: null;
		var parqueadero = !UtilObjeto.esNulo(entidad.getParqueadero())
				? new ParqueaderoDominio.Builder()
						.id(entidad.getParqueadero().getId())
						.nombreEstablecimiento(entidad.getParqueadero().getNombreEstablecimiento())
						.build()
				: null;
		return new ServicioDominio.Builder()
				.id(entidad.getId())
				.nombreServicio(entidad.getNombreServicio())
				.tipoServicio(tipoServicio)
				.parqueadero(parqueadero)
				.build();
	}
}
