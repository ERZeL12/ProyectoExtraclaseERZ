package co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.RegistrarTipoDocumentoIdentificacionCasoUso;
import co.uco.erzparking.negocio.casouso.tipodocumentoidentificacion.impl.RegistrarTipoDocumentoIdentificacionCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.RegistrarTipoDocumentoIdentificacionFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarTipoDocumentoIdentificacionFachadaImpl implements RegistrarTipoDocumentoIdentificacionFachada {

	private DAOFactory daoFactory;
	private RegistrarTipoDocumentoIdentificacionCasoUso casoUso;

	public RegistrarTipoDocumentoIdentificacionFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarTipoDocumentoIdentificacionCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final TipoDocumentoIdentificacionDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new TipoDocumentoIdentificacionDominio.Builder()
					.id(datos.getId())
					.nombreDocumentoIdentificacion(datos.getNombreDocumentoIdentificacion())
					.descripcion(datos.getDescripcion())
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
			var dto = new co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO.Builder()
					.nombreDocumentoIdentificacion("Cedula de Ciudadania")
					.descripcion("Documento de identificacion nacional para mayores de 18 años")
					.build();
			new RegistrarTipoDocumentoIdentificacionFachadaImpl().ejecutar(dto);
			System.out.println("Tipo documento registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
