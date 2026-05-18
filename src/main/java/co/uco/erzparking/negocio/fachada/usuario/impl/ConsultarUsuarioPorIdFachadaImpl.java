package co.uco.erzparking.negocio.fachada.usuario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.negocio.casouso.usuario.ConsultarUsuarioPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.usuario.impl.ConsultarUsuarioPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.fachada.usuario.ConsultarUsuarioPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarUsuarioPorIdFachadaImpl implements ConsultarUsuarioPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarUsuarioPorIdCasoUso casoUso;

	public ConsultarUsuarioPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarUsuarioPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public UsuarioDTO ejecutar(final UsuarioDTO datos) {
		try {

			var dominio = new UsuarioDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var tipoDocumentoIdentificacionDTO = resultado.getTipoDocumentoIdentificacion() != null
					? new TipoDocumentoIdentificacionDTO.Builder()
							.id(resultado.getTipoDocumentoIdentificacion().getId())
							.nombreDocumentoIdentificacion(resultado.getTipoDocumentoIdentificacion().getNombreDocumentoIdentificacion())
							.descripcion(resultado.getTipoDocumentoIdentificacion().getDescripcion())
							.build()
					: null;
			var ciudadDTO = resultado.getCiudad() != null
					? new CiudadDTO.Builder()
							.id(resultado.getCiudad().getId())
							.nombre(resultado.getCiudad().getNombre())
							.build()
					: null;
				return new UsuarioDTO.Builder()
					.id(resultado.getId())
					.tipoDocumentoIdentificacion(tipoDocumentoIdentificacionDTO)
					.numeroIdentificacion(resultado.getNumeroIdentificacion())
					.primerNombre(resultado.getPrimerNombre())
					.segundoNombre(resultado.getSegundoNombre())
					.primerApellido(resultado.getPrimerApellido())
					.segundoApellido(resultado.getSegundoApellido())
					.numeroTelefonico(resultado.getNumeroTelefonico())
					.correoElectronico(resultado.getCorreoElectronico())
					.ciudad(ciudadDTO)
					.build();
		} catch (ERZParkingExcepcion excepcion) {
						throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	public static void main(final String[] args) {
		try {
			var filtro = new UsuarioDTO.Builder()
					.id(UUID.fromString("5bc6e94a-de0a-4511-8142-f41a38a5fd01"))
					.build();
			var resultado = new ConsultarUsuarioPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("Usuario consultado: id=" + resultado.getId()
					+ ", nombres=" + resultado.getPrimerNombre() + " " + resultado.getPrimerApellido()
					+ ", correo=" + resultado.getCorreoElectronico());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
