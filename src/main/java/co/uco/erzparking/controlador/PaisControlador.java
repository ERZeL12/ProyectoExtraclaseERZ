package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.fachada.pais.ActualizarPaisFachada;
import co.uco.erzparking.negocio.fachada.pais.ConsultarPaisPorIdFachada;
import co.uco.erzparking.negocio.fachada.pais.ConsultarTodosPaissFachada;
import co.uco.erzparking.negocio.fachada.pais.QuitarPaisFachada;
import co.uco.erzparking.negocio.fachada.pais.RegistrarPaisFachada;
import co.uco.erzparking.negocio.fachada.pais.impl.ActualizarPaisFachadaImpl;
import co.uco.erzparking.negocio.fachada.pais.impl.ConsultarPaisPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.pais.impl.ConsultarTodosPaissFachadaImpl;
import co.uco.erzparking.negocio.fachada.pais.impl.QuitarPaisFachadaImpl;
import co.uco.erzparking.negocio.fachada.pais.impl.RegistrarPaisFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/paises")
public class PaisControlador {

	@PostMapping
	public ResponseEntity<Respuesta<PaisDTO>> registrar(@RequestBody PaisDTO datos) {
		Respuesta<PaisDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarPaisFachada fachada = new RegistrarPaisFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Pais registrado exitosamente");
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
	public ResponseEntity<Respuesta<PaisDTO>> actualizar(@PathVariable UUID id, @RequestBody PaisDTO datos) {
		Respuesta<PaisDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new PaisDTO.Builder().id(id).build();
			ActualizarPaisFachada fachada = new ActualizarPaisFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("Pais actualizado exitosamente");
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
	public ResponseEntity<Respuesta<PaisDTO>> quitar(@PathVariable UUID id) {
		Respuesta<PaisDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new PaisDTO.Builder().id(id).build();
			QuitarPaisFachada fachada = new QuitarPaisFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Pais eliminado exitosamente");
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
	public ResponseEntity<Respuesta<PaisDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<PaisDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new PaisDTO.Builder().id(id).build();
			ConsultarPaisPorIdFachada fachada = new ConsultarPaisPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Pais consultado exitosamente");
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
	public ResponseEntity<Respuesta<PaisDTO>> consultarTodos() {
		Respuesta<PaisDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosPaissFachada fachada = new ConsultarTodosPaissFachadaImpl();
			var resultados = fachada.ejecutar(new PaisDTO.Builder().build());
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
