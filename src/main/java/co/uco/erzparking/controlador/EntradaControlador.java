package co.uco.erzparking.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.datos.dao.sql.sqlserver.EntradaSQLServerDAO;
import co.uco.erzparking.dto.EntradaDTO;
import co.uco.erzparking.negocio.fachada.entrada.RegistrarEntradaVehiculoFachada;
import co.uco.erzparking.negocio.fachada.entrada.impl.RegistrarEntradaVehiculoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

@RestController
@RequestMapping("/erzparking/v1/entradas")
public class EntradaControlador {

	@PostMapping
	public ResponseEntity<Respuesta<EntradaDTO>> registrarEntradaVehiculo(@RequestBody EntradaDTO entrada) {
		Respuesta<EntradaDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarEntradaVehiculoFachada fachada = new RegistrarEntradaVehiculoFachadaImpl();
			fachada.ejecutar(entrada);
			respuesta.agregarMensaje("Entrada del vehiculo registrada exitosamente");
		} catch (ERZParkingExcepcion excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje(excepcion.getMensajeUsuario());
			codigoEstado = HttpStatus.BAD_REQUEST;
		} catch (Exception excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje("Se presento un error inesperado. Por favor intente nuevamente");
			codigoEstado = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(respuesta, codigoEstado);
	}

	@GetMapping("/activa/{vehiculoId}")
	public ResponseEntity<Respuesta<EntradaDTO>> consultarEntradaActivaPorVehiculo(@PathVariable UUID vehiculoId) {
		Respuesta<EntradaDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			DAOFactory daoFactory = DAOFactory.getFactory();
			var entradaDAO = (EntradaSQLServerDAO) daoFactory.getEntradaDAO();
			var entradaActiva = entradaDAO.consultarEntradaActivaPorVehiculo(vehiculoId);
			daoFactory.cerrarConexion();
			if (entradaActiva != null) {
				var dto = new EntradaDTO.Builder()
						.id(entradaActiva.getId())
						.fechaHoraEntrada(entradaActiva.getFechaHoraEntrada())
						.build();
				respuesta.setDatos(List.of(dto));
				respuesta.agregarMensaje("Entrada activa encontrada");
			} else {
				respuesta.agregarMensaje("El vehiculo no tiene una entrada activa");
			}
		} catch (ERZParkingExcepcion excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje(excepcion.getMensajeUsuario());
			codigoEstado = HttpStatus.INTERNAL_SERVER_ERROR;
		} catch (Exception excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje("Se presento un error inesperado al consultar la entrada activa");
			codigoEstado = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(respuesta, codigoEstado);
	}

}
