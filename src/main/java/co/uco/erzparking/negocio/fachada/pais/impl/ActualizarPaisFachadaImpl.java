package co.uco.erzparking.negocio.fachada.pais.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.pais.ActualizarPaisCasoUso;
import co.uco.erzparking.negocio.casouso.pais.impl.ActualizarPaisCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.negocio.fachada.pais.ActualizarPaisFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarPaisFachadaImpl implements ActualizarPaisFachada {

	private DAOFactory daoFactory;
	private ActualizarPaisCasoUso casoUso;

	public ActualizarPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final PaisDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new PaisDominio.Builder()
					.id(datos.getId())
					.nombre(datos.getNombre())
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
			var dto = new PaisDTO.Builder()
					.id(UUID.fromString("27c48f27-9bb0-455b-b76d-7abd081fd1c3"))
					.nombre("Colombia Actualizado")
					.build();
			new ActualizarPaisFachadaImpl().ejecutar(dto);
			System.out.println("Pais actualizado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
