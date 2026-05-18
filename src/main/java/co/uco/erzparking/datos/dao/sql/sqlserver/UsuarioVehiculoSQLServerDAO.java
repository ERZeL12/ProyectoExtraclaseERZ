package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.UsuarioVehiculoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class UsuarioVehiculoSQLServerDAO extends SQLDAO implements UsuarioVehiculoDAO {

	public UsuarioVehiculoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final UsuarioVehiculoEntidad entidad) {
		final String sql = "INSERT INTO UsuarioVehiculo (id, usuario_id, vehiculo_id) VALUES (?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getUsuario().getId().toString());
			ps.setString(3, entidad.getVehiculo().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al crear la asociacion usuario vehiculo", "Error al crear la asociacion usuario vehiculo");
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM UsuarioVehiculo WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar la asociacion usuario vehiculo", "Error al eliminar la asociacion usuario vehiculo");
		}
	}

	@Override
	public UsuarioVehiculoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT uv.id, "
				+ "u.id as usuario_id, u.primerNombre, u.primerApellido, u.numeroIdentificacion, "
				+ "v.id as vehiculo_id, v.placaVehiculo "
				+ "FROM UsuarioVehiculo uv "
				+ "INNER JOIN Usuario u ON uv.usuario_id = u.id "
				+ "INNER JOIN Vehiculo v ON uv.vehiculo_id = v.id "
				+ "WHERE uv.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirUsuarioVehiculoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar la asociacion usuario vehiculo por id", "Error al consultar la asociacion usuario vehiculo por id");
		}
		return null;
	}

	@Override
	public List<UsuarioVehiculoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<UsuarioVehiculoEntidad> consultarPorFiltro(final UsuarioVehiculoEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT uv.id, "
				+ "u.id as usuario_id, u.primerNombre, u.primerApellido, u.numeroIdentificacion, "
				+ "v.id as vehiculo_id, v.placaVehiculo "
				+ "FROM UsuarioVehiculo uv "
				+ "INNER JOIN Usuario u ON uv.usuario_id = u.id "
				+ "INNER JOIN Vehiculo v ON uv.vehiculo_id = v.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilObjeto.esNulo(filtro.getVehiculo()) && !UtilObjeto.esNulo(filtro.getVehiculo().getId())) {
				sql.append(" AND uv.vehiculo_id = ?");
				parametros.add(filtro.getVehiculo().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getUsuario()) && !UtilObjeto.esNulo(filtro.getUsuario().getId())) {
				sql.append(" AND uv.usuario_id = ?");
				parametros.add(filtro.getUsuario().getId().toString());
			}
		}

		final List<UsuarioVehiculoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirUsuarioVehiculoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar asociaciones usuario vehiculo por filtro", "Error al consultar asociaciones usuario vehiculo por filtro");
		}
		return resultados;
	}

	// Consulta si un vehiculo tiene un usuario dueno registrado
	public UsuarioVehiculoEntidad consultarPorVehiculo(final UUID vehiculoId) {
		final String sql = "SELECT uv.id, "
				+ "u.id as usuario_id, u.primerNombre, u.primerApellido, u.numeroIdentificacion, "
				+ "v.id as vehiculo_id, v.placaVehiculo "
				+ "FROM UsuarioVehiculo uv "
				+ "INNER JOIN Usuario u ON uv.usuario_id = u.id "
				+ "INNER JOIN Vehiculo v ON uv.vehiculo_id = v.id "
				+ "WHERE uv.vehiculo_id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, vehiculoId.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirUsuarioVehiculoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar la asociacion por vehiculo", "Error al consultar la asociacion por vehiculo");
		}
		return null;
	}

	private UsuarioVehiculoEntidad construirUsuarioVehiculoEntidad(final ResultSet rs) throws SQLException {
		var usuario = new UsuarioEntidad.Builder()
				.id(UUID.fromString(rs.getString("usuario_id")))
				.primerNombre(rs.getString("primerNombre"))
				.primerApellido(rs.getString("primerApellido"))
				.numeroIdentificacion(rs.getString("numeroIdentificacion"))
				.build();
		var vehiculo = new VehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("vehiculo_id")))
				.placaVehiculo(rs.getString("placaVehiculo"))
				.build();
		return new UsuarioVehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.usuario(usuario)
				.vehiculo(vehiculo)
				.build();
	}

}
