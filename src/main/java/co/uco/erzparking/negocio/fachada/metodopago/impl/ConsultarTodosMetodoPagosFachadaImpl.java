package co.uco.erzparking.negocio.fachada.metodopago.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.MetodoPagoDTO;
import co.uco.erzparking.negocio.casouso.metodopago.ConsultarTodosMetodoPagosCasoUso;
import co.uco.erzparking.negocio.casouso.metodopago.impl.ConsultarTodosMetodoPagosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.negocio.fachada.metodopago.ConsultarTodosMetodoPagosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosMetodoPagosFachadaImpl implements ConsultarTodosMetodoPagosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosMetodoPagosCasoUso casoUso;

	public ConsultarTodosMetodoPagosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosMetodoPagosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<MetodoPagoDTO> ejecutar(final MetodoPagoDTO datos) {
		try {

			var dominio = new MetodoPagoDominio.Builder().id(datos.getId()).build();
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

	private MetodoPagoDTO mapearADto(final MetodoPagoDominio d) {
		return new MetodoPagoDTO.Builder()
				.id(d.getId())
				.nombreMetodoPago(d.getNombreMetodoPago())
				.descripcion(d.getDescripcion())
				.build();
	}

}
