package co.uco.erzparking.negocio.casouso.departamento.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.entidad.DepartamentoEntidad;
import co.uco.erzparking.negocio.casouso.departamento.QuitarDepartamentoCasoUso;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarDepartamentoCasoUsoImpl implements QuitarDepartamentoCasoUso {

	private DAOFactory daoFactory;

	public QuitarDepartamentoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final DepartamentoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final DepartamentoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del departamento son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del departamento es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getDepartamentoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El departamento no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var departamento = new DepartamentoEntidad.Builder().id(id).build();
		var ciudades = daoFactory.getCiudadDAO().consultarPorFiltro(new CiudadEntidad.Builder().departamento(departamento).build());
		if (!ciudades.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el departamento porque tiene ciudades asociadas");
		}
	}

	private void quitar(final DepartamentoDominio datos) {
		daoFactory.getDepartamentoDAO().eliminar(datos.getId());
	}
}
