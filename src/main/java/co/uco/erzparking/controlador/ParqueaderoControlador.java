package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.fachada.parqueadero.ActualizarParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.ConsultarParqueaderoPorIdFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.ConsultarTodosParqueaderosFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.QuitarParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.RegistrarParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.ActualizarParqueaderoFachadaImpl;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.ConsultarParqueaderoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.ConsultarTodosParqueaderosFachadaImpl;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.QuitarParqueaderoFachadaImpl;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.RegistrarParqueaderoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/parqueaderos")
public class ParqueaderoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<ParqueaderoDTO>> registrar(@RequestBody ParqueaderoDTO datos) {
		Respuesta<ParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarParqueaderoFachada fachada = new RegistrarParqueaderoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Parqueadero registrado exitosamente");
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
	public ResponseEntity<Respuesta<ParqueaderoDTO>> actualizar(@PathVariable UUID id, @RequestBody ParqueaderoDTO datos) {
		Respuesta<ParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new ParqueaderoDTO.Builder().id(id).build();
			ActualizarParqueaderoFachada fachada = new ActualizarParqueaderoFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("Parqueadero actualizado exitosamente");
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
	public ResponseEntity<Respuesta<ParqueaderoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<ParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new ParqueaderoDTO.Builder().id(id).build();
			QuitarParqueaderoFachada fachada = new QuitarParqueaderoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Parqueadero eliminado exitosamente");
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
	public ResponseEntity<Respuesta<ParqueaderoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<ParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new ParqueaderoDTO.Builder().id(id).build();
			ConsultarParqueaderoPorIdFachada fachada = new ConsultarParqueaderoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Parqueadero consultado exitosamente");
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
	public ResponseEntity<Respuesta<ParqueaderoDTO>> consultarTodos() {
		Respuesta<ParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosParqueaderosFachada fachada = new ConsultarTodosParqueaderosFachadaImpl();
			var resultados = fachada.ejecutar(new ParqueaderoDTO.Builder().build());
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
