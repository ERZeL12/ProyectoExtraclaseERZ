package co.uco.erzparking.negocio.fachada.entrada.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EntradaDTO;
import co.uco.erzparking.negocio.assembler.dto.impl.EntradaDTOAssembler;
import co.uco.erzparking.negocio.casouso.entrada.ConsultarEntradaPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.entrada.impl.ConsultarEntradaPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.fachada.entrada.ConsultarEntradaPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarEntradaPorIdFachadaImpl implements ConsultarEntradaPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarEntradaPorIdCasoUso casoUso;

	public ConsultarEntradaPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarEntradaPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public EntradaDTO ejecutar(final EntradaDTO datos) {
		try {
			var dominio = EntradaDTOAssembler.getInstance().ensamblarDominio(datos);
			var resultado = casoUso.ejecutar(dominio);
			return EntradaDTOAssembler.getInstance().ensamblarDTO(resultado);
		} catch (ERZParkingExcepcion excepcion) {
			throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
