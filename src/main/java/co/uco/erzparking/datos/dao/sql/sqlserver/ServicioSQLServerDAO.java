package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.ServicioDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.entidad.TipoServicioEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ServicioSQLServerDAO extends SQLDAO implements ServicioDAO {

	public ServicioSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final ServicioEntidad entidad) {
		final String sql = "INSERT INTO Servicio (id, nombreServicio, tipoServicio_id, parqueadero_id, estadoActual) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombreServicio());
			ps.setString(3, entidad.getTipoServicio().getId().toString());
			ps.setString(4, entidad.getParqueadero().getId().toString());
			ps.setBoolean(5, entidad.isEstadoActual());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al crear el servicio", "Error al crear el servicio");
		}
	}

	@Override
	public void actualizar(final UUID id, final ServicioEntidad entidad) {
		final String sql = "UPDATE Servicio SET nombreServicio = ?, estadoActual = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombreServicio());
			ps.setBoolean(2, entidad.isEstadoActual());
			ps.setString(3, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el servicio", "Error al actualizar el servicio");
		}
	}

	@Override
	public void cambiarEstadoActual(final UUID id, final boolean nuevoEstado) {
		final String sql = "UPDATE Servicio SET estadoActual = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setBoolean(1, nuevoEstado);
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al cambiar el estado del servicio", "SQLException al cambiar estadoActual en tabla Servicio: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM Servicio WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el servicio", "Error al eliminar el servicio");
		}
	}

	@Override
	public ServicioEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT s.id, s.nombreServicio, s.estadoActual, "
				+ "ts.id as tipoServicio_id, ts.nombreServicio as nombreTipoServicio, "
				+ "p.id as parqueadero_id, p.nombreEstablecimiento as parqueadero_nombre, p.direccionEstablecimiento as parqueadero_direccion "
				+ "FROM Servicio s "
				+ "INNER JOIN TipoServicio ts ON s.tipoServicio_id = ts.id "
				+ "INNER JOIN Parqueadero p ON s.parqueadero_id = p.id "
				+ "WHERE s.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirServicioEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el servicio por id", "Error al consultar el servicio por id");
		}
		return null;
	}

	@Override
	public List<ServicioEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<ServicioEntidad> consultarPorFiltro(final ServicioEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT s.id, s.nombreServicio, s.estadoActual, "
				+ "ts.id as tipoServicio_id, ts.nombreServicio as nombreTipoServicio, "
				+ "p.id as parqueadero_id, p.nombreEstablecimiento as parqueadero_nombre, p.direccionEstablecimiento as parqueadero_direccion "
				+ "FROM Servicio s "
				+ "INNER JOIN TipoServicio ts ON s.tipoServicio_id = ts.id "
				+ "INNER JOIN Parqueadero p ON s.parqueadero_id = p.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilTexto.esNula(filtro.getNombreServicio()) && !filtro.getNombreServicio().isEmpty()) {
				sql.append(" AND s.nombreServicio LIKE ?");
				parametros.add("%" + filtro.getNombreServicio() + "%");
			}
			if (!UtilObjeto.esNulo(filtro.getParqueadero()) && !UtilObjeto.esNulo(filtro.getParqueadero().getId())) {
				sql.append(" AND s.parqueadero_id = ?");
				parametros.add(filtro.getParqueadero().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getTipoServicio()) && !UtilObjeto.esNulo(filtro.getTipoServicio().getId())) {
				sql.append(" AND s.tipoServicio_id = ?");
				parametros.add(filtro.getTipoServicio().getId().toString());
			}
		}

		final List<ServicioEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirServicioEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar servicios por filtro", "Error al consultar servicios por filtro");
		}
		return resultados;
	}

	private ServicioEntidad construirServicioEntidad(final ResultSet rs) throws SQLException {
		var tipoServicio = new TipoServicioEntidad.Builder()
				.id(UUID.fromString(rs.getString("tipoServicio_id")))
				.nombreServicio(rs.getString("nombreTipoServicio"))
				.build();
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(UUID.fromString(rs.getString("parqueadero_id")))
				.nombreEstablecimiento(rs.getString("parqueadero_nombre"))
				.direccionEstablecimiento(rs.getString("parqueadero_direccion"))
				.build();
		return new ServicioEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombreServicio(rs.getString("nombreServicio"))
				.tipoServicio(tipoServicio)
				.parqueadero(parqueadero)
				.estadoActual(rs.getBoolean("estadoActual"))
				.build();
	}

}
