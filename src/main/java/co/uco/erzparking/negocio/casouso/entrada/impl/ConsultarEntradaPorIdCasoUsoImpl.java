package co.uco.erzparking.negocio.casouso.entrada.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.assembler.entidad.impl.EntradaEntidadAssembler;
import co.uco.erzparking.negocio.casouso.entrada.ConsultarEntradaPorIdCasoUso;
import co.uco.erzparking.negocio.dominio.EntradaDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarEntradaPorIdCasoUsoImpl implements ConsultarEntradaPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarEntradaPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public EntradaDominio ejecutar(final EntradaDominio datos) {
		validarIntegridadDatos(datos);
		return consultar(datos);
	}

	private void validarIntegridadDatos(final EntradaDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos de la entrada son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador de la entrada es obligatorio para consultar");
		}
	}

	private EntradaDominio consultar(final EntradaDominio datos) {
		var entidad = daoFactory.getEntradaDAO().consultarPorId(datos.getId());
		if (UtilObjeto.esNulo(entidad)) {
			throw ERZParkingExcepcion.crear("La entrada no existe en el sistema");
		}
		return EntradaEntidadAssembler.getInstance().ensamblarDominio(entidad);
	}

}
