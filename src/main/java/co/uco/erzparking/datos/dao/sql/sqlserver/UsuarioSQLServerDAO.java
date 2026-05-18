package co.uco.erzparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.uco.erzparking.datos.dao.UsuarioDAO;
import co.uco.erzparking.datos.dao.sql.SQLDAO;
import co.uco.erzparking.entidad.CiudadEntidad;
import co.uco.erzparking.entidad.TipoDocumentoIdentificacionEntidad;
import co.uco.erzparking.entidad.UsuarioEntidad;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class UsuarioSQLServerDAO extends SQLDAO implements UsuarioDAO {

	public UsuarioSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final UsuarioEntidad entidad) {
		final String sql = "INSERT INTO Usuario (id, tipoDocumentoIdentificacion_id, numeroIdentificacion, primerNombre, segundoNombre, primerApellido, segundoApellido, numeroTelefonico, correoElectronico, ciudad_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getId().toString());
			ps.setString(2, entidad.getTipoDocumentoIdentificacion().getId().toString());
			ps.setString(3, entidad.getNumeroIdentificacion());
			ps.setString(4, entidad.getPrimerNombre());
			ps.setString(5, entidad.getSegundoNombre());
			ps.setString(6, entidad.getPrimerApellido());
			ps.setString(7, entidad.getSegundoApellido());
			ps.setLong(8, entidad.getNumeroTelefonico());
			ps.setString(9, entidad.getCorreoElectronico());
			ps.setString(10, entidad.getCiudad().getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
		    System.err.println("SQL Error: " + e.getMessage());
		    System.err.println("SQL State: " + e.getSQLState());
		    System.err.println("Error Code: " + e.getErrorCode());
		    throw ERZParkingExcepcion.crear(e, "Error al crear el usuario", "SQLException: " + e.getMessage());
		}
	}

	@Override
	public void actualizar(final UUID id, final UsuarioEntidad entidad) {
		final String sql = "UPDATE Usuario SET primerNombre = ?, segundoNombre = ?, primerApellido = ?, segundoApellido = ?, numeroTelefonico = ?, correoElectronico = ?, ciudad_id = ? WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, entidad.getPrimerNombre());
			ps.setString(2, entidad.getSegundoNombre());
			ps.setString(3, entidad.getPrimerApellido());
			ps.setString(4, entidad.getSegundoApellido());
			ps.setLong(5, entidad.getNumeroTelefonico());
			ps.setString(6, entidad.getCorreoElectronico());
			ps.setString(7, entidad.getCiudad().getId().toString());
			ps.setString(8, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al actualizar el usuario", "Error al actualizar el usuario");
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sql = "DELETE FROM Usuario WHERE id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al eliminar el usuario", "Error al eliminar el usuario");
		}
	}

	@Override
	public UsuarioEntidad consultarPorId(final UUID id) {
		final String sql = "SELECT u.id, u.numeroIdentificacion, u.primerNombre, u.segundoNombre, u.primerApellido, u.segundoApellido, u.numeroTelefonico, u.correoElectronico, "
				+ "tdi.id as tipoDoc_id, tdi.nombreDocumentoIdentificacion as tipoDoc_nombre, "
				+ "c.id as ciudad_id, c.nombre as ciudad_nombre "
				+ "FROM Usuario u "
				+ "INNER JOIN TipoDocumentoIdentificacion tdi ON u.tipoDocumentoIdentificacion_id = tdi.id "
				+ "INNER JOIN Ciudad c ON u.ciudad_id = c.id "
				+ "WHERE u.id = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, id.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirUsuarioEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el usuario por id", "Error al consultar el usuario por id");
		}
		return null;
	}

	@Override
	public List<UsuarioEntidad> consultarTodos() {
		return consultarPorFiltro(null);
	}

	@Override
	public List<UsuarioEntidad> consultarPorFiltro(final UsuarioEntidad filtro) {
		final StringBuilder sql = new StringBuilder(
				"SELECT u.id, u.numeroIdentificacion, u.primerNombre, u.segundoNombre, u.primerApellido, u.segundoApellido, u.numeroTelefonico, u.correoElectronico, "
				+ "tdi.id as tipoDoc_id, tdi.nombreDocumentoIdentificacion as tipoDoc_nombre, "
				+ "c.id as ciudad_id, c.nombre as ciudad_nombre "
				+ "FROM Usuario u "
				+ "INNER JOIN TipoDocumentoIdentificacion tdi ON u.tipoDocumentoIdentificacion_id = tdi.id "
				+ "INNER JOIN Ciudad c ON u.ciudad_id = c.id "
				+ "WHERE 1=1");
		final List<Object> parametros = new ArrayList<>();

		if (!UtilObjeto.esNulo(filtro)) {
			if (!UtilTexto.esNula(filtro.getNumeroIdentificacion()) && !filtro.getNumeroIdentificacion().isEmpty()) {
				sql.append(" AND u.numeroIdentificacion = ?");
				parametros.add(filtro.getNumeroIdentificacion());
			}
			if (!UtilTexto.esNula(filtro.getCorreoElectronico()) && !filtro.getCorreoElectronico().isEmpty()) {
				sql.append(" AND u.correoElectronico = ?");
				parametros.add(filtro.getCorreoElectronico());
			}
			if (!UtilObjeto.esNulo(filtro.getCiudad()) && !UtilObjeto.esNulo(filtro.getCiudad().getId())) {
				sql.append(" AND u.ciudad_id = ?");
				parametros.add(filtro.getCiudad().getId().toString());
			}
			if (!UtilObjeto.esNulo(filtro.getTipoDocumentoIdentificacion()) && !UtilObjeto.esNulo(filtro.getTipoDocumentoIdentificacion().getId())) {
				sql.append(" AND u.tipoDocumentoIdentificacion_id = ?");
				parametros.add(filtro.getTipoDocumentoIdentificacion().getId().toString());
			}
		}

		final List<UsuarioEntidad> resultados = new ArrayList<>();
		try (PreparedStatement ps = getConexion().prepareStatement(sql.toString())) {
			for (int i = 0; i < parametros.size(); i++) {
				ps.setObject(i + 1, parametros.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(construirUsuarioEntidad(rs));
				}
			}
		} catch (SQLException e) {
		    System.err.println("SQL Error: " + e.getMessage());
		    System.err.println("SQL State: " + e.getSQLState());
		    System.err.println("Error Code: " + e.getErrorCode());
		    throw ERZParkingExcepcion.crear(e, "Error al crear el usuario", "SQLException: " + e.getMessage());
		}
		return resultados;
	}

	@Override
	public UsuarioEntidad consultarPorNumeroIdentificacion(final String numeroIdentificacion) {
		final String sql = "SELECT u.id, u.numeroIdentificacion, u.primerNombre, u.segundoNombre, u.primerApellido, u.segundoApellido, u.numeroTelefonico, u.correoElectronico, "
				+ "tdi.id as tipoDoc_id, tdi.nombreDocumentoIdentificacion as tipoDoc_nombre, "
				+ "c.id as ciudad_id, c.nombre as ciudad_nombre "
				+ "FROM Usuario u "
				+ "INNER JOIN TipoDocumentoIdentificacion tdi ON u.tipoDocumentoIdentificacion_id = tdi.id "
				+ "INNER JOIN Ciudad c ON u.ciudad_id = c.id "
				+ "WHERE u.numeroIdentificacion = ?";
		try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
			ps.setString(1, numeroIdentificacion);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return construirUsuarioEntidad(rs);
				}
			}
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "Error al consultar el usuario por numero de identificacion", "SQLException al consultar tabla Usuario por numeroIdentificacion: " + e.getMessage());
		}
		return null;
	}

	private UsuarioEntidad construirUsuarioEntidad(final ResultSet rs) throws SQLException {
		var tipoDoc = new TipoDocumentoIdentificacionEntidad.Builder()
				.id(UUID.fromString(rs.getString("tipoDoc_id")))
				.nombreDocumentoIdentificacion(rs.getString("tipoDoc_nombre"))
				.build();
		var ciudad = new CiudadEntidad.Builder()
				.id(UUID.fromString(rs.getString("ciudad_id")))
				.nombre(rs.getString("ciudad_nombre"))
				.build();
		return new UsuarioEntidad.Builder()
				.id(UUID.fromString(rs.getString("id")))
				.tipoDocumentoIdentificacion(tipoDoc)
				.numeroIdentificacion(rs.getString("numeroIdentificacion"))
				.primerNombre(rs.getString("primerNombre"))
				.segundoNombre(rs.getString("segundoNombre"))
				.primerApellido(rs.getString("primerApellido"))
				.segundoApellido(rs.getString("segundoApellido"))
				.numeroTelefonico(rs.getLong("numeroTelefonico"))
				.correoElectronico(rs.getString("correoElectronico"))
				.ciudad(ciudad)
				.build();
	}

}
