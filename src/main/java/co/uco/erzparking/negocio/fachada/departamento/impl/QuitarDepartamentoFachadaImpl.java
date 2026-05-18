package co.uco.erzparking.negocio.fachada.departamento.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.negocio.casouso.departamento.QuitarDepartamentoCasoUso;
import co.uco.erzparking.negocio.casouso.departamento.impl.QuitarDepartamentoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.fachada.departamento.QuitarDepartamentoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarDepartamentoFachadaImpl implements QuitarDepartamentoFachada {

	private DAOFactory daoFactory;
	private QuitarDepartamentoCasoUso casoUso;

	public QuitarDepartamentoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new QuitarDepartamentoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final DepartamentoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new DepartamentoDominio.Builder().id(datos.getId()).build();
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
					.id(UUID.fromString("1f8a1ad3-3c61-4b6b-8ebd-e109aee6beb9"))
					.build();
			new QuitarDepartamentoFachadaImpl().ejecutar(dto);
			System.out.println("Departamento eliminado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
