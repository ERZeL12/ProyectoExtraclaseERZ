package co.uco.erzparking.negocio.fachada.pais.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.pais.RegistrarPaisCasoUso;
import co.uco.erzparking.negocio.casouso.pais.impl.RegistrarPaisCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.negocio.fachada.pais.RegistrarPaisFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarPaisFachadaImpl implements RegistrarPaisFachada {

	private DAOFactory daoFactory;
	private RegistrarPaisCasoUso casoUso;

	public RegistrarPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final PaisDTO datos) {
		try {

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
	        var pais = new PaisDTO.Builder()
	                .nombre("Colombia")
	                .build();
	        RegistrarPaisFachada fachada = new RegistrarPaisFachadaImpl();
	        fachada.ejecutar(pais);
	        System.out.println("Pais registrado exitosamente.");
	    } catch (Exception excepcion) {
	        System.err.println("Error: " + excepcion.getMessage());
	        excepcion.printStackTrace();
	    }
	}

}
