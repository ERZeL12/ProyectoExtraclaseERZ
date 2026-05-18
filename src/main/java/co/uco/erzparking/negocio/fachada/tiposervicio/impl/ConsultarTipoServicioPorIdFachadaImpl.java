package co.uco.erzparking.negocio.fachada.tiposervicio.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.casouso.tiposervicio.ConsultarTipoServicioPorIdCasoUso;
import co.uco.erzparking.negocio.casouso.tiposervicio.impl.ConsultarTipoServicioPorIdCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.TipoServicioDominio;
import co.uco.erzparking.negocio.fachada.tiposervicio.ConsultarTipoServicioPorIdFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTipoServicioPorIdFachadaImpl implements ConsultarTipoServicioPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarTipoServicioPorIdCasoUso casoUso;

	public ConsultarTipoServicioPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTipoServicioPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public TipoServicioDTO ejecutar(final TipoServicioDTO datos) {
		try {

			var dominio = new TipoServicioDominio.Builder().id(datos.getId()).build();
			var resultado = casoUso.ejecutar(dominio);
			return new TipoServicioDTO.Builder()
					.id(resultado.getId())
					.nombreServicio(resultado.getNombreServicio())
					.descripcion(resultado.getDescripcion())
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
			var filtro = new TipoServicioDTO.Builder()
					.id(UUID.fromString("625a474a-1f5a-4590-860f-9635d49a9a23"))
					.build();
			var resultado = new ConsultarTipoServicioPorIdFachadaImpl().ejecutar(filtro);
			System.out.println("TipoServicio consultado: id=" + resultado.getId() + ", nombre=" + resultado.getNombreServicio());
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
