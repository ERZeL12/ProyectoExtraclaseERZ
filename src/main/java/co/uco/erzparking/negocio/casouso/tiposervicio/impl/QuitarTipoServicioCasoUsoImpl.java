package co.uco.erzparking.negocio.casouso.tiposervicio.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.entidad.TipoServicioEntidad;
import co.uco.erzparking.negocio.casouso.tiposervicio.QuitarTipoServicioCasoUso;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarTipoServicioCasoUsoImpl implements QuitarTipoServicioCasoUso {

	private DAOFactory daoFactory;

	public QuitarTipoServicioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TipoServicioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final TipoServicioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipoServicio son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del tipoServicio es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getTipoServicioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El tipoServicio no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var tipoServicio = new TipoServicioEntidad.Builder().id(id).build();
		var servicios = daoFactory.getServicioDAO().consultarPorFiltro(new ServicioEntidad.Builder().tipoServicio(tipoServicio).build());
		if (!servicios.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el tipo de servicio porque tiene servicios asociados");
		}
		var espacios = daoFactory.getEspacioFisicoDAO().consultarPorFiltro(new EspacioFisicoEntidad.Builder().tipoServicio(tipoServicio).build());
		if (!espacios.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el tipo de servicio porque tiene espacios fisicos asociados");
		}
	}

	private void quitar(final TipoServicioDominio datos) {
		daoFactory.getTipoServicioDAO().eliminar(datos.getId());
	}
}
