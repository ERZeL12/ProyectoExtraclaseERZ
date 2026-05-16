package co.uco.erzparking.negocio.fachada.usuario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.negocio.casouso.usuario.ActualizarUsuarioCasoUso;
import co.uco.erzparking.negocio.casouso.usuario.impl.ActualizarUsuarioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.fachada.usuario.ActualizarUsuarioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarUsuarioFachadaImpl implements ActualizarUsuarioFachada {

	private DAOFactory daoFactory;
	private ActualizarUsuarioCasoUso casoUso;

	public ActualizarUsuarioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarUsuarioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final UsuarioDTO datos) {
		try {

			var ciudad = datos.getCiudad() != null
					? new CiudadDominio.Builder().id(datos.getCiudad().getId()).build()
					: null;
				var dominio = new UsuarioDominio.Builder()
					.id(datos.getId())
					.primerNombre(datos.getPrimerNombre())
					.segundoNombre(datos.getSegundoNombre())
					.primerApellido(datos.getPrimerApellido())
					.segundoApellido(datos.getSegundoApellido())
					.numeroTelefonico(datos.getNumeroTelefonico())
					.correoElectronico(datos.getCorreoElectronico())
					.ciudad(ciudad)
					.build();
			casoUso.ejecutar(dominio);
			daoFactory.confirmarTransaccion();
		} catch (ERZParkingExcepcion excepcion) {
						daoFactory.cancelarTransaccion();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
				} finally {
			daoFactory.cerrarConexion();
		}
	}

}
