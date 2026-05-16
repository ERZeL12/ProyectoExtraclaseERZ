package co.uco.erzparking.negocio.fachada.tiposervicio.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.casouso.tiposervicio.RegistrarTipoServicioCasoUso;
import co.uco.erzparking.negocio.casouso.tiposervicio.impl.RegistrarTipoServicioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.negocio.fachada.tiposervicio.RegistrarTipoServicioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarTipoServicioFachadaImpl implements RegistrarTipoServicioFachada {

	private DAOFactory daoFactory;
	private RegistrarTipoServicioCasoUso casoUso;

	public RegistrarTipoServicioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarTipoServicioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final TipoServicioDTO datos) {
		try {

			var dominio = new TipoServicioDominio.Builder()
					.id(datos.getId())
					.nombreServicio(datos.getNombreServicio())
					.build();
			casoUso.ejecutar(dominio);
			daoFactory.confirmarTransaccion();
		} catch (ERZParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	public static void main(final String[] args) {
		try {
			var dto = new TipoServicioDTO.Builder()
					.nombreServicio("Parqueo por horas")
					.build();
			new RegistrarTipoServicioFachadaImpl().ejecutar(dto);
			System.out.println("TipoServicio registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
