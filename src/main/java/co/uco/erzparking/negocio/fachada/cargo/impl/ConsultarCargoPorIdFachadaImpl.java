package co.uco.erzparking.negocio.fachada.cargo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.cargo.ConsultarCargoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.cargo.impl.ConsultarCargoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.fachada.cargo.ConsultarCargoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarCargoPorIdFachadaImpl implements ConsultarCargoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarCargoPorIdCasoUso casoUso;

	public ConsultarCargoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarCargoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public CargoDTO ejecutar(final CargoDTO datos) {
		try {

			var dominio = new CargoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var parqueaderoDTO = new ParqueaderoDTO.Builder()
					.id(resultado.getParqueadero().getId())
					.nombreEstablecimiento(resultado.getParqueadero().getNombreEstablecimiento())
					.build();
			return new CargoDTO.Builder()
					.id(resultado.getId())
					.nombreCargo(resultado.getNombreCargo())
					.descripcion(resultado.getDescripcion())
					.parqueadero(parqueaderoDTO)
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
