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

}
