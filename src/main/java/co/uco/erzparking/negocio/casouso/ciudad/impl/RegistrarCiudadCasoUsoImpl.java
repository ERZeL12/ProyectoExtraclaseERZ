package co.uco.erzparking.negocio.casouso.ciudad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.entidad.DepartamentoEntidad;
import co.uco.erzparking.negocio.casouso.ciudad.RegistrarCiudadCasoUso;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarCiudadCasoUsoImpl implements RegistrarCiudadCasoUso {

	private DAOFactory daoFactory;

	public RegistrarCiudadCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final CiudadDominio datos) {
		validarIntegridadDatos(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final CiudadDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del ciudad son obligatorios");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getCiudadDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final CiudadDominio datos) {
		var departamento = new DepartamentoEntidad.Builder()
				.id(datos.getDepartamento().getId())
				.build();
		var entidad = new CiudadEntidad.Builder()
				.id(generarIdUnico())
				.nombre(datos.getNombre())
				.departamento(departamento)
				.build();
		daoFactory.getCiudadDAO().crear(entidad);
	}
}
