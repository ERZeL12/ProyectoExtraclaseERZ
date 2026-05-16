package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.negocio.fachada.operario.ActualizarOperarioFachada;
import co.uco.erzparking.negocio.fachada.operario.ConsultarOperarioPorIdFachada;
import co.uco.erzparking.negocio.fachada.operario.ConsultarTodosOperariosFachada;
import co.uco.erzparking.negocio.fachada.operario.QuitarOperarioFachada;
import co.uco.erzparking.negocio.fachada.operario.RegistrarOperarioFachada;
import co.uco.erzparking.negocio.fachada.operario.impl.ActualizarOperarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.ConsultarOperarioPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.ConsultarTodosOperariosFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.QuitarOperarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.RegistrarOperarioFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/operarios")
public class OperarioControlador {

	@PostMapping
	public ResponseEntity<Respuesta<OperarioDTO>> registrar(@RequestBody OperarioDTO datos) {
		Respuesta<OperarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarOperarioFachada fachada = new RegistrarOperarioFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Operario registrado exitosamente");
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
	public ResponseEntity<Respuesta<OperarioDTO>> actualizar(@PathVariable UUID id, @RequestBody OperarioDTO datos) {
		Respuesta<OperarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new OperarioDTO.Builder().id(id).build();
			ActualizarOperarioFachada fachada = new ActualizarOperarioFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("Operario actualizado exitosamente");
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
	public ResponseEntity<Respuesta<OperarioDTO>> quitar(@PathVariable UUID id) {
		Respuesta<OperarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new OperarioDTO.Builder().id(id).build();
			QuitarOperarioFachada fachada = new QuitarOperarioFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Operario eliminado exitosamente");
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
	public ResponseEntity<Respuesta<OperarioDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<OperarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new OperarioDTO.Builder().id(id).build();
			ConsultarOperarioPorIdFachada fachada = new ConsultarOperarioPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Operario consultado exitosamente");
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
	public ResponseEntity<Respuesta<OperarioDTO>> consultarTodos() {
		Respuesta<OperarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosOperariosFachada fachada = new ConsultarTodosOperariosFachadaImpl();
			var resultados = fachada.ejecutar(new OperarioDTO.Builder().build());
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
