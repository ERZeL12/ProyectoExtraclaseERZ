package co.uco.erzparking.negocio.casouso.pais.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.PaisEntidad;
import co.uco.erzparking.negocio.casouso.pais.ActualizarPaisCasoUso;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarPaisCasoUsoImpl implements ActualizarPaisCasoUso {

	private DAOFactory daoFactory;

	public ActualizarPaisCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final PaisDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final PaisDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del pais son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del pais es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getNombre()) || datos.getNombre().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del pais es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getPaisDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El pais que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final PaisDominio datos) {
		var entidad = new PaisEntidad.Builder()
				.id(datos.getId())
				.nombre(datos.getNombre())
				.build();
		daoFactory.getPaisDAO().actualizar(datos.getId(), entidad);
	}
}
