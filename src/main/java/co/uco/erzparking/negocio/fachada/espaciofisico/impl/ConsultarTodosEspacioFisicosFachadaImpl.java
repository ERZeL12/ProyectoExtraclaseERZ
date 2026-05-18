package co.uco.erzparking.negocio.fachada.espaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.espaciofisico.ConsultarTodosEspacioFisicosCasoUso;
import co.uco.erzparking.negocio.casouso.espaciofisico.impl.ConsultarTodosEspacioFisicosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.fachada.espaciofisico.ConsultarTodosEspacioFisicosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosEspacioFisicosFachadaImpl implements ConsultarTodosEspacioFisicosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosEspacioFisicosCasoUso casoUso;

	public ConsultarTodosEspacioFisicosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosEspacioFisicosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<EspacioFisicoDTO> ejecutar(final EspacioFisicoDTO datos) {
		try {

			var dominio = new EspacioFisicoDominio.Builder().id(datos.getId()).build();
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

	private EspacioFisicoDTO mapearADto(final EspacioFisicoDominio d) {
		var tipoServicioDTO = d.getTipoServicio() != null
				? new TipoServicioDTO.Builder()
						.id(d.getTipoServicio().getId())
						.nombreServicio(d.getTipoServicio().getNombreServicio())
						.descripcion(d.getTipoServicio().getDescripcion())
						.build()
				: null;
		var estadoEspacioFisicoDTO = d.getEstadoEspacioFisico() != null
				? new EstadoEspacioFisicoDTO.Builder()
						.id(d.getEstadoEspacioFisico().getId())
						.nombreEstadoEspacioFisico(d.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())
						.build()
				: null;
		var zonaEspacioFisicoDTO = d.getZonaEspacioFisico() != null
				? new ZonaParqueaderoDTO.Builder()
						.id(d.getZonaEspacioFisico().getId())
						.nombreZona(d.getZonaEspacioFisico().getNombreZona())
						.build()
				: null;
		var parqueaderoDTO = d.getParqueadero() != null
				? new ParqueaderoDTO.Builder()
						.id(d.getParqueadero().getId())
						.nombreEstablecimiento(d.getParqueadero().getNombreEstablecimiento())
						.build()
				: null;
		return new EspacioFisicoDTO.Builder()
				.id(d.getId())
				.numeroEspacioFisico(d.getNumeroEspacioFisico())
				.tipoServicio(tipoServicioDTO)
				.estadoEspacioFisico(estadoEspacioFisicoDTO)
				.zonaEspacioFisico(zonaEspacioFisicoDTO)
				.parqueadero(parqueaderoDTO)
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new EspacioFisicoDTO.Builder().build();
			var resultado = new ConsultarTodosEspacioFisicosFachadaImpl().ejecutar(filtro);
			System.out.println("Total espacios fisicos encontrados: " + resultado.size());
			resultado.forEach(e -> System.out.println(" - " + e.getId() + " | numero=" + e.getNumeroEspacioFisico()
					+ " | estado=" + (e.getEstadoEspacioFisico() != null ? e.getEstadoEspacioFisico().getNombreEstadoEspacioFisico() : "(sin estado)")));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
