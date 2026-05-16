package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.OperarioDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.CargoEntidad;
import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.TipoDocumentoIdentificacionEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class OperarioSQLServerDAO extends SQLDAO implements OperarioDAO {

	public OperarioSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final OperarioEntidad entidad) {
		final String sql = "INSERT INTO Operario (id, tipoDocumentoIdentificacion_id, numeroIdentificacion, primerNombre, segundoNombre, primerApellido, segundoApellido, numeroTelefonico, cargo_id, parqueadero_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getTipoDocumentoIdentificacion().getId().toString());
			ps.setString(3, entidad.getNumeroIdentificacion());
			ps.setString(4, entidad.getPrimerNombre());
			ps.setString(5, entidad.getSegundoNombre());
			ps.setString(6, entidad.getPrimerApellido());
			ps.setString(7, entidad.getSegundoApellido());
			ps.setLong(8, entidad.getNumeroTelefonico());
			ps.setString(9, entidad.getCargo().getId().toString());
			ps.setString(10, entidad.getParqueadero().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al registrar el operario", "SQLException al insertar en tabla Operario: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(final UUID id, final OperarioEntidad entidad) {
		final String sql = "UPDATE Operario SET primerNombre = ?, segundoNombre = ?, primerApellido = ?, segundoApellido = ?, numeroTelefonico = ?, cargo_id = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getPrimerNombre());
			ps.setString(2, entidad.getSegundoNombre());
			ps.setString(3, entidad.getPrimerApellido());
			ps.setString(4, entidad.getSegundoApellido());
			ps.setLong(5, entidad.getNumeroTelefonico());
			ps.setString(6, entidad.getCargo().getId().toString());
			ps.setString(7, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el operario", "SQLException al actualizar tabla Operario: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM Operario WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el operario", "SQLException al eliminar de tabla Operario: " + e.getMessage());
		}
	}

	@Override
	public OperarioEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT o.id, o.numeroIdentificacion, o.primerNombre, o.segundoNombre, o.primerApellido, o.segundoApellido, o.numeroTelefonico, "
				+ "tdi.id as tipoDoc_id, c.id as cargo_id, p.id as parqueadero_id "
				+ "FROM Operario o "
				+ "INNER JOIN TipoDocumentoIdentificacion tdi ON o.tipoDocumentoIdentificacion_id = tdi.id "
				+ "INNER JOIN Cargo c ON o.cargo_id = c.id "
				+ "INNER JOIN Parqueadero p ON o.parqueadero_id = p.id "
				+ "WHERE o.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirOperarioEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el operario por id", "SQLException al consultar tabla Operario: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<OperarioEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<OperarioEntidad> consultarPorFiltro(final OperarioEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT o.id, o.numeroIdentificacion, o.primerNombre, o.segundoNombre, o.primerApellido, o.segundoApellido, o.numeroTelefonico, "
				+ "tdi.id as tipoDoc_id, c.id as cargo_id, p.id as parqueadero_id "
				+ "FROM Operario o "
				+ "INNER JOIN TipoDocumentoIdentificacion tdi ON o.tipoDocumentoIdentificacion_id = tdi.id "
				+ "INNER JOIN Cargo c ON o.cargo_id = c.id "
				+ "INNER JOIN Parqueadero p ON o.parqueadero_id = p.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilTexto.esNula(filtro.getNumeroIdentificacion()) && !filtro.getNumeroIdentificacion().isEmpty()) {
				sql.append(" AND o.numeroIdentificacion = ?");
				parametros.add(filtro.getNumeroIdentificacion());
			}
		}

		final List<OperarioEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirOperarioEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar operarios por filtro", "SQLException al consultar tabla Operario por filtro: " + e.getMessage());
		}
		return resultados;
	}

	private OperarioEntidad construirOperarioEntidad(final ResultSet rs) throws SQLException {
		var tipoDoc = new TipoDocumentoIdentificacionEntidad.Builder()
				.id(UUID.fromString(rs.getString("tipoDoc_id")))
				.build();
		var cargo = new CargoEntidad.Builder()
				.id(UUID.fromString(rs.getString("cargo_id")))
				.build();
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(UUID.fromString(rs.getString("parqueadero_id")))
				.build();
		return new OperarioEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.tipoDocumentoIdentificacion(tipoDoc)
				.numeroIdentificacion(rs.getString("numeroIdentificacion"))
				.primerNombre(rs.getString("primerNombre"))
				.segundoNombre(rs.getString("segundoNombre"))
				.primerApellido(rs.getString("primerApellido"))
				.segundoApellido(rs.getString("segundoApellido"))
				.numeroTelefonico(rs.getLong("numeroTelefonico"))
				.cargo(cargo)
				.parqueadero(parqueadero)
				.build();
	}

}