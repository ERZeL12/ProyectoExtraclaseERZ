package co.uco.erzparking.negocio.fachada.operario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.casouso.operario.RegistrarOperarioCasoUso;
import co.uco.erzparking.negocio.casouso.operario.impl.RegistrarOperarioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.dominio.OperarioDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.TipoDocumentoIdentificacionDominio;
import co.uco.erzparking.negocio.fachada.operario.RegistrarOperarioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarOperarioFachadaImpl implements RegistrarOperarioFachada {

	private DAOFactory daoFactory;
	private RegistrarOperarioCasoUso casoUso;

	public RegistrarOperarioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarOperarioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final OperarioDTO datos) {
		try {

			var dominio = new OperarioDominio.Builder()
					.id(datos.getId())
					.tipoDocumentoIdentificacion(datos.getTipoDocumentoIdentificacion() != null ? new TipoDocumentoIdentificacionDominio.Builder()
							.id(datos.getTipoDocumentoIdentificacion().getId())
							.build() : null)
					.numeroIdentificacion(datos.getNumeroIdentificacion())
					.primerNombre(datos.getPrimerNombre())
					.segundoNombre(datos.getSegundoNombre())
					.primerApellido(datos.getPrimerApellido())
					.segundoApellido(datos.getSegundoApellido())
					.numeroTelefonico(datos.getNumeroTelefonico())
					.cargo(datos.getCargo() != null ? new CargoDominio.Builder()
							.id(datos.getCargo().getId())
							.build() : null)
					.parqueadero(datos.getParqueadero() != null ? new ParqueaderoDominio.Builder()
							.id(datos.getParqueadero().getId())
							.build() : null)
					.build();
			casoUso.ejecutar(dominio);
			daoFactory.confirmarTransaccion();
		} catch (ERZParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al registrar el operario", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	public static void main(final String[] args) {
		try {
			var dto = new OperarioDTO.Builder()
					.tipoDocumentoIdentificacion(new TipoDocumentoIdentificacionDTO.Builder()
							.id(UUID.fromString("UUID_DE_TIPODOCUMENTO"))
							.build())
					.numeroIdentificacion("1037656789")
					.primerNombre("Carlos")
					.segundoNombre("Andres")
					.primerApellido("Lopez")
					.segundoApellido("Restrepo")
					.numeroTelefonico(3105551234L)
					.cargo(new CargoDTO.Builder()
							.id(UUID.fromString("UUID_DE_CARGO"))
							.build())
					.parqueadero(new ParqueaderoDTO.Builder()
							.id(UUID.fromString("UUID_DE_PARQUEADERO"))
							.build())
					.build();
			new RegistrarOperarioFachadaImpl().ejecutar(dto);
			System.out.println("Operario registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
