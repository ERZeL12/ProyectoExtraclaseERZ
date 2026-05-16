package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.VehiculoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class VehiculoSQLServerDAO extends SQLDAO implements VehiculoDAO {

	public VehiculoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final VehiculoEntidad entidad) {
		final String sql = "INSERT INTO Vehiculo (id, placaVehiculo, tipoVehiculo_id) VALUES (?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getPlacaVehiculo());
			ps.setString(3, entidad.getTipoVehiculo().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al crear el vehiculo", "Error al crear el vehiculo");
		}
	}

	@Override
	public VehiculoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT v.id, v.placaVehiculo, tv.id as tipoVehiculo_id, tv.nombreVehiculo "
				+ "FROM Vehiculo v "
				+ "INNER JOIN TipoVehiculo tv ON v.tipoVehiculo_id = tv.id "
				+ "WHERE v.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirVehiculoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el vehiculo por id", "Error al consultar el vehiculo por id");
		}
		return null;
	}

	@Override
	public List<VehiculoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<VehiculoEntidad> consultarPorFiltro(final VehiculoEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT v.id, v.placaVehiculo, tv.id as tipoVehiculo_id, tv.nombreVehiculo "
				+ "FROM Vehiculo v "
				+ "INNER JOIN TipoVehiculo tv ON v.tipoVehiculo_id = tv.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilTexto.esNula(filtro.getPlacaVehiculo()) && !filtro.getPlacaVehiculo().isEmpty()) {
				sql.append(" AND v.placaVehiculo = ?");
				parametros.add(filtro.getPlacaVehiculo());
			}
		}

		final List<VehiculoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirVehiculoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar vehiculos por filtro", "Error al consultar vehiculos por filtro");
		}
		return resultados;
	}

	public void actualizar(final UUID id, final VehiculoEntidad entidad) {
	    final String sql = "UPDATE Vehiculo SET placaVehiculo = ?, tipoVehiculo_id = ? WHERE id = ?";
	    try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
	        ps.setString(1, entidad.getPlacaVehiculo());
	        ps.setString(2, entidad.getTipoVehiculo().getId().toString());
	        ps.setString(3, id.toString());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        throw ERZParkingExcepcion.crear(e, "Error al actualizar el vehiculo", "SQLException al actualizar tabla Vehiculo: " + e.getMessage());
	    }
	}

	@Override
	public void eliminar(final UUID id) {
	    final String sql = "DELETE FROM Vehiculo WHERE id = ?";
	    try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
	        ps.setString(1, id.toString());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        throw ERZParkingExcepcion.crear(e, "Error al eliminar el vehiculo", "SQLException al eliminar de tabla Vehiculo: " + e.getMessage());
	    }
	}
	
	private VehiculoEntidad construirVehiculoEntidad(final ResultSet rs) throws SQLException {
		var tipoVehiculo = new TipoVehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("tipoVehiculo_id")))
				.nombreVehiculo(rs.getString("nombreVehiculo"))
				.build();
		return new VehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.placaVehiculo(rs.getString("placaVehiculo"))
				.tipoVehiculo(tipoVehiculo)
				.build();
	}

}
