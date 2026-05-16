package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.ConsultarEstadoEspacioFisicoPorIdFachada;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.ConsultarTodosEstadoEspacioFisicosFachada;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.QuitarEstadoEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.RegistrarEstadoEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl.ConsultarEstadoEspacioFisicoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl.ConsultarTodosEstadoEspacioFisicosFachadaImpl;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl.QuitarEstadoEspacioFisicoFachadaImpl;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl.RegistrarEstadoEspacioFisicoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/estadosespaciofisico")
public class EstadoEspacioFisicoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<EstadoEspacioFisicoDTO>> registrar(@RequestBody EstadoEspacioFisicoDTO datos) {
		Respuesta<EstadoEspacioFisicoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarEstadoEspacioFisicoFachada fachada = new RegistrarEstadoEspacioFisicoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("EstadoEspacioFisico registrado exitosamente");
		} catch (ERZParkingExcepcion excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje(excepcion.getMensajeUsuario());
			codigoEstado = HttpStatus.BAD_REQUEST;
		} catch (Exception excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje("Se presento un error inesperado");
			codigoEstado = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(respuesta, codigoEstado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Respuesta<EstadoEspacioFisicoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<EstadoEspacioFisicoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new EstadoEspacioFisicoDTO.Builder().id(id).build();
			QuitarEstadoEspacioFisicoFachada fachada = new QuitarEstadoEspacioFisicoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("EstadoEspacioFisico eliminado exitosamente");
		} catch (ERZParkingExcepcion excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje(excepcion.getMensajeUsuario());
			codigoEstado = HttpStatus.BAD_REQUEST;
		} catch (Exception excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje("Se presento un error inesperado");
			codigoEstado = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(respuesta, codigoEstado);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Respuesta<EstadoEspacioFisicoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<EstadoEspacioFisicoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new EstadoEspacioFisicoDTO.Builder().id(id).build();
			ConsultarEstadoEspacioFisicoPorIdFachada fachada = new ConsultarEstadoEspacioFisicoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("EstadoEspacioFisico consultado exitosamente");
		} catch (ERZParkingExcepcion excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje(excepcion.getMensajeUsuario());
			codigoEstado = HttpStatus.BAD_REQUEST;
		} catch (Exception excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje("Se presento un error inesperado");
			codigoEstado = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(respuesta, codigoEstado);
	}

	@GetMapping
	public ResponseEntity<Respuesta<EstadoEspacioFisicoDTO>> consultarTodos() {
		Respuesta<EstadoEspacioFisicoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosEstadoEspacioFisicosFachada fachada = new ConsultarTodosEstadoEspacioFisicosFachadaImpl();
			var resultados = fachada.ejecutar(new EstadoEspacioFisicoDTO.Builder().build());
			respuesta.setDatos(resultados);
			respuesta.agregarMensaje("Consulta exitosa");
		} catch (ERZParkingExcepcion excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje(excepcion.getMensajeUsuario());
			codigoEstado = HttpStatus.BAD_REQUEST;
		} catch (Exception excepcion) {
			respuesta = Respuesta.crearRespuestaFallida();
			respuesta.agregarMensaje("Se presento un error inesperado");
			codigoEstado = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(respuesta, codigoEstado);
	}

}
