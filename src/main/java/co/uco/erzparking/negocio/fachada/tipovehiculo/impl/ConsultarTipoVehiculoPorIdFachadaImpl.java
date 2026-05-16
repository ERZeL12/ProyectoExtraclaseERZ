package co.uco.erzparking.negocio.fachada.tipovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.casouso.tipovehiculo.ConsultarTipoVehiculoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.tipovehiculo.impl.ConsultarTipoVehiculoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoVehiculoDominio;
import co.uco.erzparking.negocio.fachada.tipovehiculo.ConsultarTipoVehiculoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTipoVehiculoPorIdFachadaImpl implements ConsultarTipoVehiculoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarTipoVehiculoPorIdCasoUso casoUso;

	public ConsultarTipoVehiculoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTipoVehiculoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public TipoVehiculoDTO ejecutar(final TipoVehiculoDTO datos) {
		try {

			var dominio = new TipoVehiculoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return new TipoVehiculoDTO.Builder()
					.id(resultado.getId())
					.nombreVehiculo(resultado.getNombreVehiculo())
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
