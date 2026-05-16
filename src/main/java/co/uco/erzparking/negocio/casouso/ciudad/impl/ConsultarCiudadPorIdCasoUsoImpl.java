package co.uco.erzparking.negocio.casouso.ciudad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.ciudad.ConsultarCiudadPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarCiudadPorIdCasoUsoImpl implements ConsultarCiudadPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarCiudadPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public CiudadDominio ejecutar(final CiudadDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final CiudadDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del ciudad son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del ciudad es obligatorio para consultar");
		}
	}

	private CiudadDominio consultar(final CiudadDominio datos) {
		var entidad = daoFactory.getCiudadDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El ciudad no existe en el sistema");
		}
		var pais = new PaisDominio.Builder()
				.id(entidad.getDepartamento().getPais().getId())
				.nombre(entidad.getDepartamento().getPais().getNombre())
				.build();
		var departamento = new DepartamentoDominio.Builder()
				.id(entidad.getDepartamento().getId())
				.nombre(entidad.getDepartamento().getNombre())
				.pais(pais)
				.build();
		return new CiudadDominio.Builder()
				.id(entidad.getId())
				.nombre(entidad.getNombre())
				.departamento(departamento)
				.build();
	}
}
