package co.uco.erzparking.negocio.fachada.zonaparqueadero.impl;

import java.util.UUID;

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
					? new ParqueaderoDTO.Builder()
							.id(resultado.getParqueadero().getId())
							.nombreEstablecimiento(resultado.getParqueadero().getNombreEstablecimiento())
							.build()
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

	public static void main(final String[] args) {
		try {
			var filtro = new ZonaParqueaderoDTO.Builder()
					.id(UUID.fromString("f71b7dc0-306a-4111-a652-3ebb9a619317"))
					.build();
			var resultado = new ConsultarZonaParqueaderoPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("ZonaParqueadero consultada: id=" + resultado.getId() + ", nombre=" + resultado.getNombreZona());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
