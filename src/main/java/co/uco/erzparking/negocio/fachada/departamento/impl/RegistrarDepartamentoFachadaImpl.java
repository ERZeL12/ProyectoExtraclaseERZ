package co.uco.erzparking.negocio.fachada.departamento.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.departamento.RegistrarDepartamentoCasoUso;
import co.uco.erzparking.negocio.casouso.departamento.impl.RegistrarDepartamentoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.negocio.fachada.departamento.RegistrarDepartamentoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarDepartamentoFachadaImpl implements RegistrarDepartamentoFachada {

	private DAOFactory daoFactory;
	private RegistrarDepartamentoCasoUso casoUso;

	public RegistrarDepartamentoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarDepartamentoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final DepartamentoDTO datos) {
	    try {
	        daoFactory.iniciarTransaccion();
	        var dominio = new DepartamentoDominio.Builder()
	                .id(datos.getId())
	                .nombre(datos.getNombre())
	                .pais(datos.getPais() != null ? new PaisDominio.Builder()
	                        .id(datos.getPais().getId())
	                        .build() : null)
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
	        var dto = new DepartamentoDTO.Builder()
	                .nombre("Antioquia")
	                .pais(new PaisDTO.Builder()
	                        .id(UUID.fromString("27c48f27-9bb0-455b-b76d-7abd081fd1c3"))
	                        .build())
	                .build();
	        new RegistrarDepartamentoFachadaImpl().ejecutar(dto);
	        System.out.println("Departamento registrado exitosamente.");
	    } catch (Exception e) {
	        System.err.println("Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
