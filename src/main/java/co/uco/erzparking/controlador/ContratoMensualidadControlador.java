package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.negocio.fachada.contratomensualidad.ConsultarContratoMensualidadPorIdFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.ConsultarTodosContratoMensualidadsFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.FinalizarContratoMensualidadFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.RegistrarContratoMensualidadFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.ConsultarContratoMensualidadPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.ConsultarTodosContratoMensualidadsFachadaImpl;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.FinalizarContratoMensualidadFachadaImpl;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.RegistrarContratoMensualidadFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/contratosmensualidad")
public class ContratoMensualidadControlador {

	@PostMapping
	public ResponseEntity<Respuesta<ContratoMensualidadDTO>> registrar(@RequestBody ContratoMensualidadDTO datos) {
		Respuesta<ContratoMensualidadDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarContratoMensualidadFachada fachada = new RegistrarContratoMensualidadFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("ContratoMensualidad registrado exitosamente");
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

	@PutMapping("/finalizar/{id}")
	public ResponseEntity<Respuesta<ContratoMensualidadDTO>> finalizar(@PathVariable UUID id) {
		Respuesta<ContratoMensualidadDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new ContratoMensualidadDTO.Builder().id(id).build();
			FinalizarContratoMensualidadFachada fachada = new FinalizarContratoMensualidadFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("ContratoMensualidad finalizado exitosamente");
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
	public ResponseEntity<Respuesta<ContratoMensualidadDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<ContratoMensualidadDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new ContratoMensualidadDTO.Builder().id(id).build();
			ConsultarContratoMensualidadPorIdFachada fachada = new ConsultarContratoMensualidadPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("ContratoMensualidad consultado exitosamente");
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
	public ResponseEntity<Respuesta<ContratoMensualidadDTO>> consultarTodos() {
		Respuesta<ContratoMensualidadDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosContratoMensualidadsFachada fachada = new ConsultarTodosContratoMensualidadsFachadaImpl();
			var resultados = fachada.ejecutar(new ContratoMensualidadDTO.Builder().build());
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
