package co.uco.erzparking.negocio.casouso.departamento.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.departamento.ConsultarDepartamentoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarDepartamentoPorIdCasoUsoImpl implements ConsultarDepartamentoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarDepartamentoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public DepartamentoDominio ejecutar(final DepartamentoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final DepartamentoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del departamento son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del departamento es obligatorio para consultar");
		}
	}

	private DepartamentoDominio consultar(final DepartamentoDominio datos) {
		var entidad = daoFactory.getDepartamentoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El departamento no existe en el sistema");
		}
		var pais = new PaisDominio.Builder()
				.id(entidad.getPais().getId())
				.nombre(entidad.getPais().getNombre())
				.build();
		return new DepartamentoDominio.Builder()
				.id(entidad.getId())
				.nombre(entidad.getNombre())
				.pais(pais)
				.build();
	}
}
