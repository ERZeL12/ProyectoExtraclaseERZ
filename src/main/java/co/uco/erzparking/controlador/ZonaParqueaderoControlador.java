package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ActualizarZonaParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ConsultarTodosZonaParqueaderosFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ConsultarZonaParqueaderoPorIdFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.QuitarZonaParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.RegistrarZonaParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.ActualizarZonaParqueaderoFachadaImpl;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.ConsultarTodosZonaParqueaderosFachadaImpl;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.ConsultarZonaParqueaderoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.QuitarZonaParqueaderoFachadaImpl;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.RegistrarZonaParqueaderoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/zonasparqueadero")
public class ZonaParqueaderoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<ZonaParqueaderoDTO>> registrar(@RequestBody ZonaParqueaderoDTO datos) {
		Respuesta<ZonaParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarZonaParqueaderoFachada fachada = new RegistrarZonaParqueaderoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("ZonaParqueadero registrado exitosamente");
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
	public ResponseEntity<Respuesta<ZonaParqueaderoDTO>> actualizar(@PathVariable UUID id, @RequestBody ZonaParqueaderoDTO datos) {
		Respuesta<ZonaParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new ZonaParqueaderoDTO.Builder().id(id).build();
			ActualizarZonaParqueaderoFachada fachada = new ActualizarZonaParqueaderoFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("ZonaParqueadero actualizado exitosamente");
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
	public ResponseEntity<Respuesta<ZonaParqueaderoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<ZonaParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new ZonaParqueaderoDTO.Builder().id(id).build();
			QuitarZonaParqueaderoFachada fachada = new QuitarZonaParqueaderoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("ZonaParqueadero eliminado exitosamente");
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
	public ResponseEntity<Respuesta<ZonaParqueaderoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<ZonaParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new ZonaParqueaderoDTO.Builder().id(id).build();
			ConsultarZonaParqueaderoPorIdFachada fachada = new ConsultarZonaParqueaderoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("ZonaParqueadero consultado exitosamente");
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
	public ResponseEntity<Respuesta<ZonaParqueaderoDTO>> consultarTodos() {
		Respuesta<ZonaParqueaderoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosZonaParqueaderosFachada fachada = new ConsultarTodosZonaParqueaderosFachadaImpl();
			var resultados = fachada.ejecutar(new ZonaParqueaderoDTO.Builder().build());
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
