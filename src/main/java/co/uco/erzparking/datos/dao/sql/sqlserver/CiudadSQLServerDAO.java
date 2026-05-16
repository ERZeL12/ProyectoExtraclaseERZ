package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.CiudadDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.entidad.DepartamentoEntidad;
import co.uco.erzparking.entidad.PaisEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class CiudadSQLServerDAO extends SQLDAO implements CiudadDAO {

	public CiudadSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final CiudadEntidad entidad) {
		validarDependenciaDepartamento(entidad);
		final String sql = "INSERT INTO Ciudad (id, nombre, departamento_id) VALUES (?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombre());
			ps.setString(3, entidad.getDepartamento().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al registrar la ciudad", "SQLException al insertar en tabla Ciudad: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(final UUID id, final CiudadEntidad entidad) {
		final String sql = "UPDATE Ciudad SET nombre = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombre());
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar la ciudad", "SQLException al actualizar tabla Ciudad: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM Ciudad WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar la ciudad", "SQLException al eliminar de tabla Ciudad: " + e.getMessage());
		}
	}

	@Override
	public CiudadEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT c.id, c.nombre, d.id as dep_id, d.nombre as dep_nombre, p.id as pais_id, p.nombre as pais_nombre "
				+ "FROM Ciudad c "
				+ "INNER JOIN Departamento d ON c.departamento_id = d.id "
				+ "INNER JOIN Pais p ON d.pais_id = p.id "
				+ "WHERE c.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirCiudadEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar la ciudad", "SQLException al consultar tabla Ciudad por id: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<CiudadEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<CiudadEntidad> consultarPorFiltro(final CiudadEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT c.id, c.nombre, d.id as dep_id, d.nombre as dep_nombre, p.id as pais_id, p.nombre as pais_nombre "
				+ "FROM Ciudad c "
				+ "INNER JOIN Departamento d ON c.departamento_id = d.id "
				+ "INNER JOIN Pais p ON d.pais_id = p.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro) && !UtilTexto.esNula(filtro.getNombre()) && !filtro.getNombre().isEmpty()) {
			sql.append(" AND c.nombre LIKE ?");
			parametros.add("%" + filtro.getNombre() + "%");
		}

		final List<CiudadEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirCiudadEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar las ciudades", "SQLException al consultar tabla Ciudad por filtro: " + e.getMessage());
		}
		return resultados;
	}

	private void validarDependenciaDepartamento(final CiudadEntidad entidad) {
		final String sql = "SELECT id FROM Departamento WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getDepartamento().getId().toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					throw ERZParkingExcepcion.crear("No se puede registrar la ciudad porque el departamento asociado no existe en el sistema", "Departamento con id " + entidad.getDepartamento().getId() + " no encontrado");
				}
			}
		} catch (ERZParkingExcepcion e) {
			throw e;
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al validar el departamento asociado a la ciudad", "SQLException al validar dependencia Departamento: " + e.getMessage());
		}
	}

	private CiudadEntidad construirCiudadEntidad(final ResultSet rs) throws SQLException {
		var pais = new PaisEntidad.Builder()
				.id(UUID.fromString(rs.getString("pais_id")))
				.nombre(rs.getString("pais_nombre"))
				.build();
		var departamento = new DepartamentoEntidad.Builder()
				.id(UUID.fromString(rs.getString("dep_id")))
				.nombre(rs.getString("dep_nombre"))
				.pais(pais)
				.build();
		return new CiudadEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombre(rs.getString("nombre"))
				.departamento(departamento)
				.build();
	}

}