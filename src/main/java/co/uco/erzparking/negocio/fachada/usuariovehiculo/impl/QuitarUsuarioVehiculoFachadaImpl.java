package co.uco.erzparking.negocio.fachada.usuariovehiculo.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.QuitarUsuarioVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.impl.QuitarUsuarioVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.QuitarUsuarioVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarUsuarioVehiculoFachadaImpl implements QuitarUsuarioVehiculoFachada {

	private DAOFactory daoFactory;
	private QuitarUsuarioVehiculoCasoUso casoUso;

	public QuitarUsuarioVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarUsuarioVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final UsuarioVehiculoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new UsuarioVehiculoDominio.Builder().id(datos.getId()).build();
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
			var dto = new UsuarioVehiculoDTO.Builder()
					.id(UUID.fromString("994b5b9b-d1f7-4a85-9659-73ae07c355a3"))
					.build();
			new QuitarUsuarioVehiculoFachadaImpl().ejecutar(dto);
			System.out.println("UsuarioVehiculo eliminado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
