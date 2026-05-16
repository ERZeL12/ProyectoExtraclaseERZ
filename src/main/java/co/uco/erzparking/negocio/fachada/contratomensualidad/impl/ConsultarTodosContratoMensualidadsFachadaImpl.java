package co.uco.erzparking.negocio.fachada.contratomensualidad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.negocio.casouso.contratomensualidad.ConsultarTodosContratoMensualidadsCasoUso;
import co.uco.erzparking.negocio.casouso.contratomensualidad.impl.ConsultarTodosContratoMensualidadsCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.negocio.fachada.contratomensualidad.ConsultarTodosContratoMensualidadsFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosContratoMensualidadsFachadaImpl implements ConsultarTodosContratoMensualidadsFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosContratoMensualidadsCasoUso casoUso;

	public ConsultarTodosContratoMensualidadsFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosContratoMensualidadsCasoUsoImpl(daoFactory);
	}

	@Override
	public List<ContratoMensualidadDTO> ejecutar(final ContratoMensualidadDTO datos) {
		try {

			var dominio = new ContratoMensualidadDominio.Builder().id(datos.getId()).build();
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

	private ContratoMensualidadDTO mapearADto(final ContratoMensualidadDominio d) {
		var espacioFisicoDTO = d.getEspacioFisico() != null
				? new EspacioFisicoDTO.Builder().id(d.getEspacioFisico().getId()).build()
				: null;
		var usuarioVehiculoDTO = d.getUsuarioVehiculo() != null
				? new UsuarioVehiculoDTO.Builder().id(d.getUsuarioVehiculo().getId()).build()
				: null;
		return new ContratoMensualidadDTO.Builder()
				.id(d.getId())
				.fechaInicioContrato(d.getFechaInicioContrato())
				.fechaFinContrato(d.getFechaFinContrato())
				.espacioFisico(espacioFisicoDTO)
				.usuarioVehiculo(usuarioVehiculoDTO)
				.build();
	}

}
