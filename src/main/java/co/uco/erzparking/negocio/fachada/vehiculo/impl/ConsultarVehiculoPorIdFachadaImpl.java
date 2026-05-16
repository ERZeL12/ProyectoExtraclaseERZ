package co.uco.erzparking.negocio.fachada.vehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.vehiculo.ConsultarVehiculoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.vehiculo.impl.ConsultarVehiculoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.VehiculoDominio;
import co.uco.erzparking.negocio.fachada.vehiculo.ConsultarVehiculoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarVehiculoPorIdFachadaImpl implements ConsultarVehiculoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarVehiculoPorIdCasoUso casoUso;

	public ConsultarVehiculoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarVehiculoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public VehiculoDTO ejecutar(final VehiculoDTO datos) {
		try {

			var dominio = new VehiculoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var tipoVehiculoDTO = resultado.getTipoVehiculo() != null
					? new TipoVehiculoDTO.Builder()
							.id(resultado.getTipoVehiculo().getId())
							.nombreVehiculo(resultado.getTipoVehiculo().getNombreVehiculo())
							.build()
					: null;
				return new VehiculoDTO.Builder()
					.id(resultado.getId())
					.placaVehiculo(resultado.getPlacaVehiculo())
					.tipoVehiculo(tipoVehiculoDTO)
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
