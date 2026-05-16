package co.uco.erzparking.negocio.casouso.tarifa.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.TarifaEntidad;
import co.uco.erzparking.negocio.casouso.tarifa.FinalizarVigenciaTarifaCasoUso;
import co.uco.erzparking.negocio.dominio.TarifaDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class FinalizarVigenciaTarifaCasoUsoImpl implements FinalizarVigenciaTarifaCasoUso {

	private DAOFactory daoFactory;

	public FinalizarVigenciaTarifaCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TarifaDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		finalizar(datos);
	}

	private void validarIntegridadDatos(final TarifaDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tarifa son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del tarifa es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getTarifaDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El tarifa no existe en el sistema");
		}
	}

	private void finalizar(final TarifaDominio datos) {
	    var entidad = new TarifaEntidad.Builder()
	            .id(datos.getId())
	            .fechaFinVigenciaTarifa(new java.util.Date())
	            .build();
	    daoFactory.getTarifaDAO().actualizar(datos.getId(), entidad);
	}
}
