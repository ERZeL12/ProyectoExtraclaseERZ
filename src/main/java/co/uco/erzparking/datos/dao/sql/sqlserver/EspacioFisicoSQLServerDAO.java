package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.EspacioFisicoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.TipoServicioEntidad;
import co.uco.erzparking.entidad.ZonaParqueaderoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class EspacioFisicoSQLServerDAO extends SQLDAO implements EspacioFisicoDAO {

	public EspacioFisicoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final EspacioFisicoEntidad entidad) {
		final String sql = "INSERT INTO EspacioFisico (id, numeroEspacioFisico, tipoServicio_id, estadoEspacioFisico_id, zonaEspacioFisico_id, parqueadero_id) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNumeroEspacioFisico());
			ps.setString(3, entidad.getTipoServicio().getId().toString());
			ps.setString(4, entidad.getEstadoEspacioFisico().getId().toString());
			ps.setString(5, entidad.getZonaEspacioFisico().getId().toString());
			ps.setString(6, entidad.getParqueadero().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
		    System.err.println("SQL Error: " + e.getMessage());
		    System.err.println("SQL State: " + e.getSQLState());
		    System.err.println("Error Code: " + e.getErrorCode());
		    throw ERZParkingExcepcion.crear(e, "Error al crear el espacio fisico", "SQLException: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(final UUID id, final EspacioFisicoEntidad entidad) {
		final String sql = "UPDATE EspacioFisico SET estadoEspacioFisico_id = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getEstadoEspacioFisico().getId().toString());
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el espacio fisico", "Error al actualizar el espacio fisico");
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM EspacioFisico WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el espacio fisico", "Error al eliminar el espacio fisico");
		}
	}

	@Override
	public EspacioFisicoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT ef.id, ef.numeroEspacioFisico, "
				+ "ts.id as tipoServicio_id, ts.nombreServicio, "
				+ "ee.id as estadoEspacioFisico_id, ee.nombreEstadoEspacioFisico, "
				+ "z.id as zona_id, z.nombreZona, "
				+ "p.id as parqueadero_id, p.nombreEstablecimiento as parqueadero_nombre "
				+ "FROM EspacioFisico ef "
				+ "INNER JOIN TipoServicio ts ON ef.tipoServicio_id = ts.id "
				+ "INNER JOIN EstadoEspacioFisico ee ON ef.estadoEspacioFisico_id = ee.id "
				+ "INNER JOIN ZonaParqueadero z ON ef.zonaEspacioFisico_id = z.id "
				+ "INNER JOIN Parqueadero p ON ef.parqueadero_id = p.id "
				+ "WHERE ef.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirEspacioFisicoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el espacio fisico por id", "Error al consultar el espacio fisico por id");
		}
		return null;
	}

	@Override
	public List<EspacioFisicoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<EspacioFisicoEntidad> consultarPorFiltro(final EspacioFisicoEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT ef.id, ef.numeroEspacioFisico, "
				+ "ts.id as tipoServicio_id, ts.nombreServicio, "
				+ "ee.id as estadoEspacioFisico_id, ee.nombreEstadoEspacioFisico, "
				+ "z.id as zona_id, z.nombreZona, "
				+ "p.id as parqueadero_id, p.nombreEstablecimiento as parqueadero_nombre "
				+ "FROM EspacioFisico ef "
				+ "INNER JOIN TipoServicio ts ON ef.tipoServicio_id = ts.id "
				+ "INNER JOIN EstadoEspacioFisico ee ON ef.estadoEspacioFisico_id = ee.id "
				+ "INNER JOIN ZonaParqueadero z ON ef.zonaEspacioFisico_id = z.id "
				+ "INNER JOIN Parqueadero p ON ef.parqueadero_id = p.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilObjeto.esNulo(filtro.getEstadoEspacioFisico())
					&& !UtilObjeto.esNulo(filtro.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())
					&& !filtro.getEstadoEspacioFisico().getNombreEstadoEspacioFisico().isEmpty()) {
				sql.append(" AND ee.nombreEstadoEspacioFisico = ?");
				parametros.add(filtro.getEstadoEspacioFisico().getNombreEstadoEspacioFisico());
			}
			if (!UtilObjeto.esNulo(filtro.getEstadoEspacioFisico())
					&& !UtilObjeto.esNulo(filtro.getEstadoEspacioFisico().getId())) {
				sql.append(" AND ef.estadoEspacioFisico_id = ?");
				parametros.add(filtro.getEstadoEspacioFisico().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getTipoServicio()) && !UtilObjeto.esNulo(filtro.getTipoServicio().getId())) {
				sql.append(" AND ef.tipoServicio_id = ?");
				parametros.add(filtro.getTipoServicio().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getZonaEspacioFisico()) && !UtilObjeto.esNulo(filtro.getZonaEspacioFisico().getId())) {
				sql.append(" AND ef.zonaEspacioFisico_id = ?");
				parametros.add(filtro.getZonaEspacioFisico().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getParqueadero()) && !UtilObjeto.esNulo(filtro.getParqueadero().getId())) {
				sql.append(" AND ef.parqueadero_id = ?");
				parametros.add(filtro.getParqueadero().getId().toString());
			}
		}

		final List<EspacioFisicoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirEspacioFisicoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar espacios fisicos por filtro", "Error al consultar espacios fisicos por filtro");
		}
		return resultados;
	}

	// Consulta espacios disponibles para un tipo de servicio excluyendo los que tienen contrato activo
	public List<EspacioFisicoEntidad> consultarDisponiblesPorTipoServicio(final UUID tipoServicioId) {
		final String sql = "SELECT ef.id, ef.numeroEspacioFisico, "
				+ "ts.id as tipoServicio_id, ts.nombreServicio, "
				+ "ee.id as estadoEspacioFisico_id, ee.nombreEstadoEspacioFisico, "
				+ "z.id as zona_id, z.nombreZona, "
				+ "p.id as parqueadero_id, p.nombreEstablecimiento as parqueadero_nombre "
				+ "FROM EspacioFisico ef "
				+ "INNER JOIN TipoServicio ts ON ef.tipoServicio_id = ts.id "
				+ "INNER JOIN EstadoEspacioFisico ee ON ef.estadoEspacioFisico_id = ee.id "
				+ "INNER JOIN ZonaParqueadero z ON ef.zonaEspacioFisico_id = z.id "
				+ "INNER JOIN Parqueadero p ON ef.parqueadero_id = p.id "
				+ "WHERE ef.tipoServicio_id = ? "
				+ "AND ee.nombreEstadoEspacioFisico = 'DISPONIBLE' "
				+ "AND ef.id NOT IN ("
				+ "  SELECT espacioFisico_id FROM ContratoMensualidad "
				+ "  WHERE CAST(GETDATE() AS DATE) BETWEEN fechaInicioContrato AND fechaFinContrato"
				+ ")";
		final List<EspacioFisicoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, tipoServicioId.toString());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirEspacioFisicoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar espacios disponibles por tipo de servicio", "Error al consultar espacios disponibles por tipo de servicio");
		}
		return resultados;
	}

	private EspacioFisicoEntidad construirEspacioFisicoEntidad(final ResultSet rs) throws SQLException {
		var tipoServicio = new TipoServicioEntidad.Builder()
				.id(UUID.fromString(rs.getString("tipoServicio_id")))
				.nombreServicio(rs.getString("nombreServicio"))
				.build();
		var estadoEspacioFisico = new EstadoEspacioFisicoEntidad.Builder()
				.id(UUID.fromString(rs.getString("estadoEspacioFisico_id")))
				.nombreEstadoEspacioFisico(rs.getString("nombreEstadoEspacioFisico"))
				.build();
		var zona = new ZonaParqueaderoEntidad.Builder()
				.id(UUID.fromString(rs.getString("zona_id")))
				.nombreZona(rs.getString("nombreZona"))
				.build();
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(UUID.fromString(rs.getString("parqueadero_id")))
				.nombreEstablecimiento(rs.getString("parqueadero_nombre"))
				.build();
		return new EspacioFisicoEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.numeroEspacioFisico(rs.getString("numeroEspacioFisico"))
				.tipoServicio(tipoServicio)
				.estadoEspacioFisico(estadoEspacioFisico)
				.zonaEspacioFisico(zona)
				.parqueadero(parqueadero)
				.build();
	}

}
