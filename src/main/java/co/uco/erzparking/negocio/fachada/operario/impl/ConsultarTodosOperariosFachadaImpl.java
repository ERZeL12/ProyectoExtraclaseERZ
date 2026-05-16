package co.uco.erzparking.negocio.fachada.operario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.casouso.operario.ConsultarTodosOperariosCasoUso;
import co.uco.erzparking.negocio.casouso.operario.impl.ConsultarTodosOperariosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.negocio.fachada.operario.ConsultarTodosOperariosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultarTodosOperariosFachadaImpl implements ConsultarTodosOperariosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosOperariosCasoUso casoUso;

	public ConsultarTodosOperariosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosOperariosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<OperarioDTO> ejecutar(final OperarioDTO datos) {
		try {
			var dominio = new OperarioDominio.Builder().build();
			var resultados = casoUso.ejecutar(dominio);
			return resultados.stream()
					.map(this::mapearADto)
					.collect(Collectors.toList());
		} catch (ERZParkingExcepcion excepcion) {
			throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al consultar los operarios", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	private OperarioDTO mapearADto(final OperarioDominio d) {
		var tipoDocumentoIdentificacionDTO = d.getTipoDocumentoIdentificacion() != null
				? new TipoDocumentoIdentificacionDTO.Builder()
						.id(d.getTipoDocumentoIdentificacion().getId())
						.build()
				: null;
		var cargoDTO = d.getCargo() != null
				? new CargoDTO.Builder().id(d.getCargo().getId()).build()
				: null;
		var parqueaderoDTO = d.getParqueadero() != null
				? new ParqueaderoDTO.Builder().id(d.getParqueadero().getId()).build()
				: null;
		return new OperarioDTO.Builder()
				.id(d.getId())
				.tipoDocumentoIdentificacion(tipoDocumentoIdentificacionDTO)
				.numeroIdentificacion(d.getNumeroIdentificacion())
				.primerNombre(d.getPrimerNombre())
				.segundoNombre(d.getSegundoNombre())
				.primerApellido(d.getPrimerApellido())
				.segundoApellido(d.getSegundoApellido())
				.numeroTelefonico(d.getNumeroTelefonico())
				.cargo(cargoDTO)
				.parqueadero(parqueaderoDTO)
				.build();
	}

}