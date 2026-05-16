package co.uco.erzparking.negocio.casouso.tiposervicio.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.tiposervicio.QuitarTipoServicioCasoUso;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarTipoServicioCasoUsoImpl implements QuitarTipoServicioCasoUso {

	private DAOFactory daoFactory;

	public QuitarTipoServicioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final TipoServicioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final TipoServicioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del tipoServicio son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del tipoServicio es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getTipoServicioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El tipoServicio no existe en el sistema");
		}
	}

	private void quitar(final TipoServicioDominio datos) {
		daoFactory.getTipoServicioDAO().eliminar(datos.getId());
	}
}
