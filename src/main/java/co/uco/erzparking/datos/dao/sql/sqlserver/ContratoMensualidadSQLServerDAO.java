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
import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ContratoMensualidadSQLServerDAO extends SQLDAO implements ContratoMensualidadDAO {

	public ContratoMensualidadSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final ContratoMensualidadEntidad entidad) {
		final String sql = "INSERT INTO ContratoMensualidad (id, fechaInicioContrato, fechaFinContrato, tarifa_id, usuarioVehiculo_id, espacioFisico_id) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setDate(2, new Date(entidad.getFechaInicioContrato().getTime()));
			ps.setDate(3, new Date(entidad.getFechaFinContrato().getTime()));
			ps.setString(4, entidad.getTarifa().getId().toString());
			ps.setString(5, entidad.getUsuarioVehiculo().getId().toString());
			ps.setString(6, entidad.getEspacioFisico().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al crear el contrato de mensualidad", "Error al crear el contrato de mensualidad");
		}
	}

	@Override
	public ContratoMensualidadEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT id, fechaInicioContrato, fechaFinContrato, tarifa_id, usuarioVehiculo_id, espacioFisico_id "
				+ "FROM ContratoMensualidad WHERE id = ?";
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
		final String sql = "SELECT id, fechaInicioContrato, fechaFinContrato, tarifa_id, usuarioVehiculo_id, espacioFisico_id FROM ContratoMensualidad";
		final List<ContratoMensualidadEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				resultados.add(construirContratoEntidad(rs));
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar contratos de mensualidad", "Error al consultar contratos de mensualidad");
		}
		return resultados;
	}

	// Consulta contrato vigente para un vehiculo especifico
	public ContratoMensualidadEntidad consultarContratoVigentePorVehiculo(final UUID vehiculoId) {
		final String sql = "SELECT cm.id, cm.fechaInicioContrato, cm.fechaFinContrato, cm.tarifa_id, cm.usuarioVehiculo_id, cm.espacioFisico_id "
				+ "FROM ContratoMensualidad cm "
				+ "INNER JOIN UsuarioVehiculo uv ON cm.usuarioVehiculo_id = uv.id "
				+ "WHERE uv.vehiculo_id = ? "
				+ "AND GETDATE() BETWEEN cm.fechaInicioContrato AND cm.fechaFinContrato";
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
		var espacioFisico = new EspacioFisicoEntidad.Builder()
				.id(UUID.fromString(rs.getString("espacioFisico_id")))
				.build();
		var usuarioVehiculo = new UsuarioVehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("usuarioVehiculo_id")))
				.build();
		return new ContratoMensualidadEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.fechaInicioContrato(rs.getDate("fechaInicioContrato"))
				.fechaFinContrato(rs.getDate("fechaFinContrato"))
				.espacioFisico(espacioFisico)
				.usuarioVehiculo(usuarioVehiculo)
				.build();
	}

}
