package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.TipoDocumentoIdentificacionDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.TipoDocumentoIdentificacionEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class TipoDocumentoIdentificacionSQLServerDAO extends SQLDAO implements TipoDocumentoIdentificacionDAO {

	public TipoDocumentoIdentificacionSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final TipoDocumentoIdentificacionEntidad entidad) {
		final String sql = "INSERT INTO TipoDocumentoIdentificacion (id, nombreDocumentoIdentificacion, descripcion) VALUES (?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getNombreDocumentoIdentificacion());
			ps.setString(3, entidad.getDescripcion());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al registrar el tipo de documento de identificacion", "SQLException al insertar en tabla TipoDocumentoIdentificacion: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM TipoDocumentoIdentificacion WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el tipo de documento de identificacion", "SQLException al eliminar de tabla TipoDocumentoIdentificacion: " + e.getMessage());
		}
	}

	@Override
	public TipoDocumentoIdentificacionEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT id, nombreDocumentoIdentificacion, descripcion FROM TipoDocumentoIdentificacion WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el tipo de documento de identificacion", "SQLException al consultar tabla TipoDocumentoIdentificacion: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<TipoDocumentoIdentificacionEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<TipoDocumentoIdentificacionEntidad> consultarPorFiltro(final TipoDocumentoIdentificacionEntidad filtro) {
		final StringBuilder sql = new StringBuilder("SELECT id, nombreDocumentoIdentificacion, descripcion FROM TipoDocumentoIdentificacion WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro) && !UtilTexto.esNula(filtro.getNombreDocumentoIdentificacion()) && !filtro.getNombreDocumentoIdentificacion().isEmpty()) {
			sql.append(" AND nombreDocumentoIdentificacion LIKE ?");
			parametros.add("%" + filtro.getNombreDocumentoIdentificacion() + "%");
		}

		final List<TipoDocumentoIdentificacionEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirEntidad(rs));
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar los tipos de documento de identificacion", "SQLException al consultar tabla TipoDocumentoIdentificacion por filtro: " + e.getMessage());
		}
		return resultados;
	}

	private TipoDocumentoIdentificacionEntidad construirEntidad(final ResultSet rs) throws SQLException {
		return new TipoDocumentoIdentificacionEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.nombreDocumentoIdentificacion(rs.getString("nombreDocumentoIdentificacion"))
				.descripcion(rs.getString("descripcion"))
				.build();
	}

}
