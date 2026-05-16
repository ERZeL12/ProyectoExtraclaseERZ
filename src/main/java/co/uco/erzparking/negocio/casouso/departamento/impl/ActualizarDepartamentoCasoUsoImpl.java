package co.uco.erzparking.negocio.casouso.departamento.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.DepartamentoEntidad;
import co.uco.erzparking.negocio.casouso.departamento.ActualizarDepartamentoCasoUso;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarDepartamentoCasoUsoImpl implements ActualizarDepartamentoCasoUso {

	private DAOFactory daoFactory;

	public ActualizarDepartamentoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final DepartamentoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final DepartamentoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del departamento son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del departamento es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getNombre()) || datos.getNombre().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del departamento es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getDepartamentoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El departamento que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final DepartamentoDominio datos) {
		var entidad = new DepartamentoEntidad.Builder()
				.id(datos.getId())
				.nombre(datos.getNombre())
				.build();
		daoFactory.getDepartamentoDAO().actualizar(datos.getId(), entidad);
	}
}
