package co.uco.erzparking.negocio.casouso.servicio.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.EntradaEntidad;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.entidad.TarifaEntidad;
import co.uco.erzparking.negocio.casouso.servicio.QuitarServicioCasoUso;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarServicioCasoUsoImpl implements QuitarServicioCasoUso {

	private DAOFactory daoFactory;

	public QuitarServicioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final ServicioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final ServicioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del servicio son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del servicio es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getServicioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El servicio no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var servicio = new ServicioEntidad.Builder().id(id).build();
		var tarifas = daoFactory.getTarifaDAO().consultarPorFiltro(new TarifaEntidad.Builder().servicio(servicio).build());
		if (!tarifas.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el servicio porque tiene tarifas asociadas");
		}
		var entradas = daoFactory.getEntradaDAO().consultarPorFiltro(new EntradaEntidad.Builder().servicio(servicio).build());
		if (!entradas.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el servicio porque tiene entradas registradas");
		}
	}

	private void quitar(final ServicioDominio datos) {
		daoFactory.getServicioDAO().eliminar(datos.getId());
	}
}
