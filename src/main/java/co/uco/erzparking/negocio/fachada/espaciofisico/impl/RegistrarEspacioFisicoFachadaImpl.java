package co.uco.erzparking.negocio.fachada.espaciofisico.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.espaciofisico.RegistrarEspacioFisicoCasoUso;
import co.uco.erzparking.negocio.casouso.espaciofisico.impl.RegistrarEspacioFisicoCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.EstadoEspacioFisicoDominio;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.negocio.dominio.ZonaParqueaderoDominio;
import co.uco.erzparking.negocio.fachada.espaciofisico.RegistrarEspacioFisicoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarEspacioFisicoFachadaImpl implements RegistrarEspacioFisicoFachada {

	private DAOFactory daoFactory;
	private RegistrarEspacioFisicoCasoUso casoUso;

	public RegistrarEspacioFisicoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarEspacioFisicoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final EspacioFisicoDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = new EspacioFisicoDominio.Builder()
					.id(datos.getId())
					.numeroEspacioFisico(datos.getNumeroEspacioFisico())
					.tipoServicio(datos.getTipoServicio() != null ? new TipoServicioDominio.Builder()
							.id(datos.getTipoServicio().getId())
							.build() : null)
					.estadoEspacioFisico(datos.getEstadoEspacioFisico() != null ? new EstadoEspacioFisicoDominio.Builder()
							.id(datos.getEstadoEspacioFisico().getId())
							.build() : null)
					.zonaEspacioFisico(datos.getZonaEspacioFisico() != null ? new ZonaParqueaderoDominio.Builder()
							.id(datos.getZonaEspacioFisico().getId())
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
			var dto = new EspacioFisicoDTO.Builder()
					.tipoServicio(new TipoServicioDTO.Builder()
							.id(UUID.fromString("625a474a-1f5a-4590-860f-9635d49a9a23"))
							.build())
					.estadoEspacioFisico(new EstadoEspacioFisicoDTO.Builder()
							.id(UUID.fromString("75db75b1-3f27-4e47-bf10-4f2e42764cf8"))
							.build())
					.zonaEspacioFisico(new ZonaParqueaderoDTO.Builder()
							.id(UUID.fromString("f71b7dc0-306a-4111-a652-3ebb9a619317"))
							.build())
					.parqueadero(new ParqueaderoDTO.Builder()
							.id(UUID.fromString("6f34728b-d0ee-4440-bd24-38b29b2ed427"))
							.build())
					.build();
			new RegistrarEspacioFisicoFachadaImpl().ejecutar(dto);
			System.out.println("EspacioFisico registrado exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
