package co.uco.erzparking.negocio.fachada.entrada.impl;

import java.util.List;
import java.util.stream.Collectors;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EntradaDTO;
import co.uco.erzparking.negocio.assembler.dto.impl.EntradaDTOAssembler;
import co.uco.erzparking.negocio.casouso.entrada.ConsultarTodosEntradasCasoUso;
import co.uco.erzparking.negocio.casouso.entrada.impl.ConsultarTodosEntradasCasoUsoImpl;
import co.uco.erzparking.negocio.fachada.entrada.ConsultarTodosEntradasFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTodosEntradasFachadaImpl implements ConsultarTodosEntradasFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosEntradasCasoUso casoUso;

	public ConsultarTodosEntradasFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosEntradasCasoUsoImpl(daoFactory);
	}

	@Override
	public List<EntradaDTO> ejecutar(final EntradaDTO datos) {
		try {
			var dominio = EntradaDTOAssembler.getInstance().ensamblarDominio(datos);
			var resultado = casoUso.ejecutar(dominio);
			return resultado.stream()
					.map(EntradaDTOAssembler.getInstance()::ensamblarDTO)
					.collect(Collectors.toList());
		} catch (ERZParkingExcepcion excepcion) {
			throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
