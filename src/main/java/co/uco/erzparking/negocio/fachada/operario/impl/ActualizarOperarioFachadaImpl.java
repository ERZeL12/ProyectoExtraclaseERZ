package co.uco.erzparking.negocio.fachada.operario.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
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

}