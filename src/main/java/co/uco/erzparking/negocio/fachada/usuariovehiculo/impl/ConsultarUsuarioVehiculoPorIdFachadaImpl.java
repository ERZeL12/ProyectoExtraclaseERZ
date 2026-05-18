package co.uco.erzparking.negocio.fachada.usuariovehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.ConsultarUsuarioVehiculoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.impl.ConsultarUsuarioVehiculoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.ConsultarUsuarioVehiculoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarUsuarioVehiculoPorIdFachadaImpl implements ConsultarUsuarioVehiculoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarUsuarioVehiculoPorIdCasoUso casoUso;

	public ConsultarUsuarioVehiculoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarUsuarioVehiculoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public UsuarioVehiculoDTO ejecutar(final UsuarioVehiculoDTO datos) {
		try {

			var dominio = new UsuarioVehiculoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var usuarioDTO = resultado.getUsuario() != null
					? new UsuarioDTO.Builder()
							.id(resultado.getUsuario().getId())
							.primerNombre(resultado.getUsuario().getPrimerNombre())
							.primerApellido(resultado.getUsuario().getPrimerApellido())
							.numeroIdentificacion(resultado.getUsuario().getNumeroIdentificacion())
							.build()
					: null;
			var vehiculoDTO = resultado.getVehiculo() != null
					? new VehiculoDTO.Builder()
							.id(resultado.getVehiculo().getId())
							.placaVehiculo(resultado.getVehiculo().getPlacaVehiculo())
							.build()
					: null;
				return new UsuarioVehiculoDTO.Builder()
					.id(resultado.getId())
					.usuario(usuarioDTO)
					.vehiculo(vehiculoDTO)
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
			var filtro = new UsuarioVehiculoDTO.Builder()
					.id(UUID.fromString("994b5b9b-d1f7-4a85-9659-73ae07c355a3"))
					.build();
			var resultado = new ConsultarUsuarioVehiculoPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("UsuarioVehiculo consultado: id=" + resultado.getId()
					+ ", usuarioId=" + (resultado.getUsuario() != null ? resultado.getUsuario().getId() : "(sin usuario)")
					+ ", vehiculoId=" + (resultado.getVehiculo() != null ? resultado.getVehiculo().getId() : "(sin vehiculo)"));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
