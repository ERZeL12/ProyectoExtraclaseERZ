package co.uco.erzparking.negocio.fachada.departamento.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.departamento.ConsultarTodosDepartamentosCasoUso;
import co.uco.erzparking.negocio.casouso.departamento.impl.ConsultarTodosDepartamentosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.fachada.departamento.ConsultarTodosDepartamentosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosDepartamentosFachadaImpl implements ConsultarTodosDepartamentosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosDepartamentosCasoUso casoUso;

	public ConsultarTodosDepartamentosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosDepartamentosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<DepartamentoDTO> ejecutar(final DepartamentoDTO datos) {
		try {

			var dominio = new DepartamentoDominio.Builder().id(datos.getId()).build();
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

	private DepartamentoDTO mapearADto(final DepartamentoDominio d) {
		var paisDTO = d.getPais() != null
				? new PaisDTO.Builder()
						.id(d.getPais().getId())
						.nombre(d.getPais().getNombre())
						.build()
				: null;
		return new DepartamentoDTO.Builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.pais(paisDTO)
				.build();
	}

}
