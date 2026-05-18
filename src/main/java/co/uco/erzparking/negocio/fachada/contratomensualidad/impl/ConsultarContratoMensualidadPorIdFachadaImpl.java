package co.uco.erzparking.negocio.fachada.contratomensualidad.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.contratomensualidad.ConsultarContratoMensualidadPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.contratomensualidad.impl.ConsultarContratoMensualidadPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.negocio.fachada.contratomensualidad.ConsultarContratoMensualidadPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarContratoMensualidadPorIdFachadaImpl implements ConsultarContratoMensualidadPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarContratoMensualidadPorIdCasoUso casoUso;

	public ConsultarContratoMensualidadPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarContratoMensualidadPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public ContratoMensualidadDTO ejecutar(final ContratoMensualidadDTO datos) {
		try {

			var dominio = new ContratoMensualidadDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var tarifaDTO = resultado.getTarifa() != null
					? new TarifaDTO.Builder()
							.id(resultado.getTarifa().getId())
							.valorServicio(resultado.getTarifa().getValorServicio())
							.build()
					: null;
			var espacioFisicoDTO = resultado.getEspacioFisico() != null
					? new EspacioFisicoDTO.Builder()
							.id(resultado.getEspacioFisico().getId())
							.numeroEspacioFisico(resultado.getEspacioFisico().getNumeroEspacioFisico())
							.build()
					: null;
			UsuarioVehiculoDTO usuarioVehiculoDTO = null;
			if (resultado.getUsuarioVehiculo() != null) {
				var usuarioDTO = resultado.getUsuarioVehiculo().getUsuario() != null
						? new UsuarioDTO.Builder()
								.id(resultado.getUsuarioVehiculo().getUsuario().getId())
								.primerNombre(resultado.getUsuarioVehiculo().getUsuario().getPrimerNombre())
								.primerApellido(resultado.getUsuarioVehiculo().getUsuario().getPrimerApellido())
								.build()
						: null;
				var vehiculoDTO = resultado.getUsuarioVehiculo().getVehiculo() != null
						? new VehiculoDTO.Builder()
								.id(resultado.getUsuarioVehiculo().getVehiculo().getId())
								.placaVehiculo(resultado.getUsuarioVehiculo().getVehiculo().getPlacaVehiculo())
								.build()
						: null;
				usuarioVehiculoDTO = new UsuarioVehiculoDTO.Builder()
						.id(resultado.getUsuarioVehiculo().getId())
						.usuario(usuarioDTO)
						.vehiculo(vehiculoDTO)
						.build();
			}
				return new ContratoMensualidadDTO.Builder()
					.id(resultado.getId())
					.fechaInicioContrato(resultado.getFechaInicioContrato())
					.fechaFinContrato(resultado.getFechaFinContrato())
					.tarifa(tarifaDTO)
					.espacioFisico(espacioFisicoDTO)
					.usuarioVehiculo(usuarioVehiculoDTO)
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
			var filtro = new ContratoMensualidadDTO.Builder()
					.id(UUID.fromString("a40c7a23-0482-49e0-906c-7865b5d34e4e"))
					.build();
			var resultado = new ConsultarContratoMensualidadPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("ContratoMensualidad consultado: id=" + resultado.getId()
					+ ", inicio=" + resultado.getFechaInicioContrato()
					+ ", fin=" + resultado.getFechaFinContrato());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
