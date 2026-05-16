package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.TipoVehiculoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class TipoVehiculoSQLServerDAO extends SQLDAO implements TipoVehiculoDAO {

	public TipoVehiculoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final TipoVehiculoEntidad entidad) {
		final String sql = "INSERT INTO TipoVehiculo (id, nombreVehiculo) VALUES (?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombreVehiculo());
			ps.executeUpdate();
		} catch (SQLException e) {
		    System.err.println("SQL Error: " + e.getMessage());
		    System.err.println("SQL State: " + e.getSQLState());
		    System.err.println("Error Code: " + e.getErrorCode());
		    throw ERZParkingExcepcion.crear(e, "Error al crear el tipo de vehiculo", "SQLException: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(final UUID id, final TipoVehiculoEntidad entidad) {
		final String sql = "UPDATE TipoVehiculo SET nombreVehiculo = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombreVehiculo());
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el tipo de vehiculo", "Error al actualizar el tipo de vehiculo");
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM TipoVehiculo WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el tipo de vehiculo", "Error al eliminar el tipo de vehiculo");
		}
	}

	@Override
	public TipoVehiculoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT id, nombreVehiculo FROM TipoVehiculo WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirTipoVehiculoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el tipo de vehiculo por id", "Error al consultar el tipo de vehiculo por id");
		}
		return null;
	}

	@Override
	public List<TipoVehiculoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<TipoVehiculoEntidad> consultarPorFiltro(final TipoVehiculoEntidad filtro) {
		final StringBuilder sql = new StringBuilder("SELECT id, nombreVehiculo FROM TipoVehiculo WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro) && !UtilTexto.esNula(filtro.getNombreVehiculo()) && !filtro.getNombreVehiculo().isEmpty()) {
			sql.append(" AND nombreVehiculo LIKE ?");
			parametros.add("%" + filtro.getNombreVehiculo() + "%");
		}

		final List<TipoVehiculoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirTipoVehiculoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar tipos de vehiculo por filtro", "Error al consultar tipos de vehiculo por filtro");
		}
		return resultados;
	}

	private TipoVehiculoEntidad construirTipoVehiculoEntidad(final ResultSet rs) throws SQLException {
		return new TipoVehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombreVehiculo(rs.getString("nombreVehiculo"))
				.build();
	}

}
