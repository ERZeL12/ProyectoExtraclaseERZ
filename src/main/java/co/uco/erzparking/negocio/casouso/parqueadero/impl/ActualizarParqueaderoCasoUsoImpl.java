package co.uco.erzparking.negocio.casouso.parqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.negocio.casouso.parqueadero.ActualizarParqueaderoCasoUso;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarParqueaderoCasoUsoImpl implements ActualizarParqueaderoCasoUso {

	private DAOFactory daoFactory;

	public ActualizarParqueaderoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ParqueaderoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final ParqueaderoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del parqueadero son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del parqueadero es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getNombreEstablecimiento()) || datos.getNombreEstablecimiento().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del establecimiento es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getParqueaderoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El parqueadero que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final ParqueaderoDominio datos) {
		var entidad = new ParqueaderoEntidad.Builder()
				.id(datos.getId())
				.nombreEstablecimiento(datos.getNombreEstablecimiento())
				.numeroTelefonico(datos.getNumeroTelefonico())
				.correoElectronico(datos.getCorreoElectronico())
				.direccionEstablecimiento(datos.getDireccionEstablecimiento())
				.build();
		daoFactory.getParqueaderoDAO().actualizar(datos.getId(), entidad);
	}
}
