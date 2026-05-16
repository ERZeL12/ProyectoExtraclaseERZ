package co.uco.erzparking.negocio.casouso.departamento.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.DepartamentoEntidad;
import co.uco.erzparking.entidad.PaisEntidad;
import co.uco.erzparking.negocio.casouso.departamento.RegistrarDepartamentoCasoUso;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarDepartamentoCasoUsoImpl implements RegistrarDepartamentoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarDepartamentoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final DepartamentoDominio datos) {
		validarIntegridadDatos(datos);
		validarPaisExiste(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final DepartamentoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del departamento son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getNombre()) || datos.getNombre().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del departamento es obligatorio");
		}
		if (UtilObjeto.esNulo(datos.getPais()) || UtilObjeto.esNulo(datos.getPais().getId())) {
			throw ERZParkingExcepcion.crear("El pais del departamento es obligatorio");
		}
	}

	private void validarPaisExiste(final DepartamentoDominio datos) {
		if (UtilObjeto.esNulo(daoFactory.getPaisDAO().consultarPorId(datos.getPais().getId()))) {
			throw ERZParkingExcepcion.crear("El pais asociado no existe en el sistema");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getDepartamentoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final DepartamentoDominio datos) {
		var pais = new PaisEntidad.Builder()
				.id(datos.getPais().getId())
				.build();
		var entidad = new DepartamentoEntidad.Builder()
				.id(generarIdUnico())
				.nombre(datos.getNombre())
				.pais(pais)
				.build();
		daoFactory.getDepartamentoDAO().crear(entidad);
	}

}