package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.negocio.fachada.usuario.ActualizarUsuarioFachada;
import co.uco.erzparking.negocio.fachada.usuario.ConsultarTodosUsuariosFachada;
import co.uco.erzparking.negocio.fachada.usuario.ConsultarUsuarioPorIdFachada;
import co.uco.erzparking.negocio.fachada.usuario.QuitarUsuarioFachada;
import co.uco.erzparking.negocio.fachada.usuario.RegistrarUsuarioFachada;
import co.uco.erzparking.negocio.fachada.usuario.impl.ActualizarUsuarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuario.impl.ConsultarTodosUsuariosFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuario.impl.ConsultarUsuarioPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuario.impl.QuitarUsuarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuario.impl.RegistrarUsuarioFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/usuarios")
public class UsuarioControlador {

	@PostMapping
	public ResponseEntity<Respuesta<UsuarioDTO>> registrar(@RequestBody UsuarioDTO datos) {
		Respuesta<UsuarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarUsuarioFachada fachada = new RegistrarUsuarioFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Usuario registrado exitosamente");
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
	public ResponseEntity<Respuesta<UsuarioDTO>> actualizar(@PathVariable UUID id, @RequestBody UsuarioDTO datos) {
		Respuesta<UsuarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new UsuarioDTO.Builder().id(id).build();
			ActualizarUsuarioFachada fachada = new ActualizarUsuarioFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("Usuario actualizado exitosamente");
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
	public ResponseEntity<Respuesta<UsuarioDTO>> quitar(@PathVariable UUID id) {
		Respuesta<UsuarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new UsuarioDTO.Builder().id(id).build();
			QuitarUsuarioFachada fachada = new QuitarUsuarioFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Usuario eliminado exitosamente");
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
	public ResponseEntity<Respuesta<UsuarioDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<UsuarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new UsuarioDTO.Builder().id(id).build();
			ConsultarUsuarioPorIdFachada fachada = new ConsultarUsuarioPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Usuario consultado exitosamente");
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
	public ResponseEntity<Respuesta<UsuarioDTO>> consultarTodos() {
		Respuesta<UsuarioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosUsuariosFachada fachada = new ConsultarTodosUsuariosFachadaImpl();
			var resultados = fachada.ejecutar(new UsuarioDTO.Builder().build());
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
