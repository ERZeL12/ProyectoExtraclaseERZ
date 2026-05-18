package co.uco.erzparking.negocio.fachada.ciudad.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.ciudad.ConsultarCiudadPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.ciudad.impl.ConsultarCiudadPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.fachada.ciudad.ConsultarCiudadPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarCiudadPorIdFachadaImpl implements ConsultarCiudadPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarCiudadPorIdCasoUso casoUso;

	public ConsultarCiudadPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarCiudadPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public CiudadDTO ejecutar(final CiudadDTO datos) {
		try {

			var dominio = new CiudadDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var paisDTO = resultado.getDepartamento() != null && resultado.getDepartamento().getPais() != null
					? new PaisDTO.Builder()
							.id(resultado.getDepartamento().getPais().getId())
							.nombre(resultado.getDepartamento().getPais().getNombre())
							.build()
					: null;
			var departamentoDTO = resultado.getDepartamento() != null
					? new DepartamentoDTO.Builder()
							.id(resultado.getDepartamento().getId())
							.nombre(resultado.getDepartamento().getNombre())
							.pais(paisDTO)
							.build()
					: null;
				return new CiudadDTO.Builder()
					.id(resultado.getId())
					.nombre(resultado.getNombre())
					.departamento(departamentoDTO)
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
			var filtro = new CiudadDTO.Builder()
					.id(UUID.fromString("434e52b1-924a-4fd6-9a08-3b13d3362e72"))
					.build();
			var resultado = new ConsultarCiudadPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("Ciudad consultada: id=" + resultado.getId() + ", nombre=" + resultado.getNombre()
					+ ", departamento=" + (resultado.getDepartamento() != null ? resultado.getDepartamento().getNombre() : "(sin departamento)"));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
