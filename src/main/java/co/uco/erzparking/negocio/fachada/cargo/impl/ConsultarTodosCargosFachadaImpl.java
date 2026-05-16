package co.uco.erzparking.negocio.fachada.cargo.impl;

import java.util.List;
import java.util.stream.Collectors;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.casouso.cargo.ConsultarTodosCargosCasoUso;
import co.uco.erzparking.negocio.casouso.cargo.impl.ConsultarTodosCargosCasoUsoImpl;
import co.uco.erzparking.negocio.dominio.CargoDominio;
import co.uco.erzparking.negocio.fachada.cargo.ConsultarTodosCargosFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class ConsultarTodosCargosFachadaImpl implements ConsultarTodosCargosFachada {

	private DAOFactory daoFactory;
	private ConsultarTodosCargosCasoUso casoUso;

	public ConsultarTodosCargosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarTodosCargosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<CargoDTO> ejecutar(final CargoDTO datos) {
		try {

			var dominio = new CargoDominio.Builder().id(datos.getId()).nombreCargo(datos.getNombreCargo()).build();
			var resultado = casoUso.ejecutar(dominio);
			return resultado.stream().map(this::mapearADto).collect(Collectors.toList());
		} catch (ERZParkingExcepcion excepcion) {
			throw excepcion;
		} catch (Exception excepcion) {
			throw ERZParkingExcepcion.crear(excepcion, "Error inesperado al procesar la solicitud", excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	private CargoDTO mapearADto(final CargoDominio d) {
		var parqueaderoDTO = new ParqueaderoDTO.Builder()
				.id(d.getParqueadero().getId())
				.nombreEstablecimiento(d.getParqueadero().getNombreEstablecimiento())
				.build();
		return new CargoDTO.Builder()
				.id(d.getId())
				.nombreCargo(d.getNombreCargo())
				.descripcion(d.getDescripcion())
				.parqueadero(parqueaderoDTO)
				.build();
	}

}
