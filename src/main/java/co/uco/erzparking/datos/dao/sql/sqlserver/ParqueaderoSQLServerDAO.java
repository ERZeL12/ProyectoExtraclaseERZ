package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.ParqueaderoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ParqueaderoSQLServerDAO extends SQLDAO implements ParqueaderoDAO {

	public ParqueaderoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final ParqueaderoEntidad entidad) {
		final String sql = "INSERT INTO Parqueadero (id, nombreEstablecimiento, numeroTelefonico, correoElectronico, direccionEstablecimiento, ciudad_id) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombreEstablecimiento());
			ps.setLong(3, entidad.getNumeroTelefonico());
			ps.setString(4, entidad.getCorreoElectronico());
			ps.setString(5, entidad.getDireccionEstablecimiento());
			ps.setString(6, entidad.getCiudad().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al crear el parqueadero", "Error al crear el parqueadero");
		}
	}

	@Override
	public void actualizar(final UUID id, final ParqueaderoEntidad entidad) {
		final String sql = "UPDATE Parqueadero SET nombreEstablecimiento = ?, numeroTelefonico = ?, correoElectronico = ?, direccionEstablecimiento = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombreEstablecimiento());
			ps.setLong(2, entidad.getNumeroTelefonico());
			ps.setString(3, entidad.getCorreoElectronico());
			ps.setString(4, entidad.getDireccionEstablecimiento());
			ps.setString(5, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el parqueadero", "Error al actualizar el parqueadero");
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM Parqueadero WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el parqueadero", "Error al eliminar el parqueadero");
		}
	}

	@Override
	public ParqueaderoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT p.id, p.nombreEstablecimiento, p.numeroTelefonico, p.correoElectronico, p.direccionEstablecimiento, c.id as ciudad_id, c.nombre as ciudad_nombre "
				+ "FROM Parqueadero p INNER JOIN Ciudad c ON p.ciudad_id = c.id WHERE p.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirParqueaderoEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el parqueadero por id", "Error al consultar el parqueadero por id");
		}
		return null;
	}

	@Override
	public List<ParqueaderoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<ParqueaderoEntidad> consultarPorFiltro(final ParqueaderoEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT p.id, p.nombreEstablecimiento, p.numeroTelefonico, p.correoElectronico, p.direccionEstablecimiento, c.id as ciudad_id, c.nombre as ciudad_nombre "
				+ "FROM Parqueadero p INNER JOIN Ciudad c ON p.ciudad_id = c.id WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilTexto.esNula(filtro.getNombreEstablecimiento()) && !filtro.getNombreEstablecimiento().isEmpty()) {
				sql.append(" AND p.nombreEstablecimiento LIKE ?");
				parametros.add("%" + filtro.getNombreEstablecimiento() + "%");
			}
			if (!UtilObjeto.esNulo(filtro.getCiudad()) && !UtilObjeto.esNulo(filtro.getCiudad().getId())) {
				sql.append(" AND p.ciudad_id = ?");
				parametros.add(filtro.getCiudad().getId().toString());
			}
		}

		final List<ParqueaderoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirParqueaderoEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar parqueaderos por filtro", "Error al consultar parqueaderos por filtro");
		}
		return resultados;
	}

	private ParqueaderoEntidad construirParqueaderoEntidad(final ResultSet rs) throws SQLException {
		var ciudad = new CiudadEntidad.Builder()
				.id(UUID.fromString(rs.getString("ciudad_id")))
				.nombre(rs.getString("ciudad_nombre"))
				.build();
		return new ParqueaderoEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombreEstablecimiento(rs.getString("nombreEstablecimiento"))
				.numeroTelefonico(rs.getLong("numeroTelefonico"))
				.correoElectronico(rs.getString("correoElectronico"))
				.direccionEstablecimiento(rs.getString("direccionEstablecimiento"))
				.ciudad(ciudad)
				.build();
	}

}
