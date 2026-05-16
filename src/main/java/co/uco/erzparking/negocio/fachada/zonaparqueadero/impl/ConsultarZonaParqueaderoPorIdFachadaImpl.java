package co.uco.erzparking.negocio.fachada.zonaparqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.ConsultarZonaParqueaderoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.zonaparqueadero.impl.ConsultarZonaParqueaderoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ConsultarZonaParqueaderoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarZonaParqueaderoPorIdFachadaImpl implements ConsultarZonaParqueaderoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarZonaParqueaderoPorIdCasoUso casoUso;

	public ConsultarZonaParqueaderoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarZonaParqueaderoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public ZonaParqueaderoDTO ejecutar(final ZonaParqueaderoDTO datos) {
		try {

			var dominio = new ZonaParqueaderoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var parqueaderoDTO = resultado.getParqueadero() != null
					? new ParqueaderoDTO.Builder().id(resultado.getParqueadero().getId()).build()
					: null;
				return new ZonaParqueaderoDTO.Builder()
					.id(resultado.getId())
					.nombreZona(resultado.getNombreZona())
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

}
