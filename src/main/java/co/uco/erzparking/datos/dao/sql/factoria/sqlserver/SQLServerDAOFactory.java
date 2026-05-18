package co.uco.erzparking.datos.dao.sql.factoria.sqlserver;

import java.sql.DriverManager;
import java.sql.SQLException;

import co.uco.erzparking.datos.dao.CargoDAO;
import co.uco.erzparking.datos.dao.CiudadDAO;
import co.uco.erzparking.datos.dao.ContratoMensualidadDAO;
import co.uco.erzparking.datos.dao.DepartamentoDAO;
import co.uco.erzparking.datos.dao.EntradaDAO;
import co.uco.erzparking.datos.dao.EspacioFisicoDAO;
import co.uco.erzparking.datos.dao.EstadoEspacioFisicoDAO;
import co.uco.erzparking.datos.dao.OperarioDAO;
import co.uco.erzparking.datos.dao.PaisDAO;
import co.uco.erzparking.datos.dao.ParqueaderoDAO;
import co.uco.erzparking.datos.dao.ServicioDAO;
import co.uco.erzparking.datos.dao.TarifaDAO;
import co.uco.erzparking.datos.dao.TipoDocumentoIdentificacionDAO;
import co.uco.erzparking.datos.dao.TipoServicioDAO;
import co.uco.erzparking.datos.dao.TipoVehiculoDAO;
import co.uco.erzparking.datos.dao.UsuarioDAO;
import co.uco.erzparking.datos.dao.UsuarioVehiculoDAO;
import co.uco.erzparking.datos.dao.VehiculoDAO;
import co.uco.erzparking.datos.dao.ZonaParqueaderoDAO;
import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.datos.dao.sql.sqlserver.CargoSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.CiudadSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.ContratoMensualidadSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.DepartamentoSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.EntradaSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.EspacioFisicoSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.EstadoEspacioFisicoSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.OperarioSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.PaisSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.ParqueaderoSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.ServicioSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.TarifaSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.TipoDocumentoIdentificacionSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.TipoServicioSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.TipoVehiculoSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.UsuarioSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.UsuarioVehiculoSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.VehiculoSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.ZonaParqueaderoSQLServerDAO;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class SQLServerDAOFactory extends DAOFactory {

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ERZParking;encrypt=false;trustServerCertificate=true";
	private static final String USUARIO = "sa";
	private static final String CONTRASENA = "ERZParking2025*";

	public SQLServerDAOFactory() { abrirConexion(); }

	@Override
	protected void abrirConexion() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
			conexion.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			throw ERZParkingExcepcion.crear(e, "No fue posible abrir la conexion con la base de datos", "Error al conectar a SQL Server: " + e.getMessage());
		}
	}

	@Override
	public void cerrarConexion() {
		try {
			if (conexion != null && !conexion.isClosed()) conexion.close();
		} catch (SQLException e) {
			throw ERZParkingExcepcion.crear(e, "No fue posible cerrar la conexion", "Error al cerrar conexion SQL: " + e.getMessage());
		}
	}

	@Override
	public void iniciarTransaccion() {
		try { conexion.setAutoCommit(false); }
		catch (SQLException e) { throw ERZParkingExcepcion.crear(e, "No fue posible iniciar la transaccion", e.getMessage()); }
	}

	@Override
	public void confirmarTransaccion() {
		try { conexion.commit(); }
		catch (SQLException e) { throw ERZParkingExcepcion.crear(e, "No fue posible confirmar la transaccion", e.getMessage()); }
	}

	@Override
	public void cancelarTransaccion() {
		try { conexion.rollback(); }
		catch (SQLException e) { throw ERZParkingExcepcion.crear(e, "No fue posible cancelar la transaccion", e.getMessage()); }
	}

	@Override public EntradaDAO getEntradaDAO() { return new EntradaSQLServerDAO(conexion); }
	@Override public EspacioFisicoDAO getEspacioFisicoDAO() { return new EspacioFisicoSQLServerDAO(conexion); }
	@Override public VehiculoDAO getVehiculoDAO() { return new VehiculoSQLServerDAO(conexion); }
	@Override public TarifaDAO getTarifaDAO() { return new TarifaSQLServerDAO(conexion); }
	@Override public ContratoMensualidadDAO getContratoMensualidadDAO() { return new ContratoMensualidadSQLServerDAO(conexion); }
	@Override public UsuarioVehiculoDAO getUsuarioVehiculoDAO() { return new UsuarioVehiculoSQLServerDAO(conexion); }
	@Override public UsuarioDAO getUsuarioDAO() { return new UsuarioSQLServerDAO(conexion); }
	@Override public ServicioDAO getServicioDAO() { return new ServicioSQLServerDAO(conexion); }
	@Override public TipoVehiculoDAO getTipoVehiculoDAO() { return new TipoVehiculoSQLServerDAO(conexion); }
	@Override public TipoServicioDAO getTipoServicioDAO() { return new TipoServicioSQLServerDAO(conexion); }
	@Override public EstadoEspacioFisicoDAO getEstadoEspacioFisicoDAO() { return new EstadoEspacioFisicoSQLServerDAO(conexion); }
	@Override public ZonaParqueaderoDAO getZonaParqueaderoDAO() { return new ZonaParqueaderoSQLServerDAO(conexion); }
	@Override public ParqueaderoDAO getParqueaderoDAO() { return new ParqueaderoSQLServerDAO(conexion); }
	@Override public CiudadDAO getCiudadDAO() { return new CiudadSQLServerDAO(conexion); }
	@Override public DepartamentoDAO getDepartamentoDAO() { return new DepartamentoSQLServerDAO(conexion); }
	@Override public PaisDAO getPaisDAO() { return new PaisSQLServerDAO(conexion); }
	@Override public TipoDocumentoIdentificacionDAO getTipoDocumentoIdentificacionDAO() { return new TipoDocumentoIdentificacionSQLServerDAO(conexion); }
	@Override public OperarioDAO getOperarioDAO() { return new OperarioSQLServerDAO(conexion); }
	@Override public CargoDAO getCargoDAO() { return new CargoSQLServerDAO(conexion); }

}
