package co.uco.erzparking.negocio.casouso.servicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.entidad.TipoServicioEntidad;
import co.uco.erzparking.negocio.casouso.servicio.RegistrarServicioCasoUso;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarServicioCasoUsoImpl implements RegistrarServicioCasoUso {

	private DAOFactory daoFactory;

	public RegistrarServicioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ServicioDominio datos) {
		validarIntegridadDatos(datos);
		validarDependencias(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final ServicioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del servicio son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getNombreServicio()) || datos.getNombreServicio().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del servicio es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getTipoServicio()) || UtilObjeto.esNulo(datos.getTipoServicio().getId())) {
			throw ERZParkingExcepcion.crear("El tipo de servicio es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getParqueadero()) || UtilObjeto.esNulo(datos.getParqueadero().getId())) {
			throw ERZParkingExcepcion.crear("El parqueadero del servicio es obligatorio");
		}
	}

	private void validarDependencias(final ServicioDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getTipoServicioDAO().consultarPorId(datos.getTipoServicio().getId()))) {
			throw ERZParkingExcepcion.crear("El tipo de servicio asociado no existe en el sistema");
		}
		if (UtilObjeto.esNulo(daoFactory.getParqueaderoDAO().consultarPorId(datos.getParqueadero().getId()))) {
			throw ERZParkingExcepcion.crear("El parqueadero asociado no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getServicioDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final ServicioDominio datos) {
		var tipoServicio = new TipoServicioEntidad.Builder()
				.id(datos.getTipoServicio().getId())
				.build();
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(datos.getParqueadero().getId())
				.build();
		var entidad = new ServicioEntidad.Builder()
				.id(generarIdUnico())
				.nombreServicio(datos.getNombreServicio())
				.tipoServicio(tipoServicio)
				.parqueadero(parqueadero)
				.build();
		daoFactory.getServicioDAO().crear(entidad);
	}
}
