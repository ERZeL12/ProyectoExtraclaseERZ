package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.PaisDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.PaisEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class PaisSQLServerDAO extends SQLDAO implements PaisDAO {

	public PaisSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final PaisEntidad entidad) {
		final String sql = "INSERT INTO Pais (id, nombre) VALUES (?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombre());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al registrar el pais", "SQLException al insertar en tabla Pais: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(final UUID id, final PaisEntidad entidad) {
		final String sql = "UPDATE Pais SET nombre = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombre());
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el pais", "SQLException al actualizar tabla Pais: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM Pais WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el pais", "SQLException al eliminar de tabla Pais: " + e.getMessage());
		}
	}

	@Override
	public PaisEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT id, nombre FROM Pais WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirPaisEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el pais", "SQLException al consultar tabla Pais por id: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<PaisEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<PaisEntidad> consultarPorFiltro(final PaisEntidad filtro) {
		final StringBuilder sql = new StringBuilder("SELECT id, nombre FROM Pais WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro) && !UtilTexto.esNula(filtro.getNombre()) && !filtro.getNombre().isEmpty()) {
			sql.append(" AND nombre LIKE ?");
			parametros.add("%" + filtro.getNombre() + "%");
		}

		final List<PaisEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirPaisEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar los paises", "SQLException al consultar tabla Pais por filtro: " + e.getMessage());
		}
		return resultados;
	}

	private PaisEntidad construirPaisEntidad(final ResultSet rs) throws SQLException {
		return new PaisEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombre(rs.getString("nombre"))
				.build();
	}

}
