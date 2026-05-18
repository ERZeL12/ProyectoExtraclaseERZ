package co.uco.erzparking.negocio.fachada.tiposervicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.casouso.tiposervicio.ConsultarTodosTipoServiciosCasoUso;
import co.uco.erzparking.negocio.casouso.tiposervicio.impl.ConsultarTodosTipoServiciosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.negocio.fachada.tiposervicio.ConsultarTodosTipoServiciosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosTipoServiciosFachadaImpl implements ConsultarTodosTipoServiciosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosTipoServiciosCasoUso casoUso;

	public ConsultarTodosTipoServiciosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosTipoServiciosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<TipoServicioDTO> ejecutar(final TipoServicioDTO datos) {
		try {

			var dominio = new TipoServicioDominio.Builder().id(datos.getId()).build();
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

	private TipoServicioDTO mapearADto(final TipoServicioDominio d) {
		return new TipoServicioDTO.Builder()
				.id(d.getId())
				.nombreServicio(d.getNombreServicio())
				.descripcion(d.getDescripcion())
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new TipoServicioDTO.Builder().build();
			var resultado = new ConsultarTodosTipoServiciosFachadaImpl().ejecutar(filtro);
			System.out.println("Total tipos servicio encontrados: " + resultado.size());
			resultado.forEach(t -> System.out.println(" - " + t.getId() + " | " + t.getNombreServicio()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
