package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.ZonaParqueaderoDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.entidad.ZonaParqueaderoEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ZonaParqueaderoSQLServerDAO extends SQLDAO implements ZonaParqueaderoDAO {

	public ZonaParqueaderoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final ZonaParqueaderoEntidad entidad) {
		final String sql = "INSERT INTO ZonaParqueadero (id, nombreZona, parqueadero_id) VALUES (?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombreZona());
			ps.setString(3, entidad.getParqueadero().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al crear la zona del parqueadero", "Error al crear la zona del parqueadero");
		}
	}

	@Override
	public void actualizar(final UUID id, final ZonaParqueaderoEntidad entidad) {
		final String sql = "UPDATE ZonaParqueadero SET nombreZona = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getNombreZona());
			ps.setString(2, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar la zona del parqueadero", "Error al actualizar la zona del parqueadero");
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM ZonaParqueadero WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar la zona del parqueadero", "Error al eliminar la zona del parqueadero");
		}
	}

	@Override
	public ZonaParqueaderoEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT z.id, z.nombreZona, p.id as parqueadero_id FROM ZonaParqueadero z INNER JOIN Parqueadero p ON z.parqueadero_id = p.id WHERE z.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirZonaEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar la zona por id", "Error al consultar la zona por id");
		}
		return null;
	}

	@Override
	public List<ZonaParqueaderoEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<ZonaParqueaderoEntidad> consultarPorFiltro(final ZonaParqueaderoEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT z.id, z.nombreZona, p.id as parqueadero_id FROM ZonaParqueadero z INNER JOIN Parqueadero p ON z.parqueadero_id = p.id WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro) && !UtilTexto.esNula(filtro.getNombreZona()) && !filtro.getNombreZona().isEmpty()) {
			sql.append(" AND z.nombreZona LIKE ?");
			parametros.add("%" + filtro.getNombreZona() + "%");
		}

		final List<ZonaParqueaderoEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirZonaEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar zonas por filtro", "Error al consultar zonas por filtro");
		}
		return resultados;
	}

	private ZonaParqueaderoEntidad construirZonaEntidad(final ResultSet rs) throws SQLException {
		var parqueadero = new ParqueaderoEntidad.Builder()
				.id(UUID.fromString(rs.getString("parqueadero_id")))
				.build();
		return new ZonaParqueaderoEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombreZona(rs.getString("nombreZona"))
				.parqueadero(parqueadero)
				.build();
	}

}
