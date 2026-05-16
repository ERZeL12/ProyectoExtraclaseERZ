package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.fachada.vehiculo.ActualizarVehiculoFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.ConsultarTodosVehiculosFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.ConsultarVehiculoPorIdFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.QuitarVehiculoFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.RegistrarVehiculoFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.ActualizarVehiculoFachadaImpl;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.ConsultarTodosVehiculosFachadaImpl;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.ConsultarVehiculoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.QuitarVehiculoFachadaImpl;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.RegistrarVehiculoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/vehiculos")
public class VehiculoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<VehiculoDTO>> registrar(@RequestBody VehiculoDTO datos) {
		Respuesta<VehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarVehiculoFachada fachada = new RegistrarVehiculoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Vehiculo registrado exitosamente");
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
	public ResponseEntity<Respuesta<VehiculoDTO>> actualizar(@PathVariable UUID id, @RequestBody VehiculoDTO datos) {
		Respuesta<VehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new VehiculoDTO.Builder().id(id).build();
			ActualizarVehiculoFachada fachada = new ActualizarVehiculoFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("Vehiculo actualizado exitosamente");
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
	public ResponseEntity<Respuesta<VehiculoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<VehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new VehiculoDTO.Builder().id(id).build();
			QuitarVehiculoFachada fachada = new QuitarVehiculoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Vehiculo eliminado exitosamente");
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
	public ResponseEntity<Respuesta<VehiculoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<VehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new VehiculoDTO.Builder().id(id).build();
			ConsultarVehiculoPorIdFachada fachada = new ConsultarVehiculoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Vehiculo consultado exitosamente");
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
	public ResponseEntity<Respuesta<VehiculoDTO>> consultarTodos() {
		Respuesta<VehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosVehiculosFachada fachada = new ConsultarTodosVehiculosFachadaImpl();
			var resultados = fachada.ejecutar(new VehiculoDTO.Builder().build());
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
