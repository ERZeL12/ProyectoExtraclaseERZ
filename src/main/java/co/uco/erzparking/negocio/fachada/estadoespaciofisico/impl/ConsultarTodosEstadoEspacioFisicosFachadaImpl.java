package co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.ConsultarTodosEstadoEspacioFisicosCasoUso;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.impl.ConsultarTodosEstadoEspacioFisicosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.ConsultarTodosEstadoEspacioFisicosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosEstadoEspacioFisicosFachadaImpl implements ConsultarTodosEstadoEspacioFisicosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosEstadoEspacioFisicosCasoUso casoUso;

	public ConsultarTodosEstadoEspacioFisicosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosEstadoEspacioFisicosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<EstadoEspacioFisicoDTO> ejecutar(final EstadoEspacioFisicoDTO datos) {
		try {

			var dominio = new EstadoEspacioFisicoDominio.Builder().id(datos.getId()).build();
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

	private EstadoEspacioFisicoDTO mapearADto(final EstadoEspacioFisicoDominio d) {
		return new EstadoEspacioFisicoDTO.Builder()
				.id(d.getId())
				.nombreEstadoEspacioFisico(d.getNombreEstadoEspacioFisico())
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new EstadoEspacioFisicoDTO.Builder().build();
			var resultado = new ConsultarTodosEstadoEspacioFisicosFachadaImpl().ejecutar(filtro);
			System.out.println("Total estados espacio fisico encontrados: " + resultado.size());
			resultado.forEach(e -> System.out.println(" - " + e.getId() + " | " + e.getNombreEstadoEspacioFisico()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
