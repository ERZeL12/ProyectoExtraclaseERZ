package co.uco.erzparking.negocio.casouso.espaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.TipoServicioEntidad;
import co.uco.erzparking.entidad.ZonaParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.espaciofisico.RegistrarEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarEspacioFisicoCasoUsoImpl implements RegistrarEspacioFisicoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarEspacioFisicoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final EspacioFisicoDominio datos) {
		validarIntegridadDatos(datos);
		validarDependencias(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final EspacioFisicoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del espacio fisico son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getTipoServicio()) || UtilObjeto.esNulo(datos.getTipoServicio().getId())) {
			throw ERZParkingExcepcion.crear("El tipo de servicio del espacio fisico es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getEstadoEspacioFisico()) || UtilObjeto.esNulo(datos.getEstadoEspacioFisico().getId())) {
			throw ERZParkingExcepcion.crear("El estado del espacio fisico es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getZonaEspacioFisico()) || UtilObjeto.esNulo(datos.getZonaEspacioFisico().getId())) {
			throw ERZParkingExcepcion.crear("La zona del espacio fisico es obligatoria");
		}
		if (UtilObjeto.esNulo(datos.getParqueadero()) || UtilObjeto.esNulo(datos.getParqueadero().getId())) {
			throw ERZParkingExcepcion.crear("El parqueadero del espacio fisico es obligatorio");
		}
	}

	private void validarDependencias(final EspacioFisicoDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getTipoServicioDAO().consultarPorId(datos.getTipoServicio().getId()))) {
			throw ERZParkingExcepcion.crear("El tipo de servicio asociado no existe en el sistema");
		}
		if (UtilObjeto.esNulo(daoFactory.getEstadoEspacioFisicoDAO().consultarPorId(datos.getEstadoEspacioFisico().getId()))) {
			throw ERZParkingExcepcion.crear("El estado del espacio fisico asociado no existe en el sistema");
		}
		if (UtilObjeto.esNulo(daoFactory.getZonaParqueaderoDAO().consultarPorId(datos.getZonaEspacioFisico().getId()))) {
			throw ERZParkingExcepcion.crear("La zona del parqueadero asociada no existe en el sistema");
		}
		if (UtilObjeto.esNulo(daoFactory.getParqueaderoDAO().consultarPorId(datos.getParqueadero().getId()))) {
			throw ERZParkingExcepcion.crear("El parqueadero asociado no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getEspacioFisicoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private String determinarNumeroEspacioFisico(final EspacioFisicoDominio datos) {
		if (!UtilTexto.esNula(datos.getNumeroEspacioFisico()) && !datos.getNumeroEspacioFisico().isEmpty()) {
			return datos.getNumeroEspacioFisico();
		}
		var total = daoFactory.getEspacioFisicoDAO().consultarTodos().size();
		return "E-" + (total + 1);
	}

	private void guardar(final EspacioFisicoDominio datos) {
		var tipoServicio = new TipoServicioEntidad.Builder()
				.id(datos.getTipoServicio().getId())
				.build();
		var estado = new EstadoEspacioFisicoEntidad.Builder()
				.id(datos.getEstadoEspacioFisico().getId())
				.build();
		var zona = new ZonaParqueaderoEntidad.Builder()
				.id(datos.getZonaEspacioFisico().getId())
				.build();
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(datos.getParqueadero().getId())
				.build();
		var entidad = new EspacioFisicoEntidad.Builder()
				.id(generarIdUnico())
				.numeroEspacioFisico(determinarNumeroEspacioFisico(datos))
				.tipoServicio(tipoServicio)
				.estadoEspacioFisico(estado)
				.zonaEspacioFisico(zona)
				.parqueadero(parqueadero)
				.build();
		daoFactory.getEspacioFisicoDAO().crear(entidad);
	}
}
