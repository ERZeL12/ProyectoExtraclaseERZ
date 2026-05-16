package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.MetodoPagoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.MetodoPagoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class MetodoPagoSQLServerDAO extends SQLDAO implements MetodoPagoDAO {

	public MetodoPagoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final MetodoPagoEntidad entidad) {
		final String sql = "INSERT INTO MetodoPago (id, nombreMetodoPago, descripcion) VALUES (?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombreMetodoPago());
			ps.setString(3, entidad.getDescripcion());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al registrar el metodo de pago", "SQLException al insertar en tabla MetodoPago: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(final UUID id, final MetodoPagoEntidad entidad) {
		final String sql = "UPDATE MetodoPago SET nombreMetodoPago = ?, descripcion = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombreMetodoPago());
			ps.setString(2, entidad.getDescripcion());
			ps.setString(3, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el metodo de pago", "SQLException al actualizar tabla MetodoPago: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM MetodoPago WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el metodo de pago", "SQLException al eliminar de tabla MetodoPago: " + e.getMessage());
		}
	}

	@Override
	public MetodoPagoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT id, nombreMetodoPago, descripcion FROM MetodoPago WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el metodo de pago", "SQLException al consultar tabla MetodoPago: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<MetodoPagoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<MetodoPagoEntidad> consultarPorFiltro(final MetodoPagoEntidad filtro) {
		final StringBuilder sql = new StringBuilder("SELECT id, nombreMetodoPago, descripcion FROM MetodoPago WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro) && !UtilTexto.esNula(filtro.getNombreMetodoPago()) && !filtro.getNombreMetodoPago().isEmpty()) {
			sql.append(" AND nombreMetodoPago LIKE ?");
			parametros.add("%" + filtro.getNombreMetodoPago() + "%");
		}

		final List<MetodoPagoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar los metodos de pago", "SQLException al consultar tabla MetodoPago por filtro: " + e.getMessage());
		}
		return resultados;
	}

	private MetodoPagoEntidad construirEntidad(final ResultSet rs) throws SQLException {
		return new MetodoPagoEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombreMetodoPago(rs.getString("nombreMetodoPago"))
				.descripcion(rs.getString("descripcion"))
				.build();
	}

}
