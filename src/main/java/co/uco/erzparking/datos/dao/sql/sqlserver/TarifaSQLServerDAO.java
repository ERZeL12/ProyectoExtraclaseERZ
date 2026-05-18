package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.TarifaDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.entidad.TarifaEntidad;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.transversal.UtilFecha;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class TarifaSQLServerDAO extends SQLDAO implements TarifaDAO {

	public TarifaSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final TarifaEntidad entidad) {
		final String sql = "INSERT INTO Tarifa (id, valorServicio, fechaInicioVigenciaTarifa, fechaFinVigenciaTarifa, tipoVehiculo_id, servicio_id, estadoActual) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setDouble(2, entidad.getValorServicio());
			ps.setDate(3, new Date(entidad.getFechaInicioVigenciaTarifa().getTime()));
			if (UtilFecha.esSentinela(entidad.getFechaFinVigenciaTarifa())) {
				ps.setNull(4, java.sql.Types.DATE);
			} else {
				ps.setDate(4, new Date(entidad.getFechaFinVigenciaTarifa().getTime()));
			}
			ps.setString(5, entidad.getTipoVehiculo().getId().toString());
			ps.setString(6, entidad.getServicio().getId().toString());
			ps.setBoolean(7, entidad.isEstadoActual());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al crear la tarifa", "Error al crear la tarifa");
		}
	}

	@Override
	public void cambiarEstadoActual(final UUID id, final boolean nuevoEstado) {
		final String sql = "UPDATE Tarifa SET estadoActual = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setBoolean(1, nuevoEstado);
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al cambiar el estado de la tarifa", "SQLException al cambiar estadoActual en tabla Tarifa: " + e.getMessage());
		}
	}

	@Override
	public TarifaEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT t.id, t.valorServicio, t.fechaInicioVigenciaTarifa, t.fechaFinVigenciaTarifa, t.estadoActual, "
				+ "tv.id as tipoVehiculo_id, tv.nombreVehiculo, s.id as servicio_id, s.nombreServicio "
				+ "FROM Tarifa t "
				+ "INNER JOIN TipoVehiculo tv ON t.tipoVehiculo_id = tv.id "
				+ "INNER JOIN Servicio s ON t.servicio_id = s.id "
				+ "WHERE t.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirTarifaEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar la tarifa por id", "Error al consultar la tarifa por id");
		}
		return null;
	}

	@Override
	public List<TarifaEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<TarifaEntidad> consultarPorFiltro(final TarifaEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT t.id, t.valorServicio, t.fechaInicioVigenciaTarifa, t.fechaFinVigenciaTarifa, t.estadoActual, "
				+ "tv.id as tipoVehiculo_id, tv.nombreVehiculo, s.id as servicio_id, s.nombreServicio "
				+ "FROM Tarifa t "
				+ "INNER JOIN TipoVehiculo tv ON t.tipoVehiculo_id = tv.id "
				+ "INNER JOIN Servicio s ON t.servicio_id = s.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilObjeto.esNulo(filtro.getTipoVehiculo()) && !UtilObjeto.esNulo(filtro.getTipoVehiculo().getId())) {
				sql.append(" AND t.tipoVehiculo_id = ?");
				parametros.add(filtro.getTipoVehiculo().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getServicio()) && !UtilObjeto.esNulo(filtro.getServicio().getId())) {
				sql.append(" AND t.servicio_id = ?");
				parametros.add(filtro.getServicio().getId().toString());
			}
		}

		final List<TarifaEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirTarifaEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar tarifas", "Error al consultar tarifas");
		}
		return resultados;
	}

	// Consulta tarifa vigente para tipo de vehiculo y servicio en fecha actual
	public TarifaEntidad consultarTarifaVigente(final UUID tipoVehiculoId, final UUID servicioId) {
		final String sql = "SELECT t.id, t.valorServicio, t.fechaInicioVigenciaTarifa, t.fechaFinVigenciaTarifa, t.estadoActual, "
				+ "tv.id as tipoVehiculo_id, tv.nombreVehiculo, s.id as servicio_id, s.nombreServicio "
				+ "FROM Tarifa t "
				+ "INNER JOIN TipoVehiculo tv ON t.tipoVehiculo_id = tv.id "
				+ "INNER JOIN Servicio s ON t.servicio_id = s.id "
				+ "WHERE t.tipoVehiculo_id = ? AND t.servicio_id = ? "
				+ "AND CAST(GETDATE() AS DATE) >= t.fechaInicioVigenciaTarifa "
				+ "AND (t.fechaFinVigenciaTarifa IS NULL OR CAST(GETDATE() AS DATE) <= t.fechaFinVigenciaTarifa)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, tipoVehiculoId.toString());
			ps.setString(2, servicioId.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirTarifaEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar la tarifa vigente", "Error al consultar la tarifa vigente");
		}
		return null;
	}

	@Override
	public void actualizar(final UUID id, final TarifaEntidad entidad) {
	    final String sql = "UPDATE Tarifa SET fechaFinVigenciaTarifa = ? WHERE id = ?";
	    try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
	        ps.setDate(1, new Date(entidad.getFechaFinVigenciaTarifa().getTime()));
	        ps.setString(2, id.toString());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        throw ERZParkingExcepcion.crear(e, "Error al finalizar la vigencia de la tarifa", "SQLException al actualizar tabla Tarifa: " + e.getMessage());
	    }
	}
	
	private TarifaEntidad construirTarifaEntidad(final ResultSet rs) throws SQLException {
		var tipoVehiculo = new TipoVehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("tipoVehiculo_id")))
				.nombreVehiculo(rs.getString("nombreVehiculo"))
				.build();
		var servicio = new ServicioEntidad.Builder()
				.id(UUID.fromString(rs.getString("servicio_id")))
				.nombreServicio(rs.getString("nombreServicio"))
				.build();
		return new TarifaEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.valorServicio(rs.getDouble("valorServicio"))
				.fechaInicioVigenciaTarifa(rs.getDate("fechaInicioVigenciaTarifa"))
				.fechaFinVigenciaTarifa(rs.getDate("fechaFinVigenciaTarifa"))
				.tipoVehiculo(tipoVehiculo)
				.servicio(servicio)
				.estadoActual(rs.getBoolean("estadoActual"))
				.build();
	}

}
