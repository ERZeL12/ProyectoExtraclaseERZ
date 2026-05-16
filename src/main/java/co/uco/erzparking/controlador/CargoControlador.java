package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.negocio.fachada.cargo.ActualizarCargoFachada;
import co.uco.erzparking.negocio.fachada.cargo.ConsultarCargoPorIdFachada;
import co.uco.erzparking.negocio.fachada.cargo.ConsultarTodosCargosFachada;
import co.uco.erzparking.negocio.fachada.cargo.QuitarCargoFachada;
import co.uco.erzparking.negocio.fachada.cargo.RegistrarCargoFachada;
import co.uco.erzparking.negocio.fachada.cargo.impl.ActualizarCargoFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.ConsultarCargoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.ConsultarTodosCargosFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.QuitarCargoFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.RegistrarCargoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/cargos")
public class CargoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<CargoDTO>> registrar(@RequestBody CargoDTO datos) {
		Respuesta<CargoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarCargoFachada fachada = new RegistrarCargoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Cargo registrado exitosamente");
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
	public ResponseEntity<Respuesta<CargoDTO>> actualizar(@PathVariable UUID id, @RequestBody CargoDTO datos) {
		Respuesta<CargoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new CargoDTO.Builder()
					.id(id)
					.nombreCargo(datos.getNombreCargo())
					.descripcion(datos.getDescripcion())
					.build();
			ActualizarCargoFachada fachada = new ActualizarCargoFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("Cargo actualizado exitosamente");
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
	public ResponseEntity<Respuesta<CargoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<CargoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new CargoDTO.Builder().id(id).build();
			QuitarCargoFachada fachada = new QuitarCargoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Cargo eliminado exitosamente");
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
	public ResponseEntity<Respuesta<CargoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<CargoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new CargoDTO.Builder().id(id).build();
			ConsultarCargoPorIdFachada fachada = new ConsultarCargoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Cargo consultado exitosamente");
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
	public ResponseEntity<Respuesta<CargoDTO>> consultarTodos() {
		Respuesta<CargoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosCargosFachada fachada = new ConsultarTodosCargosFachadaImpl();
			var resultados = fachada.ejecutar(new CargoDTO.Builder().build());
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
