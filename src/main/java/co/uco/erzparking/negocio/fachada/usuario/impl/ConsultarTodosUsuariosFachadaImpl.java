package co.uco.erzparking.negocio.fachada.usuario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.negocio.casouso.usuario.ConsultarTodosUsuariosCasoUso;
import co.uco.erzparking.negocio.casouso.usuario.impl.ConsultarTodosUsuariosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.fachada.usuario.ConsultarTodosUsuariosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosUsuariosFachadaImpl implements ConsultarTodosUsuariosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosUsuariosCasoUso casoUso;

	public ConsultarTodosUsuariosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosUsuariosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<UsuarioDTO> ejecutar(final UsuarioDTO datos) {
		try {

			var dominio = new UsuarioDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return resultado.stream().map(this::mapearADto).collect(java.util.stream.Collectors.toList());
		} catch (ERZParkingExcepcion excepcion) {
						throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	private UsuarioDTO mapearADto(final UsuarioDominio d) {
		var tipoDocumentoIdentificacionDTO = d.getTipoDocumentoIdentificacion() != null
				? new TipoDocumentoIdentificacionDTO.Builder()
						.id(d.getTipoDocumentoIdentificacion().getId())
						.nombreDocumentoIdentificacion(d.getTipoDocumentoIdentificacion().getNombreDocumentoIdentificacion())
						.descripcion(d.getTipoDocumentoIdentificacion().getDescripcion())
						.build()
				: null;
		var ciudadDTO = d.getCiudad() != null
				? new CiudadDTO.Builder()
						.id(d.getCiudad().getId())
						.nombre(d.getCiudad().getNombre())
						.build()
				: null;
		return new UsuarioDTO.Builder()
				.id(d.getId())
				.tipoDocumentoIdentificacion(tipoDocumentoIdentificacionDTO)
				.numeroIdentificacion(d.getNumeroIdentificacion())
				.primerNombre(d.getPrimerNombre())
				.segundoNombre(d.getSegundoNombre())
				.primerApellido(d.getPrimerApellido())
				.segundoApellido(d.getSegundoApellido())
				.numeroTelefonico(d.getNumeroTelefonico())
				.correoElectronico(d.getCorreoElectronico())
				.ciudad(ciudadDTO)
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new UsuarioDTO.Builder().build();
			var resultado = new ConsultarTodosUsuariosFachadaImpl().ejecutar(filtro);
			System.out.println("Total usuarios encontrados: " + resultado.size());
			resultado.forEach(u -> System.out.println(" - " + u.getId() + " | " + u.getPrimerNombre() + " " + u.getPrimerApellido()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
