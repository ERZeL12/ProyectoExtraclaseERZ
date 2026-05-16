package co.uco.erzparking.negocio.casouso.pais.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.PaisEntidad;
import co.uco.erzparking.negocio.casouso.pais.RegistrarPaisCasoUso;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarPaisCasoUsoImpl implements RegistrarPaisCasoUso {

	private DAOFactory daoFactory;

	public RegistrarPaisCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final PaisDominio datos) {
		validarIntegridadDatos(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final PaisDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del pais son obligatorios");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getPaisDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final PaisDominio datos) {
	    var entidad = new PaisEntidad.Builder()
	            .id(generarIdUnico())
	            .nombre(datos.getNombre())
	            .build();
	    daoFactory.getPaisDAO().crear(entidad);
	}
}
