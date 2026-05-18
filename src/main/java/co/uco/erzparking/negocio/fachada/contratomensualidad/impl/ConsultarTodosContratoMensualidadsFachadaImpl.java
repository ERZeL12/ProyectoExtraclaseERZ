package co.uco.erzparking.negocio.fachada.contratomensualidad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.dto.VehiculoDTO;
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
		var tarifaDTO = d.getTarifa() != null
				? new TarifaDTO.Builder()
						.id(d.getTarifa().getId())
						.valorServicio(d.getTarifa().getValorServicio())
						.build()
				: null;
		var espacioFisicoDTO = d.getEspacioFisico() != null
				? new EspacioFisicoDTO.Builder()
						.id(d.getEspacioFisico().getId())
						.numeroEspacioFisico(d.getEspacioFisico().getNumeroEspacioFisico())
						.build()
				: null;
		UsuarioVehiculoDTO usuarioVehiculoDTO = null;
		if (d.getUsuarioVehiculo() != null) {
			var usuarioDTO = d.getUsuarioVehiculo().getUsuario() != null
					? new UsuarioDTO.Builder()
							.id(d.getUsuarioVehiculo().getUsuario().getId())
							.primerNombre(d.getUsuarioVehiculo().getUsuario().getPrimerNombre())
							.primerApellido(d.getUsuarioVehiculo().getUsuario().getPrimerApellido())
							.build()
					: null;
			var vehiculoDTO = d.getUsuarioVehiculo().getVehiculo() != null
					? new VehiculoDTO.Builder()
							.id(d.getUsuarioVehiculo().getVehiculo().getId())
							.placaVehiculo(d.getUsuarioVehiculo().getVehiculo().getPlacaVehiculo())
							.build()
					: null;
			usuarioVehiculoDTO = new UsuarioVehiculoDTO.Builder()
					.id(d.getUsuarioVehiculo().getId())
					.usuario(usuarioDTO)
					.vehiculo(vehiculoDTO)
					.build();
		}
		return new ContratoMensualidadDTO.Builder()
				.id(d.getId())
				.fechaInicioContrato(d.getFechaInicioContrato())
				.fechaFinContrato(d.getFechaFinContrato())
				.tarifa(tarifaDTO)
				.espacioFisico(espacioFisicoDTO)
				.usuarioVehiculo(usuarioVehiculoDTO)
				.build();
	}

	public static void main(final String[] args) {
		try {
			var filtro = new ContratoMensualidadDTO.Builder().build();
			var resultado = new ConsultarTodosContratoMensualidadsFachadaImpl().ejecutar(filtro);
			System.out.println("Total contratos mensualidad encontrados: " + resultado.size());
			resultado.forEach(c -> System.out.println(" - " + c.getId()
					+ " | inicio=" + c.getFechaInicioContrato()
					+ " | fin=" + c.getFechaFinContrato()));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
