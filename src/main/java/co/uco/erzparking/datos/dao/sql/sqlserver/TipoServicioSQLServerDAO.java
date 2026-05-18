package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.TipoServicioDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.TipoServicioEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class TipoServicioSQLServerDAO extends SQLDAO implements TipoServicioDAO {

	public TipoServicioSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final TipoServicioEntidad entidad) {
	    final String sql = "INSERT INTO TipoServicio (id, nombreServicio, descripcion) VALUES (?, ?, ?)";
	    try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
	        ps.setString(1, entidad.getId().toString());
	        ps.setString(2, entidad.getNombreServicio());
	        ps.setString(3, entidad.getDescripcion());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        throw ERZParkingExcepcion.crear(e, "Error al crear el tipo de servicio", "SQLException al insertar en tabla TipoServicio: " + e.getMessage());
	    }
	}

	@Override
	public void actualizar(final UUID id, final TipoServicioEntidad entidad) {
		final String sql = "UPDATE TipoServicio SET nombreServicio = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombreServicio());
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el tipo de servicio", "Error al actualizar el tipo de servicio");
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM TipoServicio WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el tipo de servicio", "Error al eliminar el tipo de servicio");
		}
	}

	@Override
	public TipoServicioEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT id, nombreServicio, descripcion FROM TipoServicio WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirTipoServicioEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el tipo de servicio por id", "Error al consultar el tipo de servicio por id");
		}
		return null;
	}

	@Override
	public List<TipoServicioEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<TipoServicioEntidad> consultarPorFiltro(final TipoServicioEntidad filtro) {
		final StringBuilder sql = new StringBuilder("SELECT id, nombreServicio, descripcion FROM TipoServicio WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro) && !UtilTexto.esNula(filtro.getNombreServicio()) && !filtro.getNombreServicio().isEmpty()) {
			sql.append(" AND nombreServicio LIKE ?");
			parametros.add("%" + filtro.getNombreServicio() + "%");
		}

		final List<TipoServicioEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirTipoServicioEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar tipos de servicio por filtro", "Error al consultar tipos de servicio por filtro");
		}
		return resultados;
	}

	private TipoServicioEntidad construirTipoServicioEntidad(final ResultSet rs) throws SQLException {
		return new TipoServicioEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombreServicio(rs.getString("nombreServicio"))
				.descripcion(rs.getString("descripcion"))
				.build();
	}

}
