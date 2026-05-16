package co.uco.erzparking.negocio.casouso.metodopago.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.MetodoPagoEntidad;
import co.uco.erzparking.negocio.casouso.metodopago.ActualizarMetodoPagoCasoUso;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class ActualizarMetodoPagoCasoUsoImpl implements ActualizarMetodoPagoCasoUso {

	private DAOFactory daoFactory;

	public ActualizarMetodoPagoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final MetodoPagoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		actualizar(datos);
	}

	private void validarIntegridadDatos(final MetodoPagoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del metodo de pago son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del metodo de pago es obligatorio para actualizar");
		}
		if (UtilObjeto.esNulo(datos.getNombreMetodoPago()) || datos.getNombreMetodoPago().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del metodo de pago es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getMetodoPagoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El metodo de pago que intenta actualizar no existe en el sistema");
		}
	}

	private void actualizar(final MetodoPagoDominio datos) {
		var entidad = new MetodoPagoEntidad.Builder()
				.id(datos.getId())
				.nombreMetodoPago(datos.getNombreMetodoPago())
				.descripcion(datos.getDescripcion())
				.build();
		daoFactory.getMetodoPagoDAO().actualizar(datos.getId(), entidad);
	}
}
