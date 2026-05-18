package co.uco.erzparking.negocio.fachada.espaciofisico.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.espaciofisico.ConsultarEspacioFisicoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.espaciofisico.impl.ConsultarEspacioFisicoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.fachada.espaciofisico.ConsultarEspacioFisicoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarEspacioFisicoPorIdFachadaImpl implements ConsultarEspacioFisicoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarEspacioFisicoPorIdCasoUso casoUso;

	public ConsultarEspacioFisicoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarEspacioFisicoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public EspacioFisicoDTO ejecutar(final EspacioFisicoDTO datos) {
		try {

			var dominio = new EspacioFisicoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var tipoServicioDTO = resultado.getTipoServicio() != null
					? new TipoServicioDTO.Builder()
							.id(resultado.getTipoServicio().getId())
							.nombreServicio(resultado.getTipoServicio().getNombreServicio())
							.descripcion(resultado.getTipoServicio().getDescripcion())
							.build()
					: null;
			var estadoEspacioFisicoDTO = resultado.getEstadoEspacioFisico() != null
					? new EstadoEspacioFisicoDTO.Builder()
							.id(resultado.getEstadoEspacioFisico().getId())
							.nombreEstadoEspacioFisico(resultado.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())
							.build()
					: null;
			var zonaEspacioFisicoDTO = resultado.getZonaEspacioFisico() != null
					? new ZonaParqueaderoDTO.Builder()
							.id(resultado.getZonaEspacioFisico().getId())
							.nombreZona(resultado.getZonaEspacioFisico().getNombreZona())
							.build()
					: null;
			var parqueaderoDTO = resultado.getParqueadero() != null
					? new ParqueaderoDTO.Builder()
							.id(resultado.getParqueadero().getId())
							.nombreEstablecimiento(resultado.getParqueadero().getNombreEstablecimiento())
							.build()
					: null;
				return new EspacioFisicoDTO.Builder()
					.id(resultado.getId())
					.numeroEspacioFisico(resultado.getNumeroEspacioFisico())
					.tipoServicio(tipoServicioDTO)
					.estadoEspacioFisico(estadoEspacioFisicoDTO)
					.zonaEspacioFisico(zonaEspacioFisicoDTO)
					.parqueadero(parqueaderoDTO)
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
			var filtro = new EspacioFisicoDTO.Builder()
					.id(UUID.fromString("046a876f-ff0a-4e78-9147-78fef43b9669"))
					.build();
			var resultado = new ConsultarEspacioFisicoPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("EspacioFisico consultado: id=" + resultado.getId()
					+ ", numero=" + resultado.getNumeroEspacioFisico()
					+ ", estado=" + (resultado.getEstadoEspacioFisico() != null ? resultado.getEstadoEspacioFisico().getNombreEstadoEspacioFisico() : "(sin estado)"));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
