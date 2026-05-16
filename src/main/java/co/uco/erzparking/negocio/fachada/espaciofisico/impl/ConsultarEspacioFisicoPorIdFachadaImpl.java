package co.uco.erzparking.negocio.fachada.espaciofisico.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.dto.TipoServicioDTO;
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
							.build()
					: null;
			var estadoEspacioFisicoDTO = resultado.getEstadoEspacioFisico() != null
					? new EstadoEspacioFisicoDTO.Builder()
							.nombreEstadoEspacioFisico(resultado.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())
							.build()
					: null;
				return new EspacioFisicoDTO.Builder()
					.id(resultado.getId())
					.numeroEspacioFisico(resultado.getNumeroEspacioFisico())
					.tipoServicio(tipoServicioDTO)
					.estadoEspacioFisico(estadoEspacioFisicoDTO)
					.build();
		} catch (ERZParkingExcepcion excepcion) {
						throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
