package co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.ConsultarEstadoEspacioFisicoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.estadoespaciofisico.impl.ConsultarEstadoEspacioFisicoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.ConsultarEstadoEspacioFisicoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarEstadoEspacioFisicoPorIdFachadaImpl implements ConsultarEstadoEspacioFisicoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarEstadoEspacioFisicoPorIdCasoUso casoUso;

	public ConsultarEstadoEspacioFisicoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarEstadoEspacioFisicoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public EstadoEspacioFisicoDTO ejecutar(final EstadoEspacioFisicoDTO datos) {
		try {

			var dominio = new EstadoEspacioFisicoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return new EstadoEspacioFisicoDTO.Builder()
					.id(resultado.getId())
					.nombreEstadoEspacioFisico(resultado.getNombreEstadoEspacioFisico())
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
			var filtro = new EstadoEspacioFisicoDTO.Builder()
					.id(UUID.fromString("75db75b1-3f27-4e47-bf10-4f2e42764cf8"))
					.build();
			var resultado = new ConsultarEstadoEspacioFisicoPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("EstadoEspacioFisico consultado: id=" + resultado.getId() + ", nombre=" + resultado.getNombreEstadoEspacioFisico());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
