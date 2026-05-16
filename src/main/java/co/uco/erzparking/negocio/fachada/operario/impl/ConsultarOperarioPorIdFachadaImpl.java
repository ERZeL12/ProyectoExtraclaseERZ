package co.uco.erzparking.negocio.fachada.operario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.casouso.operario.ConsultarOperarioPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.operario.impl.ConsultarOperarioPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.negocio.fachada.operario.ConsultarOperarioPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarOperarioPorIdFachadaImpl implements ConsultarOperarioPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarOperarioPorIdCasoUso casoUso;

	public ConsultarOperarioPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarOperarioPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public OperarioDTO ejecutar(final OperarioDTO datos) {
		try {
			var dominio = new OperarioDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var tipoDocumentoIdentificacionDTO = resultado.getTipoDocumentoIdentificacion() != null
					? new TipoDocumentoIdentificacionDTO.Builder()
							.id(resultado.getTipoDocumentoIdentificacion().getId())
							.build()
					: null;
			var cargoDTO = resultado.getCargo() != null
					? new CargoDTO.Builder().id(resultado.getCargo().getId()).build()
					: null;
			var parqueaderoDTO = resultado.getParqueadero() != null
					? new ParqueaderoDTO.Builder().id(resultado.getParqueadero().getId()).build()
					: null;
			return new OperarioDTO.Builder()
					.id(resultado.getId())
					.tipoDocumentoIdentificacion(tipoDocumentoIdentificacionDTO)
					.numeroIdentificacion(resultado.getNumeroIdentificacion())
					.primerNombre(resultado.getPrimerNombre())
					.segundoNombre(resultado.getSegundoNombre())
					.primerApellido(resultado.getPrimerApellido())
					.segundoApellido(resultado.getSegundoApellido())
					.numeroTelefonico(resultado.getNumeroTelefonico())
					.cargo(cargoDTO)
					.parqueadero(parqueaderoDTO)
					.build();
		} catch (ERZParkingExcepcion excepcion) {
			throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al consultar el operario", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}