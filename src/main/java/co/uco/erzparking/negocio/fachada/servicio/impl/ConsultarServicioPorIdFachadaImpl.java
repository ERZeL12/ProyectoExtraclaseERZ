package co.uco.erzparking.negocio.fachada.servicio.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.casouso.servicio.ConsultarServicioPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.servicio.impl.ConsultarServicioPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.negocio.fachada.servicio.ConsultarServicioPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarServicioPorIdFachadaImpl implements ConsultarServicioPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarServicioPorIdCasoUso casoUso;

	public ConsultarServicioPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarServicioPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public ServicioDTO ejecutar(final ServicioDTO datos) {
		try {

			var dominio = new ServicioDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			var tipoServicioDTO = resultado.getTipoServicio() != null
					? new TipoServicioDTO.Builder()
							.id(resultado.getTipoServicio().getId())
							.nombreServicio(resultado.getTipoServicio().getNombreServicio())
							.descripcion(resultado.getTipoServicio().getDescripcion())
							.build()
					: null;
			var parqueaderoDTO = resultado.getParqueadero() != null
					? new ParqueaderoDTO.Builder()
							.id(resultado.getParqueadero().getId())
							.nombreEstablecimiento(resultado.getParqueadero().getNombreEstablecimiento())
							.build()
					: null;
				return new ServicioDTO.Builder()
					.id(resultado.getId())
					.nombreServicio(resultado.getNombreServicio())
					.tipoServicio(tipoServicioDTO)
					.parqueadero(parqueaderoDTO)
					.build();
		} catch (ERZParkingExcepcion excepcion) {
						throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	public static void main(final String[] args) {
		try {
			var filtro = new ServicioDTO.Builder()
					.id(UUID.fromString("54d2035f-120c-47e4-a54d-06b149349c11"))
					.build();
			var resultado = new ConsultarServicioPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("Servicio consultado: id=" + resultado.getId()
					+ ", nombre=" + resultado.getNombreServicio()
					+ ", tipoServicio=" + (resultado.getTipoServicio() != null ? resultado.getTipoServicio().getNombreServicio() : "(sin tipo)"));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
