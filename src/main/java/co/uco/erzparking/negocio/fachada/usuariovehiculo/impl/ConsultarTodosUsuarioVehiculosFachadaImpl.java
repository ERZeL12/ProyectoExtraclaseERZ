package co.uco.erzparking.negocio.fachada.usuariovehiculo.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.ConsultarTodosUsuarioVehiculosCasoUso;
import co.uco.erzparking.negocio.casouso.usuariovehiculo.impl.ConsultarTodosUsuarioVehiculosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.UsuarioVehiculoDominio;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.ConsultarTodosUsuarioVehiculosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;

public class ConsultarTodosUsuarioVehiculosFachadaImpl implements ConsultarTodosUsuarioVehiculosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosUsuarioVehiculosCasoUso casoUso;

	public ConsultarTodosUsuarioVehiculosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosUsuarioVehiculosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<UsuarioVehiculoDTO> ejecutar(final UsuarioVehiculoDTO datos) {
		try {

			var dominio = new UsuarioVehiculoDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return resultado.stream().map(this::mapearADto).collect(java.util.stream.Collectors.toList());
		} catch (ERZParkingExcepcion excepcion) {
						throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	private UsuarioVehiculoDTO mapearADto(final UsuarioVehiculoDominio d) {
		var usuarioDTO = d.getUsuario() != null
				? new UsuarioDTO.Builder().id(d.getUsuario().getId()).build()
				: null;
		var vehiculoDTO = d.getVehiculo() != null
				? new VehiculoDTO.Builder().id(d.getVehiculo().getId()).build()
				: null;
		return new UsuarioVehiculoDTO.Builder()
				.id(d.getId())
				.usuario(usuarioDTO)
				.vehiculo(vehiculoDTO)
				.build();
	}

}
