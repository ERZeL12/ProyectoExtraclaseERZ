package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.CargoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class CargoSQLServerDAO extends SQLDAO implements CargoDAO {

	public CargoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final CargoEntidad entidad) {
		validarDependenciaParqueadero(entidad);
		final String sql = "INSERT INTO Cargo (id, nombreCargo, descripcion, parqueadero_id, estadoActual) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombreCargo());
			ps.setString(3, entidad.getDescripcion());
			ps.setString(4, entidad.getParqueadero().getId().toString());
			ps.setBoolean(5, entidad.isEstadoActual());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al registrar el cargo", "SQLException al insertar en tabla Cargo: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(final UUID id, final CargoEntidad entidad) {
		final String sql = "UPDATE Cargo SET nombreCargo = ?, descripcion = ?, estadoActual = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombreCargo());
			ps.setString(2, entidad.getDescripcion());
			ps.setBoolean(3, entidad.isEstadoActual());
			ps.setString(4, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el cargo", "SQLException al actualizar tabla Cargo: " + e.getMessage());
		}
	}

	@Override
	public void cambiarEstadoActual(final UUID id, final boolean nuevoEstado) {
		final String sql = "UPDATE Cargo SET estadoActual = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setBoolean(1, nuevoEstado);
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al cambiar el estado del cargo", "SQLException al cambiar estadoActual en tabla Cargo: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM Cargo WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el cargo", "SQLException al eliminar de tabla Cargo: " + e.getMessage());
		}
	}

	@Override
	public CargoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT c.id, c.nombreCargo, c.descripcion, c.estadoActual, p.id as parqueadero_id, p.nombreEstablecimiento as parqueadero_nombre "
				+ "FROM Cargo c INNER JOIN Parqueadero p ON c.parqueadero_id = p.id WHERE c.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirCargoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el cargo", "SQLException al consultar tabla Cargo por id: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<CargoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<CargoEntidad> consultarPorFiltro(final CargoEntidad filtro) {
		final StringBuilder sql = new StringBuilder("SELECT c.id, c.nombreCargo, c.descripcion, c.estadoActual, p.id as parqueadero_id, p.nombreEstablecimiento as parqueadero_nombre "
				+ "FROM Cargo c INNER JOIN Parqueadero p ON c.parqueadero_id = p.id WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilTexto.esNula(filtro.getNombreCargo()) && !filtro.getNombreCargo().isEmpty()) {
				sql.append(" AND c.nombreCargo LIKE ?");
				parametros.add("%" + filtro.getNombreCargo() + "%");
			}
			if (!UtilObjeto.esNulo(filtro.getParqueadero()) && !UtilObjeto.esNulo(filtro.getParqueadero().getId())) {
				sql.append(" AND c.parqueadero_id = ?");
				parametros.add(filtro.getParqueadero().getId().toString());
			}
		}

		final List<CargoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirCargoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar los cargos", "SQLException al consultar tabla Cargo por filtro: " + e.getMessage());
		}
		return resultados;
	}

	private void validarDependenciaParqueadero(final CargoEntidad entidad) {
		final String sql = "SELECT id FROM Parqueadero WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getParqueadero().getId().toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					throw ERZParkingExcepcion.crear("No se puede registrar el cargo porque el parqueadero asociado no existe en el sistema", "Parqueadero con id " + entidad.getParqueadero().getId() + " no encontrado");
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al validar el parqueadero asociado al cargo", "SQLException al validar dependencia Parqueadero: " + e.getMessage());
		}
	}

	private CargoEntidad construirCargoEntidad(final ResultSet rs) throws SQLException {
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(UUID.fromString(rs.getString("parqueadero_id")))
				.nombreEstablecimiento(rs.getString("parqueadero_nombre"))
				.build();
		return new CargoEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombreCargo(rs.getString("nombreCargo"))
				.descripcion(rs.getString("descripcion"))
				.parqueadero(parqueadero)
				.estadoActual(rs.getBoolean("estadoActual"))
				.build();
	}

}
