package co.uco.erzparking.negocio.casouso.tiposervicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TipoServicioEntidad;
import co.uco.erzparking.negocio.casouso.tiposervicio.RegistrarTipoServicioCasoUso;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarTipoServicioCasoUsoImpl implements RegistrarTipoServicioCasoUso {

	private DAOFactory daoFactory;

	public RegistrarTipoServicioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TipoServicioDominio datos) {
		validarIntegridadDatos(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final TipoServicioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipo de servicio son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getNombreServicio()) || datos.getNombreServicio().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del tipo de servicio es obligatorio");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getTipoServicioDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final TipoServicioDominio datos) {
		var entidad = new TipoServicioEntidad.Builder()
				.id(generarIdUnico())
				.nombreServicio(datos.getNombreServicio())
				.descripcion(datos.getDescripcion())
				.build();
		daoFactory.getTipoServicioDAO().crear(entidad);
	}
}
