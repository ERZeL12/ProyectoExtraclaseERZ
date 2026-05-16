package co.uco.erzparking.negocio.fachada.metodopago.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.MetodoPagoDTO;
import co.uco.erzparking.negocio.casouso.metodopago.ConsultarMetodoPagoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.metodopago.impl.ConsultarMetodoPagoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.negocio.fachada.metodopago.ConsultarMetodoPagoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarMetodoPagoPorIdFachadaImpl implements ConsultarMetodoPagoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarMetodoPagoPorIdCasoUso casoUso;

	public ConsultarMetodoPagoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarMetodoPagoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public MetodoPagoDTO ejecutar(final MetodoPagoDTO datos) {
		try {

			var dominio = new MetodoPagoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return new MetodoPagoDTO.Builder()
					.id(resultado.getId())
					.nombreMetodoPago(resultado.getNombreMetodoPago())
					.descripcion(resultado.getDescripcion())
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
