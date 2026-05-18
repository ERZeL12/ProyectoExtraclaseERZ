package co.uco.erzparking.negocio.fachada.usuario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CiudadDTO;
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
			daoFactory.iniciarTransaccion();
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

	public static void main(final String[] args) {
		try {
			var dto = new UsuarioDTO.Builder()
					.id(UUID.fromString("5bc6e94a-de0a-4511-8142-f41a38a5fd01"))
					.primerNombre("Juan")
					.segundoNombre("Carlos")
					.primerApellido("Gomez")
					.segundoApellido("Perez")
					.numeroTelefonico(3001234567L)
					.correoElectronico("juan.gomez@correo.com")
					.ciudad(new CiudadDTO.Builder()
							.id(UUID.fromString("434e52b1-924a-4fd6-9a08-3b13d3362e72"))
							.build())
					.build();
			new ActualizarUsuarioFachadaImpl().ejecutar(dto);
			System.out.println("Usuario actualizado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
