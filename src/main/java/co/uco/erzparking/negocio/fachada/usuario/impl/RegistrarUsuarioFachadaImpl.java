package co.uco.erzparking.negocio.fachada.usuario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.negocio.casouso.usuario.RegistrarUsuarioCasoUso;
import co.uco.erzparking.negocio.casouso.usuario.impl.RegistrarUsuarioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.fachada.usuario.RegistrarUsuarioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarUsuarioFachadaImpl implements RegistrarUsuarioFachada {

	private DAOFactory daoFactory;
	private RegistrarUsuarioCasoUso casoUso;

	public RegistrarUsuarioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarUsuarioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final UsuarioDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new UsuarioDominio.Builder()
					.id(datos.getId())
					.primerNombre(datos.getPrimerNombre())
					.segundoNombre(datos.getSegundoNombre())
					.primerApellido(datos.getPrimerApellido())
					.segundoApellido(datos.getSegundoApellido())
					.numeroIdentificacion(datos.getNumeroIdentificacion())
					.numeroTelefonico(datos.getNumeroTelefonico())
					.correoElectronico(datos.getCorreoElectronico())
					.tipoDocumentoIdentificacion(datos.getTipoDocumentoIdentificacion() != null ? new co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio.Builder().id(datos.getTipoDocumentoIdentificacion().getId()).build() : null)
					.ciudad(datos.getCiudad() != null ? new co.uco.erzparking.negocio.dominio.CiudadDominio.Builder().id(datos.getCiudad().getId()).build() : null)
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
			var dto = new co.uco.erzparking.dto.UsuarioDTO.Builder()
					.primerNombre("Juan")
					.segundoNombre("")
					.primerApellido("Gomez")
					.segundoApellido("")
					.numeroIdentificacion("1234567890")
					.numeroTelefonico(3126206033L)
					.correoElectronico("juan@correo.com")
					.tipoDocumentoIdentificacion(new co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO.Builder()
							.id(java.util.UUID.fromString("d6843f94-2ed3-47ff-b903-e7445142c816"))
							.build())
					.ciudad(new co.uco.erzparking.dto.CiudadDTO.Builder()
							.id(java.util.UUID.fromString("434e52b1-924a-4fd6-9a08-3b13d3362e72"))
							.build())
					.build();
			new RegistrarUsuarioFachadaImpl().ejecutar(dto);
			System.out.println("Usuario registrado exitosamente.");
			
			System.out.println("Nombre: " + dto.getPrimerNombre());
			System.out.println("Apellido: " + dto.getPrimerApellido());
			System.out.println("TipoDoc ID: " + dto.getTipoDocumentoIdentificacion().getId());
			System.out.println("Ciudad ID: " + dto.getCiudad().getId());
			
		} catch (Exception e) {
		    System.err.println("Error: " + e.getMessage());
		    Throwable causa = e.getCause();
		    while (causa != null) {
		        System.err.println("Causa: " + causa.getMessage());
		        causa = causa.getCause();
		    }
		    e.printStackTrace();
		}
	}

}
