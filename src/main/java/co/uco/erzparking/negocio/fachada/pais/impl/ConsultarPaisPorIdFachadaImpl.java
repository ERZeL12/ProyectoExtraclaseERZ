package co.uco.erzparking.negocio.fachada.pais.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.pais.ConsultarPaisPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.pais.impl.ConsultarPaisPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.negocio.fachada.pais.ConsultarPaisPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarPaisPorIdFachadaImpl implements ConsultarPaisPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarPaisPorIdCasoUso casoUso;

	public ConsultarPaisPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarPaisPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public PaisDTO ejecutar(final PaisDTO datos) {
		try {

			var dominio = new PaisDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return new PaisDTO.Builder()
					.id(resultado.getId())
					.nombre(resultado.getNombre())
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
