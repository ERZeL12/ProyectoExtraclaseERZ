package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.negocio.fachada.servicio.ActualizarServicioFachada;
import co.uco.erzparking.negocio.fachada.servicio.ConsultarServicioPorIdFachada;
import co.uco.erzparking.negocio.fachada.servicio.ConsultarTodosServiciosFachada;
import co.uco.erzparking.negocio.fachada.servicio.QuitarServicioFachada;
import co.uco.erzparking.negocio.fachada.servicio.RegistrarServicioFachada;
import co.uco.erzparking.negocio.fachada.servicio.impl.ActualizarServicioFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.ConsultarServicioPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.ConsultarTodosServiciosFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.QuitarServicioFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.RegistrarServicioFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/servicios")
public class ServicioControlador {

	@PostMapping
	public ResponseEntity<Respuesta<ServicioDTO>> registrar(@RequestBody ServicioDTO datos) {
		Respuesta<ServicioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarServicioFachada fachada = new RegistrarServicioFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Servicio registrado exitosamente");
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

	@PutMapping("/{id}")
	public ResponseEntity<Respuesta<ServicioDTO>> actualizar(@PathVariable UUID id, @RequestBody ServicioDTO datos) {
		Respuesta<ServicioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new ServicioDTO.Builder().id(id).build();
			ActualizarServicioFachada fachada = new ActualizarServicioFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("Servicio actualizado exitosamente");
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
	public ResponseEntity<Respuesta<ServicioDTO>> quitar(@PathVariable UUID id) {
		Respuesta<ServicioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new ServicioDTO.Builder().id(id).build();
			QuitarServicioFachada fachada = new QuitarServicioFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Servicio eliminado exitosamente");
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
	public ResponseEntity<Respuesta<ServicioDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<ServicioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new ServicioDTO.Builder().id(id).build();
			ConsultarServicioPorIdFachada fachada = new ConsultarServicioPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Servicio consultado exitosamente");
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
	public ResponseEntity<Respuesta<ServicioDTO>> consultarTodos() {
		Respuesta<ServicioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosServiciosFachada fachada = new ConsultarTodosServiciosFachadaImpl();
			var resultados = fachada.ejecutar(new ServicioDTO.Builder().build());
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
