package co.uco.erzparking.negocio.casouso.servicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.negocio.casouso.servicio.ActualizarServicioCasoUso;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarServicioCasoUsoImpl implements ActualizarServicioCasoUso {

	private DAOFactory daoFactory;

	public ActualizarServicioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ServicioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final ServicioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del servicio son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del servicio es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getNombreServicio()) || datos.getNombreServicio().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del servicio es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getServicioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El servicio que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final ServicioDominio datos) {
		var entidad = new ServicioEntidad.Builder()
				.id(datos.getId())
				.nombreServicio(datos.getNombreServicio())
				.build();
		daoFactory.getServicioDAO().actualizar(datos.getId(), entidad);
	}
}
