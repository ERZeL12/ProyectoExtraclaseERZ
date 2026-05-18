package co.uco.erzparking.negocio.fachada.pais.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.fachada.pais.QuitarPaisFachada;
import java.util.UUID;
import co.uco.erzparking.negocio.casouso.pais.QuitarPaisCasoUso;
import co.uco.erzparking.negocio.casouso.pais.impl.QuitarPaisCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarPaisFachadaImpl implements QuitarPaisFachada {

	private DAOFactory daoFactory;
	private QuitarPaisCasoUso casoUso;

	public QuitarPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final PaisDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new PaisDominio.Builder().id(datos.getId()).build();
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
	                .id(UUID.fromString("c23b1beb-2227-46f5-8dd6-2947d063f1de"))
	                .build();
	        QuitarPaisFachada fachada = new QuitarPaisFachadaImpl();
	        fachada.ejecutar(pais);
	        System.out.println("Pais eliminado exitosamente.");
	    } catch (Exception excepcion) {
	        System.err.println("Error: " + excepcion.getMessage());
	        excepcion.printStackTrace();
	    }
	}

}
