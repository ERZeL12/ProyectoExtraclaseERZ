package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.negocio.fachada.ciudad.ActualizarCiudadFachada;
import co.uco.erzparking.negocio.fachada.ciudad.ConsultarCiudadPorIdFachada;
import co.uco.erzparking.negocio.fachada.ciudad.ConsultarTodosCiudadsFachada;
import co.uco.erzparking.negocio.fachada.ciudad.QuitarCiudadFachada;
import co.uco.erzparking.negocio.fachada.ciudad.RegistrarCiudadFachada;
import co.uco.erzparking.negocio.fachada.ciudad.impl.ActualizarCiudadFachadaImpl;
import co.uco.erzparking.negocio.fachada.ciudad.impl.ConsultarCiudadPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.ciudad.impl.ConsultarTodosCiudadsFachadaImpl;
import co.uco.erzparking.negocio.fachada.ciudad.impl.QuitarCiudadFachadaImpl;
import co.uco.erzparking.negocio.fachada.ciudad.impl.RegistrarCiudadFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/ciudades")
public class CiudadControlador {

	@PostMapping
	public ResponseEntity<Respuesta<CiudadDTO>> registrar(@RequestBody CiudadDTO datos) {
		Respuesta<CiudadDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarCiudadFachada fachada = new RegistrarCiudadFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Ciudad registrado exitosamente");
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
	public ResponseEntity<Respuesta<CiudadDTO>> actualizar(@PathVariable UUID id, @RequestBody CiudadDTO datos) {
		Respuesta<CiudadDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new CiudadDTO.Builder().id(id).build();
			ActualizarCiudadFachada fachada = new ActualizarCiudadFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("Ciudad actualizado exitosamente");
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
	public ResponseEntity<Respuesta<CiudadDTO>> quitar(@PathVariable UUID id) {
		Respuesta<CiudadDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new CiudadDTO.Builder().id(id).build();
			QuitarCiudadFachada fachada = new QuitarCiudadFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Ciudad eliminado exitosamente");
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
	public ResponseEntity<Respuesta<CiudadDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<CiudadDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new CiudadDTO.Builder().id(id).build();
			ConsultarCiudadPorIdFachada fachada = new ConsultarCiudadPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Ciudad consultado exitosamente");
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
	public ResponseEntity<Respuesta<CiudadDTO>> consultarTodos() {
		Respuesta<CiudadDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosCiudadsFachada fachada = new ConsultarTodosCiudadsFachadaImpl();
			var resultados = fachada.ejecutar(new CiudadDTO.Builder().build());
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
