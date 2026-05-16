package co.uco.erzparking.negocio.casouso.zonaparqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.ZonaParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.RegistrarZonaParqueaderoCasoUso;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarZonaParqueaderoCasoUsoImpl implements RegistrarZonaParqueaderoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarZonaParqueaderoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ZonaParqueaderoDominio datos) {
		validarIntegridadDatos(datos);
		validarParqueaderoExiste(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final ZonaParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos de la zona del parqueadero son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getNombreZona()) || datos.getNombreZona().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre de la zona es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getParqueadero()) || UtilObjeto.esNulo(datos.getParqueadero().getId())) {
			throw ERZParkingExcepcion.crear("El parqueadero de la zona es obligatorio");
		}
	}

	private void validarParqueaderoExiste(final ZonaParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getParqueaderoDAO().consultarPorId(datos.getParqueadero().getId()))) {
			throw ERZParkingExcepcion.crear("El parqueadero asociado a la zona no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getZonaParqueaderoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final ZonaParqueaderoDominio datos) {
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(datos.getParqueadero().getId())
				.build();
		var entidad = new ZonaParqueaderoEntidad.Builder()
				.id(generarIdUnico())
				.nombreZona(datos.getNombreZona())
				.parqueadero(parqueadero)
				.build();
		daoFactory.getZonaParqueaderoDAO().crear(entidad);
	}
}
