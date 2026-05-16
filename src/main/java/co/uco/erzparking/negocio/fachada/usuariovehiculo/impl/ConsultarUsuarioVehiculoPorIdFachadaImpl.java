package co.uco.erzparking.negocio.fachada.usuariovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.ConsultarUsuarioVehiculoPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.impl.ConsultarUsuarioVehiculoPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.ConsultarUsuarioVehiculoPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarUsuarioVehiculoPorIdFachadaImpl implements ConsultarUsuarioVehiculoPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarUsuarioVehiculoPorIdCasoUso casoUso;

	public ConsultarUsuarioVehiculoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarUsuarioVehiculoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public UsuarioVehiculoDTO ejecutar(final UsuarioVehiculoDTO datos) {
		try {

			var dominio = new UsuarioVehiculoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var usuarioDTO = resultado.getUsuario() != null
					? new UsuarioDTO.Builder().id(resultado.getUsuario().getId()).build()
					: null;
			var vehiculoDTO = resultado.getVehiculo() != null
					? new VehiculoDTO.Builder().id(resultado.getVehiculo().getId()).build()
					: null;
				return new UsuarioVehiculoDTO.Builder()
					.id(resultado.getId())
					.usuario(usuarioDTO)
					.vehiculo(vehiculoDTO)
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
