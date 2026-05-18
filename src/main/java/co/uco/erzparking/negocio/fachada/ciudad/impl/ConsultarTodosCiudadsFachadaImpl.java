package co.uco.erzparking.negocio.fachada.ciudad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.ciudad.ConsultarTodosCiudadsCasoUso;
import co.uco.erzparking.negocio.casouso.ciudad.impl.ConsultarTodosCiudadsCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.fachada.ciudad.ConsultarTodosCiudadsFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosCiudadsFachadaImpl implements ConsultarTodosCiudadsFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosCiudadsCasoUso casoUso;

	public ConsultarTodosCiudadsFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosCiudadsCasoUsoImpl(daoFactory);
	}

	@Override
	public List<CiudadDTO> ejecutar(final CiudadDTO datos) {
		try {

			var dominio = new CiudadDominio.Builder().id(datos.getId()).build();
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

	private CiudadDTO mapearADto(final CiudadDominio d) {
		var paisDTO = d.getDepartamento() != null && d.getDepartamento().getPais() != null
				? new PaisDTO.Builder()
						.id(d.getDepartamento().getPais().getId())
						.nombre(d.getDepartamento().getPais().getNombre())
						.build()
				: null;
		var departamentoDTO = d.getDepartamento() != null
				? new DepartamentoDTO.Builder()
						.id(d.getDepartamento().getId())
						.nombre(d.getDepartamento().getNombre())
						.pais(paisDTO)
						.build()
				: null;
		return new CiudadDTO.Builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.departamento(departamentoDTO)
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new CiudadDTO.Builder().build();
			var resultado = new ConsultarTodosCiudadsFachadaImpl().ejecutar(filtro);
			System.out.println("Total ciudades encontradas: " + resultado.size());
			resultado.forEach(c -> System.out.println(" - " + c.getId() + " | " + c.getNombre()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
