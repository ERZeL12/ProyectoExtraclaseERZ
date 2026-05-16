package co.uco.erzparking.negocio.fachada.tiposervicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.casouso.tiposervicio.ConsultarTipoServicioPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.tiposervicio.impl.ConsultarTipoServicioPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.negocio.fachada.tiposervicio.ConsultarTipoServicioPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTipoServicioPorIdFachadaImpl implements ConsultarTipoServicioPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarTipoServicioPorIdCasoUso casoUso;

	public ConsultarTipoServicioPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTipoServicioPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public TipoServicioDTO ejecutar(final TipoServicioDTO datos) {
		try {

			var dominio = new TipoServicioDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return new TipoServicioDTO.Builder()
					.id(resultado.getId())
					.nombreServicio(resultado.getNombreServicio())
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
