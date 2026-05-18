package co.uco.erzparking.negocio.casouso.operario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EntradaEntidad;
import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.negocio.casouso.operario.QuitarOperarioCasoUso;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarOperarioCasoUsoImpl implements QuitarOperarioCasoUso {

	private DAOFactory daoFactory;

	public QuitarOperarioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final OperarioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final OperarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del operario son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del operario es obligatorio para quitar");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getOperarioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El operario no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var operario = new OperarioEntidad.Builder().id(id).build();
		var entradas = daoFactory.getEntradaDAO().consultarPorFiltro(new EntradaEntidad.Builder().operario(operario).build());
		if (!entradas.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el operario porque tiene entradas registradas");
		}
	}

	private void quitar(final OperarioDominio datos) {
		daoFactory.getOperarioDAO().eliminar(datos.getId());
	}

}