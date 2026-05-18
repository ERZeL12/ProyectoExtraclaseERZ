package co.uco.erzparking.negocio.fachada.servicio.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.casouso.servicio.RegistrarServicioCasoUso;
import co.uco.erzparking.negocio.casouso.servicio.impl.RegistrarServicioCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.negocio.fachada.servicio.RegistrarServicioFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarServicioFachadaImpl implements RegistrarServicioFachada {

	private DAOFactory daoFactory;
	private RegistrarServicioCasoUso casoUso;

	public RegistrarServicioFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarServicioCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final ServicioDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new ServicioDominio.Builder()
					.id(datos.getId())
					.nombreServicio(datos.getNombreServicio())
					.tipoServicio(datos.getTipoServicio() != null ? new TipoServicioDominio.Builder()
							.id(datos.getTipoServicio().getId())
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
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	public static void main(final String[] args) {
		try {
			var dto = new ServicioDTO.Builder()
					.nombreServicio("Parqueo")
					.tipoServicio(new TipoServicioDTO.Builder()
							.id(UUID.fromString("625a474a-1f5a-4590-860f-9635d49a9a23"))
							.build())
					.parqueadero(new ParqueaderoDTO.Builder()
							.id(UUID.fromString("6f34728b-d0ee-4440-bd24-38b29b2ed427"))
							.build())
					.build();
			new RegistrarServicioFachadaImpl().ejecutar(dto);
			System.out.println("Servicio registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
