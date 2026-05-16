package co.uco.erzparking.negocio.fachada.contratomensualidad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
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
			var espacioFisicoDTO = resultado.getEspacioFisico() != null
					? new EspacioFisicoDTO.Builder().id(resultado.getEspacioFisico().getId()).build()
					: null;
			var usuarioVehiculoDTO = resultado.getUsuarioVehiculo() != null
					? new UsuarioVehiculoDTO.Builder().id(resultado.getUsuarioVehiculo().getId()).build()
					: null;
				return new ContratoMensualidadDTO.Builder()
					.id(resultado.getId())
					.fechaInicioContrato(resultado.getFechaInicioContrato())
					.fechaFinContrato(resultado.getFechaFinContrato())
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

}
