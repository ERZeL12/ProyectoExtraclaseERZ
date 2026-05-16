package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.EstadoEspacioFisicoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class EstadoEspacioFisicoSQLServerDAO extends SQLDAO implements EstadoEspacioFisicoDAO {

	public EstadoEspacioFisicoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final EstadoEspacioFisicoEntidad entidad) {
		final String sql = "INSERT INTO EstadoEspacioFisico (id, nombreEstadoEspacioFisico) VALUES (NEWID(), ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombreEstadoEspacioFisico());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al crear el estado de espacio fisico", "Error al crear el estado de espacio fisico");
		}
	}

	@Override
	public void actualizar(final UUID id, final EstadoEspacioFisicoEntidad entidad) {
		final String sql = "UPDATE EstadoEspacioFisico SET nombreEstadoEspacioFisico = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombreEstadoEspacioFisico());
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el estado de espacio fisico", "Error al actualizar el estado de espacio fisico");
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM EstadoEspacioFisico WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el estado de espacio fisico", "Error al eliminar el estado de espacio fisico");
		}
	}

	@Override
	public EstadoEspacioFisicoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT id, nombreEstadoEspacioFisico FROM EstadoEspacioFisico WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirEstadoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el estado de espacio fisico por id", "Error al consultar el estado de espacio fisico por id");
		}
		return null;
	}

	@Override
	public List<EstadoEspacioFisicoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<EstadoEspacioFisicoEntidad> consultarPorFiltro(final EstadoEspacioFisicoEntidad filtro) {
		final StringBuilder sql = new StringBuilder("SELECT id, nombreEstadoEspacioFisico FROM EstadoEspacioFisico WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro) && !UtilTexto.esNula(filtro.getNombreEstadoEspacioFisico()) && !filtro.getNombreEstadoEspacioFisico().isEmpty()) {
			sql.append(" AND nombreEstadoEspacioFisico = ?");
			parametros.add(filtro.getNombreEstadoEspacioFisico());
		}

		final List<EstadoEspacioFisicoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirEstadoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar estados de espacio fisico por filtro", "Error al consultar estados de espacio fisico por filtro");
		}
		return resultados;
	}

	private EstadoEspacioFisicoEntidad construirEstadoEntidad(final ResultSet rs) throws SQLException {
		return new EstadoEspacioFisicoEntidad.Builder()
				.nombreEstadoEspacioFisico(rs.getString("nombreEstadoEspacioFisico"))
				.build();
	}

}
