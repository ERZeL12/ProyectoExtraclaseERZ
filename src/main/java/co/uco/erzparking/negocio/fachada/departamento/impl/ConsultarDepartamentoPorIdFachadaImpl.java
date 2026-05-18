package co.uco.erzparking.negocio.fachada.departamento.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.departamento.ConsultarDepartamentoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.departamento.impl.ConsultarDepartamentoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.fachada.departamento.ConsultarDepartamentoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarDepartamentoPorIdFachadaImpl implements ConsultarDepartamentoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarDepartamentoPorIdCasoUso casoUso;

	public ConsultarDepartamentoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarDepartamentoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public DepartamentoDTO ejecutar(final DepartamentoDTO datos) {
		try {

			var dominio = new DepartamentoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var paisDTO = resultado.getPais() != null
					? new PaisDTO.Builder()
							.id(resultado.getPais().getId())
							.nombre(resultado.getPais().getNombre())
							.build()
					: null;
				return new DepartamentoDTO.Builder()
					.id(resultado.getId())
					.nombre(resultado.getNombre())
					.pais(paisDTO)
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
			var filtro = new DepartamentoDTO.Builder()
					.id(UUID.fromString("1f8a1ad3-3c61-4b6b-8ebd-e109aee6beb9"))
					.build();
			var resultado = new ConsultarDepartamentoPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("Departamento consultado: id=" + resultado.getId() + ", nombre=" + resultado.getNombre()
					+ ", pais=" + (resultado.getPais() != null ? resultado.getPais().getNombre() : "(sin pais)"));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
