package co.uco.erzparking.negocio.casouso.ciudad.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.negocio.casouso.ciudad.QuitarCiudadCasoUso;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarCiudadCasoUsoImpl implements QuitarCiudadCasoUso {

	private DAOFactory daoFactory;

	public QuitarCiudadCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final CiudadDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final CiudadDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del ciudad son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del ciudad es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getCiudadDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El ciudad no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var ciudad = new CiudadEntidad.Builder().id(id).build();
		var usuarios = daoFactory.getUsuarioDAO().consultarPorFiltro(new UsuarioEntidad.Builder().ciudad(ciudad).build());
		if (!usuarios.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar la ciudad porque tiene usuarios asociados");
		}
		var parqueaderos = daoFactory.getParqueaderoDAO().consultarPorFiltro(new ParqueaderoEntidad.Builder().ciudad(ciudad).build());
		if (!parqueaderos.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar la ciudad porque tiene parqueaderos asociados");
		}
	}

	private void quitar(final CiudadDominio datos) {
		daoFactory.getCiudadDAO().eliminar(datos.getId());
	}
}
