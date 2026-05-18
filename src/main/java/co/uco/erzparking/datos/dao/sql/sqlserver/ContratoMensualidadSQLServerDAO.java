package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.ContratoMensualidadDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.ContratoMensualidadEntidad;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.TarifaEntidad;
import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ContratoMensualidadSQLServerDAO extends SQLDAO implements ContratoMensualidadDAO {

	public ContratoMensualidadSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final ContratoMensualidadEntidad entidad) {
		final String sql = "INSERT INTO ContratoMensualidad (id, fechaInicioContrato, fechaFinContrato, tarifa_id, usuarioVehiculo_id, espacioFisico_id, estadoActual) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setDate(2, new Date(entidad.getFechaInicioContrato().getTime()));
			ps.setDate(3, new Date(entidad.getFechaFinContrato().getTime()));
			ps.setString(4, entidad.getTarifa().getId().toString());
			ps.setString(5, entidad.getUsuarioVehiculo().getId().toString());
			ps.setString(6, entidad.getEspacioFisico().getId().toString());
			ps.setBoolean(7, entidad.isEstadoActual());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al crear el contrato de mensualidad", "Error al crear el contrato de mensualidad");
		}
	}

	@Override
	public void cambiarEstadoActual(final UUID id, final boolean nuevoEstado) {
		final String sql = "UPDATE ContratoMensualidad SET estadoActual = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setBoolean(1, nuevoEstado);
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al cambiar el estado del contrato de mensualidad", "SQLException al cambiar estadoActual en tabla ContratoMensualidad: " + e.getMessage());
		}
	}

	@Override
	public ContratoMensualidadEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT cm.id, cm.fechaInicioContrato, cm.fechaFinContrato, cm.estadoActual, "
				+ "t.id as tarifa_id, t.valorServicio, "
				+ "uv.id as usuarioVehiculo_id, "
				+ "u.id as usuario_id, u.primerNombre, u.primerApellido, "
				+ "v.id as vehiculo_id, v.placaVehiculo, "
				+ "ef.id as espacioFisico_id, ef.numeroEspacioFisico "
				+ "FROM ContratoMensualidad cm "
				+ "INNER JOIN Tarifa t ON cm.tarifa_id = t.id "
				+ "INNER JOIN UsuarioVehiculo uv ON cm.usuarioVehiculo_id = uv.id "
				+ "INNER JOIN Usuario u ON uv.usuario_id = u.id "
				+ "INNER JOIN Vehiculo v ON uv.vehiculo_id = v.id "
				+ "INNER JOIN EspacioFisico ef ON cm.espacioFisico_id = ef.id "
				+ "WHERE cm.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirContratoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el contrato de mensualidad por id", "Error al consultar el contrato de mensualidad por id");
		}
		return null;
	}

	@Override
	public List<ContratoMensualidadEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<ContratoMensualidadEntidad> consultarPorFiltro(final ContratoMensualidadEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT cm.id, cm.fechaInicioContrato, cm.fechaFinContrato, cm.estadoActual, "
				+ "t.id as tarifa_id, t.valorServicio, "
				+ "uv.id as usuarioVehiculo_id, "
				+ "u.id as usuario_id, u.primerNombre, u.primerApellido, "
				+ "v.id as vehiculo_id, v.placaVehiculo, "
				+ "ef.id as espacioFisico_id, ef.numeroEspacioFisico "
				+ "FROM ContratoMensualidad cm "
				+ "INNER JOIN Tarifa t ON cm.tarifa_id = t.id "
				+ "INNER JOIN UsuarioVehiculo uv ON cm.usuarioVehiculo_id = uv.id "
				+ "INNER JOIN Usuario u ON uv.usuario_id = u.id "
				+ "INNER JOIN Vehiculo v ON uv.vehiculo_id = v.id "
				+ "INNER JOIN EspacioFisico ef ON cm.espacioFisico_id = ef.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilObjeto.esNulo(filtro.getTarifa()) && !UtilObjeto.esNulo(filtro.getTarifa().getId())) {
				sql.append(" AND cm.tarifa_id = ?");
				parametros.add(filtro.getTarifa().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getUsuarioVehiculo()) && !UtilObjeto.esNulo(filtro.getUsuarioVehiculo().getId())) {
				sql.append(" AND cm.usuarioVehiculo_id = ?");
				parametros.add(filtro.getUsuarioVehiculo().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getEspacioFisico()) && !UtilObjeto.esNulo(filtro.getEspacioFisico().getId())) {
				sql.append(" AND cm.espacioFisico_id = ?");
				parametros.add(filtro.getEspacioFisico().getId().toString());
			}
		}

		final List<ContratoMensualidadEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirContratoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar contratos de mensualidad", "Error al consultar contratos de mensualidad");
		}
		return resultados;
	}

	// Consulta contrato vigente para un espacio fisico especifico
	public ContratoMensualidadEntidad consultarContratoVigentePorEspacioFisico(final UUID espacioFisicoId) {
		final String sql = "SELECT cm.id, cm.fechaInicioContrato, cm.fechaFinContrato, cm.estadoActual, "
				+ "t.id as tarifa_id, t.valorServicio, "
				+ "uv.id as usuarioVehiculo_id, "
				+ "u.id as usuario_id, u.primerNombre, u.primerApellido, "
				+ "v.id as vehiculo_id, v.placaVehiculo, "
				+ "ef.id as espacioFisico_id, ef.numeroEspacioFisico "
				+ "FROM ContratoMensualidad cm "
				+ "INNER JOIN Tarifa t ON cm.tarifa_id = t.id "
				+ "INNER JOIN UsuarioVehiculo uv ON cm.usuarioVehiculo_id = uv.id "
				+ "INNER JOIN Usuario u ON uv.usuario_id = u.id "
				+ "INNER JOIN Vehiculo v ON uv.vehiculo_id = v.id "
				+ "INNER JOIN EspacioFisico ef ON cm.espacioFisico_id = ef.id "
				+ "WHERE cm.espacioFisico_id = ? "
				+ "AND cm.estadoActual = 1 "
				+ "AND CAST(GETDATE() AS DATE) BETWEEN cm.fechaInicioContrato AND cm.fechaFinContrato";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, espacioFisicoId.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirContratoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar contrato vigente del espacio fisico", "Error al consultar contrato vigente del espacio fisico");
		}
		return null;
	}

	// Consulta contrato vigente para un vehiculo especifico
	public ContratoMensualidadEntidad consultarContratoVigentePorVehiculo(final UUID vehiculoId) {
		final String sql = "SELECT cm.id, cm.fechaInicioContrato, cm.fechaFinContrato, cm.estadoActual, "
				+ "t.id as tarifa_id, t.valorServicio, "
				+ "uv.id as usuarioVehiculo_id, "
				+ "u.id as usuario_id, u.primerNombre, u.primerApellido, "
				+ "v.id as vehiculo_id, v.placaVehiculo, "
				+ "ef.id as espacioFisico_id, ef.numeroEspacioFisico "
				+ "FROM ContratoMensualidad cm "
				+ "INNER JOIN Tarifa t ON cm.tarifa_id = t.id "
				+ "INNER JOIN UsuarioVehiculo uv ON cm.usuarioVehiculo_id = uv.id "
				+ "INNER JOIN Usuario u ON uv.usuario_id = u.id "
				+ "INNER JOIN Vehiculo v ON uv.vehiculo_id = v.id "
				+ "INNER JOIN EspacioFisico ef ON cm.espacioFisico_id = ef.id "
				+ "WHERE uv.vehiculo_id = ? "
				+ "AND CAST(GETDATE() AS DATE) BETWEEN cm.fechaInicioContrato AND cm.fechaFinContrato";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, vehiculoId.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirContratoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar contrato vigente del vehiculo", "Error al consultar contrato vigente del vehiculo");
		}
		return null;
	}

	@Override
	public void actualizar(final UUID id, final ContratoMensualidadEntidad entidad) {
	    final String sql = "UPDATE ContratoMensualidad SET fechaFinContrato = ? WHERE id = ?";
	    try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
	        ps.setDate(1, new Date(entidad.getFechaFinContrato().getTime()));
	        ps.setString(2, id.toString());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        throw ERZParkingExcepcion.crear(e, "Error al finalizar el contrato de mensualidad", "SQLException al actualizar tabla ContratoMensualidad: " + e.getMessage());
	    }
	}
	
	private ContratoMensualidadEntidad construirContratoEntidad(final ResultSet rs) throws SQLException {
		var tarifa = new TarifaEntidad.Builder()
				.id(UUID.fromString(rs.getString("tarifa_id")))
				.valorServicio(rs.getDouble("valorServicio"))
				.build();
		var usuario = new UsuarioEntidad.Builder()
				.id(UUID.fromString(rs.getString("usuario_id")))
				.primerNombre(rs.getString("primerNombre"))
				.primerApellido(rs.getString("primerApellido"))
				.build();
		var vehiculo = new VehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("vehiculo_id")))
				.placaVehiculo(rs.getString("placaVehiculo"))
				.build();
		var usuarioVehiculo = new UsuarioVehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("usuarioVehiculo_id")))
				.usuario(usuario)
				.vehiculo(vehiculo)
				.build();
		var espacioFisico = new EspacioFisicoEntidad.Builder()
				.id(UUID.fromString(rs.getString("espacioFisico_id")))
				.numeroEspacioFisico(rs.getString("numeroEspacioFisico"))
				.build();
		return new ContratoMensualidadEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.fechaInicioContrato(rs.getDate("fechaInicioContrato"))
				.fechaFinContrato(rs.getDate("fechaFinContrato"))
				.tarifa(tarifa)
				.espacioFisico(espacioFisico)
				.usuarioVehiculo(usuarioVehiculo)
				.estadoActual(rs.getBoolean("estadoActual"))
				.build();
	}

}
