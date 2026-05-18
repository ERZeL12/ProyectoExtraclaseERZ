package co.uco.erzparking.negocio.fachada.usuariovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.RegistrarUsuarioVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.impl.RegistrarUsuarioVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.RegistrarUsuarioVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarUsuarioVehiculoFachadaImpl implements RegistrarUsuarioVehiculoFachada {

	private DAOFactory daoFactory;
	private RegistrarUsuarioVehiculoCasoUso casoUso;

	public RegistrarUsuarioVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarUsuarioVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final UsuarioVehiculoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new UsuarioVehiculoDominio.Builder()
					.id(datos.getId())
					.usuario(datos.getUsuario() != null ? new co.uco.erzparking.negocio.dominio.UsuarioDominio.Builder().id(datos.getUsuario().getId()).build() : null)
					.vehiculo(datos.getVehiculo() != null ? new co.uco.erzparking.negocio.dominio.VehiculoDominio.Builder().id(datos.getVehiculo().getId()).build() : null)
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
			var dto = new co.uco.erzparking.dto.UsuarioVehiculoDTO.Builder()
					.usuario(new co.uco.erzparking.dto.UsuarioDTO.Builder()
							.id(java.util.UUID.fromString("5bc6e94a-de0a-4511-8142-f41a38a5fd01"))
							.build())
					.vehiculo(new co.uco.erzparking.dto.VehiculoDTO.Builder()
							.id(java.util.UUID.fromString("be6c0e80-dcea-4fb0-b443-3b0d728a08a9"))
							.build())
					.build();
			new RegistrarUsuarioVehiculoFachadaImpl().ejecutar(dto);
			System.out.println("UsuarioVehiculo registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
