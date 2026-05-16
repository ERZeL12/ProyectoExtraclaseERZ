package co.uco.erzparking.negocio.fachada.departamento.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.casouso.departamento.ConsultarDepartamentoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.departamento.impl.ConsultarDepartamentoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.DepartamentoDominio;
import co.uco.erzparking.negocio.fachada.departamento.ConsultarDepartamentoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarDepartamentoPorIdFachadaImpl implements ConsultarDepartamentoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarDepartamentoPorIdCasoUso casoUso;

	public ConsultarDepartamentoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarDepartamentoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public DepartamentoDTO ejecutar(final DepartamentoDTO datos) {
		try {

			var dominio = new DepartamentoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var paisDTO = resultado.getPais() != null
					? new PaisDTO.Builder()
							.id(resultado.getPais().getId())
							.nombre(resultado.getPais().getNombre())
							.build()
					: null;
				return new DepartamentoDTO.Builder()
					.id(resultado.getId())
					.nombre(resultado.getNombre())
					.pais(paisDTO)
					.build();
		} catch (ERZParkingExcepcion excepcion) {
						throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

}
