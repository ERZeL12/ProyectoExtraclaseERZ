package co.uco.erzparking.negocio.fachada.operario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.negocio.casouso.operario.ActualizarOperarioCasoUso;
import co.uco.erzparking.negocio.casouso.operario.impl.ActualizarOperarioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.negocio.fachada.operario.ActualizarOperarioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarOperarioFachadaImpl implements ActualizarOperarioFachada {

	private DAOFactory daoFactory;
	private ActualizarOperarioCasoUso casoUso;

	public ActualizarOperarioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarOperarioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final OperarioDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var cargo = datos.getCargo() != null
					? new CargoDominio.Builder().id(datos.getCargo().getId()).build()
					: null;
			var dominio = new OperarioDominio.Builder()
					.id(datos.getId())
					.primerNombre(datos.getPrimerNombre())
					.segundoNombre(datos.getSegundoNombre())
					.primerApellido(datos.getPrimerApellido())
					.segundoApellido(datos.getSegundoApellido())
					.numeroTelefonico(datos.getNumeroTelefonico())
					.cargo(cargo)
					.build();
			casoUso.ejecutar(dominio);
			daoFactory.confirmarTransaccion();
		} catch (ERZParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al actualizar el operario", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	public static void main(final String[] args) {
		try {
			var dto = new OperarioDTO.Builder()
					.id(UUID.fromString("4318b80c-b391-490c-a49e-e94dc3efd7c0"))
					.primerNombre("Pedro")
					.segundoNombre("Andres")
					.primerApellido("Ramirez")
					.segundoApellido("Lopez")
					.numeroTelefonico(3017654321L)
					.cargo(new CargoDTO.Builder()
							.id(UUID.fromString("19108fe6-14ef-4e90-8baa-5a607def5b3d"))
							.build())
					.build();
			new ActualizarOperarioFachadaImpl().ejecutar(dto);
			System.out.println("Operario actualizado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}