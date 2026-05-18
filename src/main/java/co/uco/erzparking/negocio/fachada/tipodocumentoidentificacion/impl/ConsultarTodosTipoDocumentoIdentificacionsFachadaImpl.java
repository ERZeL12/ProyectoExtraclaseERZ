package co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.ConsultarTodosTipoDocumentoIdentificacionsCasoUso;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.impl.ConsultarTodosTipoDocumentoIdentificacionsCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.ConsultarTodosTipoDocumentoIdentificacionsFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosTipoDocumentoIdentificacionsFachadaImpl implements ConsultarTodosTipoDocumentoIdentificacionsFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosTipoDocumentoIdentificacionsCasoUso casoUso;

	public ConsultarTodosTipoDocumentoIdentificacionsFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosTipoDocumentoIdentificacionsCasoUsoImpl(daoFactory);
	}

	@Override
	public List<TipoDocumentoIdentificacionDTO> ejecutar(final TipoDocumentoIdentificacionDTO datos) {
		try {

			var dominio = new TipoDocumentoIdentificacionDominio.Builder().id(datos.getId()).build();
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

	private TipoDocumentoIdentificacionDTO mapearADto(final TipoDocumentoIdentificacionDominio d) {
		return new TipoDocumentoIdentificacionDTO.Builder()
				.id(d.getId())
				.nombreDocumentoIdentificacion(d.getNombreDocumentoIdentificacion())
				.descripcion(d.getDescripcion())
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new TipoDocumentoIdentificacionDTO.Builder().build();
			var resultado = new ConsultarTodosTipoDocumentoIdentificacionsFachadaImpl().ejecutar(filtro);
			System.out.println("Total tipos documento encontrados: " + resultado.size());
			resultado.forEach(t -> System.out.println(" - " + t.getId() + " | " + t.getNombreDocumentoIdentificacion()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
