package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.ConsultarTodosUsuarioVehiculosFachada;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.ConsultarUsuarioVehiculoPorIdFachada;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.QuitarUsuarioVehiculoFachada;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.RegistrarUsuarioVehiculoFachada;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.impl.ConsultarTodosUsuarioVehiculosFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.impl.ConsultarUsuarioVehiculoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.impl.QuitarUsuarioVehiculoFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.impl.RegistrarUsuarioVehiculoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/usuariosvehiculo")
public class UsuarioVehiculoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<UsuarioVehiculoDTO>> registrar(@RequestBody UsuarioVehiculoDTO datos) {
		Respuesta<UsuarioVehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarUsuarioVehiculoFachada fachada = new RegistrarUsuarioVehiculoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("UsuarioVehiculo registrado exitosamente");
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
	public ResponseEntity<Respuesta<UsuarioVehiculoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<UsuarioVehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new UsuarioVehiculoDTO.Builder().id(id).build();
			QuitarUsuarioVehiculoFachada fachada = new QuitarUsuarioVehiculoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("UsuarioVehiculo eliminado exitosamente");
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
	public ResponseEntity<Respuesta<UsuarioVehiculoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<UsuarioVehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new UsuarioVehiculoDTO.Builder().id(id).build();
			ConsultarUsuarioVehiculoPorIdFachada fachada = new ConsultarUsuarioVehiculoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("UsuarioVehiculo consultado exitosamente");
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
	public ResponseEntity<Respuesta<UsuarioVehiculoDTO>> consultarTodos() {
		Respuesta<UsuarioVehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosUsuarioVehiculosFachada fachada = new ConsultarTodosUsuarioVehiculosFachadaImpl();
			var resultados = fachada.ejecutar(new UsuarioVehiculoDTO.Builder().build());
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
