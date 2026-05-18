package co.uco.erzparking.negocio.fachada.zonaparqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.ConsultarTodosZonaParqueaderosCasoUso;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.impl.ConsultarTodosZonaParqueaderosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ConsultarTodosZonaParqueaderosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosZonaParqueaderosFachadaImpl implements ConsultarTodosZonaParqueaderosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosZonaParqueaderosCasoUso casoUso;

	public ConsultarTodosZonaParqueaderosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosZonaParqueaderosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<ZonaParqueaderoDTO> ejecutar(final ZonaParqueaderoDTO datos) {
		try {

			var dominio = new ZonaParqueaderoDominio.Builder().id(datos.getId()).build();
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

	private ZonaParqueaderoDTO mapearADto(final ZonaParqueaderoDominio d) {
		var parqueaderoDTO = d.getParqueadero() != null
				? new ParqueaderoDTO.Builder()
						.id(d.getParqueadero().getId())
						.nombreEstablecimiento(d.getParqueadero().getNombreEstablecimiento())
						.build()
				: null;
		return new ZonaParqueaderoDTO.Builder()
				.id(d.getId())
				.nombreZona(d.getNombreZona())
				.parqueadero(parqueaderoDTO)
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new ZonaParqueaderoDTO.Builder().build();
			var resultado = new ConsultarTodosZonaParqueaderosFachadaImpl().ejecutar(filtro);
			System.out.println("Total zonas parqueadero encontradas: " + resultado.size());
			resultado.forEach(z -> System.out.println(" - " + z.getId() + " | " + z.getNombreZona()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
