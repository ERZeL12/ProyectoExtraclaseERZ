package co.uco.erzparking.negocio.fachada.departamento.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.negocio.casouso.departamento.ActualizarDepartamentoCasoUso;
import co.uco.erzparking.negocio.casouso.departamento.impl.ActualizarDepartamentoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.fachada.departamento.ActualizarDepartamentoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ActualizarDepartamentoFachadaImpl implements ActualizarDepartamentoFachada {

	private DAOFactory daoFactory;
	private ActualizarDepartamentoCasoUso casoUso;

	public ActualizarDepartamentoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ActualizarDepartamentoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final DepartamentoDTO datos) {
		try {

			var dominio = new DepartamentoDominio.Builder()
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

}
