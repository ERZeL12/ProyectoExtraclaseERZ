package co.uco.erzparking.negocio.casouso.espaciofisico.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.entidad.ContratoMensualidadEntidad;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.negocio.casouso.espaciofisico.QuitarEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarEspacioFisicoCasoUsoImpl implements QuitarEspacioFisicoCasoUso {

	private DAOFactory daoFactory;

	public QuitarEspacioFisicoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final EspacioFisicoDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		validarSinDependencias(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final EspacioFisicoDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del espacioFisico son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del espacioFisico es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getEspacioFisicoDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El espacioFisico no existe en el sistema");
		}
	}

	private void validarSinDependencias(final UUID id) {
		var espacio = new EspacioFisicoEntidad.Builder().id(id).build();
		var contratos = daoFactory.getContratoMensualidadDAO().consultarPorFiltro(new ContratoMensualidadEntidad.Builder().espacioFisico(espacio).build());
		if (!contratos.isEmpty()) {
			throw ERZParkingExcepcion.crear("No se puede eliminar el espacio fisico porque tiene contratos de mensualidad asociados");
		}
	}

	private void quitar(final EspacioFisicoDominio datos) {
		daoFactory.getEspacioFisicoDAO().eliminar(datos.getId());
	}
}
