package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.MetodoPagoDTO;
import co.uco.erzparking.negocio.fachada.metodopago.ActualizarMetodoPagoFachada;
import co.uco.erzparking.negocio.fachada.metodopago.ConsultarMetodoPagoPorIdFachada;
import co.uco.erzparking.negocio.fachada.metodopago.ConsultarTodosMetodoPagosFachada;
import co.uco.erzparking.negocio.fachada.metodopago.QuitarMetodoPagoFachada;
import co.uco.erzparking.negocio.fachada.metodopago.RegistrarMetodoPagoFachada;
import co.uco.erzparking.negocio.fachada.metodopago.impl.ActualizarMetodoPagoFachadaImpl;
import co.uco.erzparking.negocio.fachada.metodopago.impl.ConsultarMetodoPagoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.metodopago.impl.ConsultarTodosMetodoPagosFachadaImpl;
import co.uco.erzparking.negocio.fachada.metodopago.impl.QuitarMetodoPagoFachadaImpl;
import co.uco.erzparking.negocio.fachada.metodopago.impl.RegistrarMetodoPagoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/metodospago")
public class MetodoPagoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<MetodoPagoDTO>> registrar(@RequestBody MetodoPagoDTO datos) {
		Respuesta<MetodoPagoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarMetodoPagoFachada fachada = new RegistrarMetodoPagoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("MetodoPago registrado exitosamente");
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
	public ResponseEntity<Respuesta<MetodoPagoDTO>> actualizar(@PathVariable UUID id, @RequestBody MetodoPagoDTO datos) {
		Respuesta<MetodoPagoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new MetodoPagoDTO.Builder().id(id).build();
			ActualizarMetodoPagoFachada fachada = new ActualizarMetodoPagoFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("MetodoPago actualizado exitosamente");
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
	public ResponseEntity<Respuesta<MetodoPagoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<MetodoPagoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new MetodoPagoDTO.Builder().id(id).build();
			QuitarMetodoPagoFachada fachada = new QuitarMetodoPagoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("MetodoPago eliminado exitosamente");
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
	public ResponseEntity<Respuesta<MetodoPagoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<MetodoPagoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new MetodoPagoDTO.Builder().id(id).build();
			ConsultarMetodoPagoPorIdFachada fachada = new ConsultarMetodoPagoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("MetodoPago consultado exitosamente");
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
	public ResponseEntity<Respuesta<MetodoPagoDTO>> consultarTodos() {
		Respuesta<MetodoPagoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosMetodoPagosFachada fachada = new ConsultarTodosMetodoPagosFachadaImpl();
			var resultados = fachada.ejecutar(new MetodoPagoDTO.Builder().build());
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
