package co.uco.erzparking.negocio.casouso.metodopago.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.metodopago.ConsultarMetodoPagoPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarMetodoPagoPorIdCasoUsoImpl implements ConsultarMetodoPagoPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarMetodoPagoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public MetodoPagoDominio ejecutar(final MetodoPagoDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final MetodoPagoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del metodoPago son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del metodoPago es obligatorio para consultar");
		}
	}

	private MetodoPagoDominio consultar(final MetodoPagoDominio datos) {
		var entidad = daoFactory.getMetodoPagoDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El metodoPago no existe en el sistema");
		}
		return new MetodoPagoDominio.Builder()
				.id(entidad.getId())
				.nombreMetodoPago(entidad.getNombreMetodoPago())
				.descripcion(entidad.getDescripcion())
				.build();
	}
}
