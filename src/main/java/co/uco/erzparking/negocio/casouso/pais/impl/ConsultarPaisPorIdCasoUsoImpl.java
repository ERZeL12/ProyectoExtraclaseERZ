package co.uco.erzparking.negocio.casouso.pais.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.casouso.pais.ConsultarPaisPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.PaisDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarPaisPorIdCasoUsoImpl implements ConsultarPaisPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarPaisPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public PaisDominio ejecutar(final PaisDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final PaisDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del pais son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del pais es obligatorio para consultar");
		}
	}

	private PaisDominio consultar(final PaisDominio datos) {
		var entidad = daoFactory.getPaisDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("El pais no existe en el sistema");
		}
		return new PaisDominio.Builder()
				.id(entidad.getId())
				.nombre(entidad.getNombre())
				.build();
	}
}
