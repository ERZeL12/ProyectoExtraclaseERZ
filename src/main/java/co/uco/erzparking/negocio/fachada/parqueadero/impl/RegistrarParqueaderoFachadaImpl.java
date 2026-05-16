package co.uco.erzparking.negocio.fachada.parqueadero.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.parqueadero.RegistrarParqueaderoCasoUso;
import co.uco.erzparking.negocio.casouso.parqueadero.impl.RegistrarParqueaderoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.parqueadero.RegistrarParqueaderoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarParqueaderoFachadaImpl implements RegistrarParqueaderoFachada {

	private DAOFactory daoFactory;
	private RegistrarParqueaderoCasoUso casoUso;

	public RegistrarParqueaderoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarParqueaderoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ParqueaderoDTO datos) {
		try {

			var dominio = new ParqueaderoDominio.Builder()
					.id(datos.getId())
					.nombreEstablecimiento(datos.getNombreEstablecimiento())
					.numeroTelefonico(datos.getNumeroTelefonico())
					.correoElectronico(datos.getCorreoElectronico())
					.direccionEstablecimiento(datos.getDireccionEstablecimiento())
					.ciudad(datos.getCiudad() != null ? new co.uco.erzparking.negocio.dominio.CiudadDominio.Builder().id(datos.getCiudad().getId()).build() : null)
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
			var dto = new co.uco.erzparking.dto.ParqueaderoDTO.Builder()
					.nombreEstablecimiento("Parqueadero ERZ")
					.numeroTelefonico(3009876543L)
					.correoElectronico("erz@parqueadero.com")
					.direccionEstablecimiento("Calle 10 # 5-20")
					.ciudad(new co.uco.erzparking.dto.CiudadDTO.Builder()
							.id(java.util.UUID.fromString("434e52b1-924a-4fd6-9a08-3b13d3362e72"))
							.build())
					.build();
			new RegistrarParqueaderoFachadaImpl().ejecutar(dto);
			System.out.println("Parqueadero registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}