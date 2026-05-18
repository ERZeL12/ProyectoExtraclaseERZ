package co.uco.erzparking.negocio.fachada.ciudad.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.negocio.casouso.ciudad.RegistrarCiudadCasoUso;
import co.uco.erzparking.negocio.casouso.ciudad.impl.RegistrarCiudadCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CiudadDominio;
import co.uco.erzparking.negocio.fachada.ciudad.RegistrarCiudadFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarCiudadFachadaImpl implements RegistrarCiudadFachada {

	private DAOFactory daoFactory;
	private RegistrarCiudadCasoUso casoUso;

	public RegistrarCiudadFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarCiudadCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final CiudadDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new CiudadDominio.Builder()
					.id(datos.getId())
					.nombre(datos.getNombre())
					.departamento(datos.getDepartamento() != null ? new co.uco.erzparking.negocio.dominio.DepartamentoDominio.Builder().id(datos.getDepartamento().getId()).build() : null)
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
			var dto = new co.uco.erzparking.dto.CiudadDTO.Builder()
					.nombre("Marinilla")
					.departamento(new co.uco.erzparking.dto.DepartamentoDTO.Builder()
							.id(java.util.UUID.fromString("1f8a1ad3-3c61-4b6b-8ebd-e109aee6beb9"))
							.build())
					.build();
			new RegistrarCiudadFachadaImpl().ejecutar(dto);
			System.out.println("Ciudad registrada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
