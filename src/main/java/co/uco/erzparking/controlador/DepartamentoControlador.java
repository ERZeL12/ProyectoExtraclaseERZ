package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.negocio.fachada.departamento.ActualizarDepartamentoFachada;
import co.uco.erzparking.negocio.fachada.departamento.ConsultarDepartamentoPorIdFachada;
import co.uco.erzparking.negocio.fachada.departamento.ConsultarTodosDepartamentosFachada;
import co.uco.erzparking.negocio.fachada.departamento.QuitarDepartamentoFachada;
import co.uco.erzparking.negocio.fachada.departamento.RegistrarDepartamentoFachada;
import co.uco.erzparking.negocio.fachada.departamento.impl.ActualizarDepartamentoFachadaImpl;
import co.uco.erzparking.negocio.fachada.departamento.impl.ConsultarDepartamentoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.departamento.impl.ConsultarTodosDepartamentosFachadaImpl;
import co.uco.erzparking.negocio.fachada.departamento.impl.QuitarDepartamentoFachadaImpl;
import co.uco.erzparking.negocio.fachada.departamento.impl.RegistrarDepartamentoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/departamentos")
public class DepartamentoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<DepartamentoDTO>> registrar(@RequestBody DepartamentoDTO datos) {
		Respuesta<DepartamentoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarDepartamentoFachada fachada = new RegistrarDepartamentoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Departamento registrado exitosamente");
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
	public ResponseEntity<Respuesta<DepartamentoDTO>> actualizar(@PathVariable UUID id, @RequestBody DepartamentoDTO datos) {
		Respuesta<DepartamentoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new DepartamentoDTO.Builder().id(id).build();
			ActualizarDepartamentoFachada fachada = new ActualizarDepartamentoFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("Departamento actualizado exitosamente");
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
	public ResponseEntity<Respuesta<DepartamentoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<DepartamentoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new DepartamentoDTO.Builder().id(id).build();
			QuitarDepartamentoFachada fachada = new QuitarDepartamentoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Departamento eliminado exitosamente");
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
	public ResponseEntity<Respuesta<DepartamentoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<DepartamentoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new DepartamentoDTO.Builder().id(id).build();
			ConsultarDepartamentoPorIdFachada fachada = new ConsultarDepartamentoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Departamento consultado exitosamente");
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
	public ResponseEntity<Respuesta<DepartamentoDTO>> consultarTodos() {
		Respuesta<DepartamentoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosDepartamentosFachada fachada = new ConsultarTodosDepartamentosFachadaImpl();
			var resultados = fachada.ejecutar(new DepartamentoDTO.Builder().build());
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
