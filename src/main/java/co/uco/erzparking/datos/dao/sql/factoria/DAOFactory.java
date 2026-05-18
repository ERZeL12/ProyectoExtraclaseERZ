package co.uco.erzparking.datos.dao.sql.factoria;

import java.sql.Connection;

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
import co.uco.erzparking.datos.dao.sql.factoria.sqlserver.SQLServerDAOFactory;

public abstract class DAOFactory {

	protected Connection conexion;
	private static TipoFactoriaEnum FACTORIA_ACTUAL = TipoFactoriaEnum.SQLSERVER;

	public static DAOFactory getFactory() {
		switch (FACTORIA_ACTUAL) {
		case SQLSERVER:
			return new SQLServerDAOFactory();
		default:
			throw new IllegalArgumentException("Tipo de factoria no soportada: " + FACTORIA_ACTUAL);
		}
	}

	protected abstract void abrirConexion();
	public abstract void cerrarConexion();
	public abstract void iniciarTransaccion();
	public abstract void confirmarTransaccion();
	public abstract void cancelarTransaccion();

	public abstract EntradaDAO getEntradaDAO();
	public abstract EspacioFisicoDAO getEspacioFisicoDAO();
	public abstract VehiculoDAO getVehiculoDAO();
	public abstract TarifaDAO getTarifaDAO();
	public abstract ContratoMensualidadDAO getContratoMensualidadDAO();
	public abstract UsuarioVehiculoDAO getUsuarioVehiculoDAO();
	public abstract UsuarioDAO getUsuarioDAO();
	public abstract ServicioDAO getServicioDAO();
	public abstract TipoVehiculoDAO getTipoVehiculoDAO();
	public abstract TipoServicioDAO getTipoServicioDAO();
	public abstract EstadoEspacioFisicoDAO getEstadoEspacioFisicoDAO();
	public abstract ZonaParqueaderoDAO getZonaParqueaderoDAO();
	public abstract ParqueaderoDAO getParqueaderoDAO();
	public abstract CiudadDAO getCiudadDAO();
	public abstract DepartamentoDAO getDepartamentoDAO();
	public abstract PaisDAO getPaisDAO();
	public abstract TipoDocumentoIdentificacionDAO getTipoDocumentoIdentificacionDAO();
	public abstract OperarioDAO getOperarioDAO();
	public abstract CargoDAO getCargoDAO();

}
