package co.uco.erzparking.negocio.fachada.servicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.casouso.servicio.ConsultarTodosServiciosCasoUso;
import co.uco.erzparking.negocio.casouso.servicio.impl.ConsultarTodosServiciosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.fachada.servicio.ConsultarTodosServiciosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosServiciosFachadaImpl implements ConsultarTodosServiciosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosServiciosCasoUso casoUso;

	public ConsultarTodosServiciosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosServiciosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<ServicioDTO> ejecutar(final ServicioDTO datos) {
		try {

			var dominio = new ServicioDominio.Builder().id(datos.getId()).build();
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

	private ServicioDTO mapearADto(final ServicioDominio d) {
		var tipoServicioDTO = d.getTipoServicio() != null
				? new TipoServicioDTO.Builder()
						.id(d.getTipoServicio().getId())
						.nombreServicio(d.getTipoServicio().getNombreServicio())
						.descripcion(d.getTipoServicio().getDescripcion())
						.build()
				: null;
		var parqueaderoDTO = d.getParqueadero() != null
				? new ParqueaderoDTO.Builder()
						.id(d.getParqueadero().getId())
						.nombreEstablecimiento(d.getParqueadero().getNombreEstablecimiento())
						.build()
				: null;
		return new ServicioDTO.Builder()
				.id(d.getId())
				.nombreServicio(d.getNombreServicio())
				.tipoServicio(tipoServicioDTO)
				.parqueadero(parqueaderoDTO)
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new ServicioDTO.Builder().build();
			var resultado = new ConsultarTodosServiciosFachadaImpl().ejecutar(filtro);
			System.out.println("Total servicios encontrados: " + resultado.size());
			resultado.forEach(s -> System.out.println(" - " + s.getId() + " | " + s.getNombreServicio()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
