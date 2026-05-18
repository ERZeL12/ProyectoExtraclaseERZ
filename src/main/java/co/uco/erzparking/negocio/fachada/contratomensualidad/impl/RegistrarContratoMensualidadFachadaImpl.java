package co.uco.erzparking.negocio.fachada.contratomensualidad.impl;

import java.util.Calendar;
import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.negocio.casouso.contratomensualidad.RegistrarContratoMensualidadCasoUso;
import co.uco.erzparking.negocio.casouso.contratomensualidad.impl.RegistrarContratoMensualidadCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ContratoMensualidadDominio;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.fachada.contratomensualidad.RegistrarContratoMensualidadFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarContratoMensualidadFachadaImpl implements RegistrarContratoMensualidadFachada {

	private DAOFactory daoFactory;
	private RegistrarContratoMensualidadCasoUso casoUso;

	public RegistrarContratoMensualidadFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarContratoMensualidadCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ContratoMensualidadDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new ContratoMensualidadDominio.Builder()
					.id(datos.getId())
					.fechaInicioContrato(datos.getFechaInicioContrato())
					.fechaFinContrato(datos.getFechaFinContrato())
					.tarifa(datos.getTarifa() != null ? new TarifaDominio.Builder()
							.id(datos.getTarifa().getId())
							.build() : null)
					.usuarioVehiculo(datos.getUsuarioVehiculo() != null ? new UsuarioVehiculoDominio.Builder()
							.id(datos.getUsuarioVehiculo().getId())
							.build() : null)
					.espacioFisico(datos.getEspacioFisico() != null ? new EspacioFisicoDominio.Builder()
							.id(datos.getEspacioFisico().getId())
							.build() : null)
					.build();
			casoUso.ejecutar(dominio);
			daoFactory.confirmarTransaccion();
		} catch (ERZParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	public static void main(final String[] args) {
		try {
			var inicio = new java.util.Date();
			var cal = Calendar.getInstance();
			cal.setTime(inicio);
			cal.add(Calendar.MONTH, 1);
			var fin = cal.getTime();

			var dto = new ContratoMensualidadDTO.Builder()
					.fechaInicioContrato(inicio)
					.fechaFinContrato(fin)
					.tarifa(new TarifaDTO.Builder()
							.id(UUID.fromString("2addbabd-1197-48f7-9bec-4b967b51be9c"))
							.build())
					.usuarioVehiculo(new UsuarioVehiculoDTO.Builder()
							.id(UUID.fromString("994b5b9b-d1f7-4a85-9659-73ae07c355a3"))
							.build())
					.espacioFisico(new EspacioFisicoDTO.Builder()
							.id(UUID.fromString("046a876f-ff0a-4e78-9147-78fef43b9669"))
							.build())
					.build();
			new RegistrarContratoMensualidadFachadaImpl().ejecutar(dto);
			System.out.println("ContratoMensualidad registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
