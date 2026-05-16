package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.DepartamentoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.DepartamentoEntidad;
import co.uco.erzparking.entidad.PaisEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class DepartamentoSQLServerDAO extends SQLDAO implements DepartamentoDAO {

	public DepartamentoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final DepartamentoEntidad entidad) {
		validarDependenciaPais(entidad);
		final String sql = "INSERT INTO Departamento (id, nombre, pais_id) VALUES (?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombre());
			ps.setString(3, entidad.getPais().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al registrar el departamento", "SQLException al insertar en tabla Departamento: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(final UUID id, final DepartamentoEntidad entidad) {
		final String sql = "UPDATE Departamento SET nombre = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombre());
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el departamento", "SQLException al actualizar tabla Departamento: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM Departamento WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el departamento", "SQLException al eliminar de tabla Departamento: " + e.getMessage());
		}
	}

	@Override
	public DepartamentoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT d.id, d.nombre, p.id as pais_id, p.nombre as pais_nombre FROM Departamento d INNER JOIN Pais p ON d.pais_id = p.id WHERE d.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirDepartamentoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el departamento", "SQLException al consultar tabla Departamento por id: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<DepartamentoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<DepartamentoEntidad> consultarPorFiltro(final DepartamentoEntidad filtro) {
		final StringBuilder sql = new StringBuilder("SELECT d.id, d.nombre, p.id as pais_id, p.nombre as pais_nombre FROM Departamento d INNER JOIN Pais p ON d.pais_id = p.id WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro) && !UtilTexto.esNula(filtro.getNombre()) && !filtro.getNombre().isEmpty()) {
			sql.append(" AND d.nombre LIKE ?");
			parametros.add("%" + filtro.getNombre() + "%");
		}

		final List<DepartamentoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirDepartamentoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar los departamentos", "SQLException al consultar tabla Departamento por filtro: " + e.getMessage());
		}
		return resultados;
	}

	private void validarDependenciaPais(final DepartamentoEntidad entidad) {
		final String sql = "SELECT id FROM Pais WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getPais().getId().toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					throw ERZParkingExcepcion.crear("No se puede registrar el departamento porque el pais asociado no existe en el sistema", "Pais con id " + entidad.getPais().getId() + " no encontrado");
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al validar el pais asociado al departamento", "SQLException al validar dependencia Pais: " + e.getMessage());
		}
	}

	private DepartamentoEntidad construirDepartamentoEntidad(final ResultSet rs) throws SQLException {
		var pais = new PaisEntidad.Builder()
				.id(UUID.fromString(rs.getString("pais_id")))
				.nombre(rs.getString("pais_nombre"))
				.build();
		return new DepartamentoEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombre(rs.getString("nombre"))
				.pais(pais)
				.build();
	}

}
