package co.uco.erzparking.negocio.fachada.parqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.parqueadero.ConsultarParqueaderoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.parqueadero.impl.ConsultarParqueaderoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.parqueadero.ConsultarParqueaderoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarParqueaderoPorIdFachadaImpl implements ConsultarParqueaderoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarParqueaderoPorIdCasoUso casoUso;

	public ConsultarParqueaderoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarParqueaderoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public ParqueaderoDTO ejecutar(final ParqueaderoDTO datos) {
		try {

			var dominio = new ParqueaderoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var ciudadDTO = resultado.getCiudad() != null
					? new CiudadDTO.Builder().id(resultado.getCiudad().getId()).build()
					: null;
				return new ParqueaderoDTO.Builder()
					.id(resultado.getId())
					.nombreEstablecimiento(resultado.getNombreEstablecimiento())
					.numeroTelefonico(resultado.getNumeroTelefonico())
					.correoElectronico(resultado.getCorreoElectronico())
					.direccionEstablecimiento(resultado.getDireccionEstablecimiento())
					.ciudad(ciudadDTO)
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
