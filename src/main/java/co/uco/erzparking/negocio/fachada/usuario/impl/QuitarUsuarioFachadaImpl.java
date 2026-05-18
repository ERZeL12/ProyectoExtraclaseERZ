package co.uco.erzparking.negocio.fachada.usuario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.negocio.casouso.usuario.QuitarUsuarioCasoUso;
import co.uco.erzparking.negocio.casouso.usuario.impl.QuitarUsuarioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.fachada.usuario.QuitarUsuarioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarUsuarioFachadaImpl implements QuitarUsuarioFachada {

	private DAOFactory daoFactory;
	private QuitarUsuarioCasoUso casoUso;

	public QuitarUsuarioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarUsuarioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final UsuarioDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new UsuarioDominio.Builder().id(datos.getId()).build();
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
					.build();
			new QuitarUsuarioFachadaImpl().ejecutar(dto);
			System.out.println("Usuario eliminado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
