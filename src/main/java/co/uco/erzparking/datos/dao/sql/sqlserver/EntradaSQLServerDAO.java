package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.EntradaDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.EntradaEntidad;
import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.entidad.TipoVehiculoEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class EntradaSQLServerDAO extends SQLDAO implements EntradaDAO {

	public EntradaSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final EntradaEntidad entidad) {
		final String sql = "INSERT INTO Entrada (id, fechaHoraEntrada, vehiculo_id, servicio_id, operario_id) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setTimestamp(2, Timestamp.valueOf(entidad.getFechaHoraEntrada()));
			ps.setString(3, entidad.getVehiculo().getId().toString());
			ps.setString(4, entidad.getServicio().getId().toString());
			ps.setString(5, entidad.getOperario().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al registrar la entrada en la base de datos", "SQLException al insertar en tabla Entrada: " + e.getMessage());
		}
	}

	@Override
	public EntradaEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT e.id, e.fechaHoraEntrada, "
				+ "v.id as vehiculo_id, v.placaVehiculo, "
				+ "tv.id as tipoVehiculo_id, tv.nombreVehiculo, "
				+ "s.id as servicio_id, s.nombreServicio, "
				+ "p.id as parqueadero_id, p.nombreEstablecimiento as parqueadero_nombre, p.direccionEstablecimiento as parqueadero_direccion, "
				+ "o.id as operario_id, o.primerNombre as operario_primerNombre, o.primerApellido as operario_primerApellido, o.estadoActual as operario_estadoActual "
				+ "FROM Entrada e "
				+ "INNER JOIN Vehiculo v ON e.vehiculo_id = v.id "
				+ "INNER JOIN TipoVehiculo tv ON v.tipoVehiculo_id = tv.id "
				+ "INNER JOIN Servicio s ON e.servicio_id = s.id "
				+ "INNER JOIN Parqueadero p ON s.parqueadero_id = p.id "
				+ "INNER JOIN Operario o ON e.operario_id = o.id "
				+ "WHERE e.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirEntradaEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar la entrada por id", "SQLException al consultar tabla Entrada por id: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<EntradaEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<EntradaEntidad> consultarPorFiltro(final EntradaEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT e.id, e.fechaHoraEntrada, "
				+ "v.id as vehiculo_id, v.placaVehiculo, "
				+ "tv.id as tipoVehiculo_id, tv.nombreVehiculo, "
				+ "s.id as servicio_id, s.nombreServicio, "
				+ "p.id as parqueadero_id, p.nombreEstablecimiento as parqueadero_nombre, p.direccionEstablecimiento as parqueadero_direccion, "
				+ "o.id as operario_id, o.primerNombre as operario_primerNombre, o.primerApellido as operario_primerApellido, o.estadoActual as operario_estadoActual "
				+ "FROM Entrada e "
				+ "INNER JOIN Vehiculo v ON e.vehiculo_id = v.id "
				+ "INNER JOIN TipoVehiculo tv ON v.tipoVehiculo_id = tv.id "
				+ "INNER JOIN Servicio s ON e.servicio_id = s.id "
				+ "INNER JOIN Parqueadero p ON s.parqueadero_id = p.id "
				+ "INNER JOIN Operario o ON e.operario_id = o.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilObjeto.esNulo(filtro.getVehiculo()) && !UtilObjeto.esNulo(filtro.getVehiculo().getId())) {
				sql.append(" AND e.vehiculo_id = ?");
				parametros.add(filtro.getVehiculo().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getServicio()) && !UtilObjeto.esNulo(filtro.getServicio().getId())) {
				sql.append(" AND e.servicio_id = ?");
				parametros.add(filtro.getServicio().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getOperario()) && !UtilObjeto.esNulo(filtro.getOperario().getId())) {
				sql.append(" AND e.operario_id = ?");
				parametros.add(filtro.getOperario().getId().toString());
			}
		}

		final List<EntradaEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirEntradaEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar entradas por filtro", "SQLException al consultar tabla Entrada por filtro: " + e.getMessage());
		}
		return resultados;
	}

	private EntradaEntidad construirEntradaEntidad(final ResultSet rs) throws SQLException {
		var tipoVehiculo = new TipoVehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("tipoVehiculo_id")))
				.nombreVehiculo(rs.getString("nombreVehiculo"))
				.build();
		var vehiculo = new VehiculoEntidad.Builder()
				.id(UUID.fromString(rs.getString("vehiculo_id")))
				.placaVehiculo(rs.getString("placaVehiculo"))
				.tipoVehiculo(tipoVehiculo)
				.build();
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(UUID.fromString(rs.getString("parqueadero_id")))
				.nombreEstablecimiento(rs.getString("parqueadero_nombre"))
				.direccionEstablecimiento(rs.getString("parqueadero_direccion"))
				.build();
		var servicio = new ServicioEntidad.Builder()
				.id(UUID.fromString(rs.getString("servicio_id")))
				.nombreServicio(rs.getString("nombreServicio"))
				.parqueadero(parqueadero)
				.build();
		var operario = new OperarioEntidad.Builder()
				.id(UUID.fromString(rs.getString("operario_id")))
				.primerNombre(rs.getString("operario_primerNombre"))
				.primerApellido(rs.getString("operario_primerApellido"))
				.estadoActual(rs.getBoolean("operario_estadoActual"))
				.build();
		return new EntradaEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.fechaHoraEntrada(rs.getTimestamp("fechaHoraEntrada").toLocalDateTime())
				.vehiculo(vehiculo)
				.servicio(servicio)
				.operario(operario)
				.build();
	}

}