package co.uco.erzparking.negocio.casouso.metodopago.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.MetodoPagoEntidad;
import co.uco.erzparking.negocio.casouso.metodopago.RegistrarMetodoPagoCasoUso;
import co.uco.erzparking.negocio.dominio.MetodoPagoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.UUID;

public class RegistrarMetodoPagoCasoUsoImpl implements RegistrarMetodoPagoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarMetodoPagoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final MetodoPagoDominio datos) {
		validarIntegridadDatos(datos);
		guardar(datos);
	}

	private void validarIntegridadDatos(final MetodoPagoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del metodo de pago son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getNombreMetodoPago()) || datos.getNombreMetodoPago().isEmpty()) {
			throw ERZParkingExcepcion.crear("El nombre del metodo de pago es obligatorio");
		}
	}

	private UUID generarIdUnico() {
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(daoFactory.getMetodoPagoDAO().consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	private void guardar(final MetodoPagoDominio datos) {
		var entidad = new MetodoPagoEntidad.Builder()
				.id(generarIdUnico())
				.nombreMetodoPago(datos.getNombreMetodoPago())
				.descripcion(datos.getDescripcion())
				.build();
		daoFactory.getMetodoPagoDAO().crear(entidad);
	}
}
