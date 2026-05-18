package co.uco.erzparking.negocio.fachada.pais.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.pais.ConsultarTodosPaissCasoUso;
import co.uco.erzparking.negocio.casouso.pais.impl.ConsultarTodosPaissCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.negocio.fachada.pais.ConsultarTodosPaissFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosPaissFachadaImpl implements ConsultarTodosPaissFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosPaissCasoUso casoUso;

	public ConsultarTodosPaissFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosPaissCasoUsoImpl(daoFactory);
	}

	@Override
	public List<PaisDTO> ejecutar(final PaisDTO datos) {
		try {

			var dominio = new PaisDominio.Builder().id(datos.getId()).build();
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

	private PaisDTO mapearADto(final PaisDominio d) {
		return new PaisDTO.Builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new PaisDTO.Builder().build();
			var resultado = new ConsultarTodosPaissFachadaImpl().ejecutar(filtro);
			System.out.println("Total paises encontrados: " + resultado.size());
			resultado.forEach(p -> System.out.println(" - " + p.getId() + " | " + p.getNombre()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
