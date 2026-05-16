package co.uco.erzparking.negocio.fachada.parqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.parqueadero.ConsultarTodosParqueaderosCasoUso;
import co.uco.erzparking.negocio.casouso.parqueadero.impl.ConsultarTodosParqueaderosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.parqueadero.ConsultarTodosParqueaderosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosParqueaderosFachadaImpl implements ConsultarTodosParqueaderosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosParqueaderosCasoUso casoUso;

	public ConsultarTodosParqueaderosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosParqueaderosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<ParqueaderoDTO> ejecutar(final ParqueaderoDTO datos) {
		try {

			var dominio = new ParqueaderoDominio.Builder().id(datos.getId()).build();
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

	private ParqueaderoDTO mapearADto(final ParqueaderoDominio d) {
		var ciudadDTO = d.getCiudad() != null
				? new CiudadDTO.Builder().id(d.getCiudad().getId()).build()
				: null;
		return new ParqueaderoDTO.Builder()
				.id(d.getId())
				.nombreEstablecimiento(d.getNombreEstablecimiento())
				.numeroTelefonico(d.getNumeroTelefonico())
				.correoElectronico(d.getCorreoElectronico())
				.direccionEstablecimiento(d.getDireccionEstablecimiento())
				.ciudad(ciudadDTO)
				.build();
	}

}
