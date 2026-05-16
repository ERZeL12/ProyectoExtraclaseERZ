package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.negocio.fachada.tarifa.ConsultarTarifaPorIdFachada;
import co.uco.erzparking.negocio.fachada.tarifa.ConsultarTodosTarifasFachada;
import co.uco.erzparking.negocio.fachada.tarifa.FinalizarVigenciaTarifaFachada;
import co.uco.erzparking.negocio.fachada.tarifa.RegistrarTarifaFachada;
import co.uco.erzparking.negocio.fachada.tarifa.impl.ConsultarTarifaPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.tarifa.impl.ConsultarTodosTarifasFachadaImpl;
import co.uco.erzparking.negocio.fachada.tarifa.impl.FinalizarVigenciaTarifaFachadaImpl;
import co.uco.erzparking.negocio.fachada.tarifa.impl.RegistrarTarifaFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/tarifas")
public class TarifaControlador {

	@PostMapping
	public ResponseEntity<Respuesta<TarifaDTO>> registrar(@RequestBody TarifaDTO datos) {
		Respuesta<TarifaDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarTarifaFachada fachada = new RegistrarTarifaFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Tarifa registrado exitosamente");
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
	public ResponseEntity<Respuesta<TarifaDTO>> finalizarVigencia(@PathVariable UUID id) {
		Respuesta<TarifaDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new TarifaDTO.Builder().id(id).build();
			FinalizarVigenciaTarifaFachada fachada = new FinalizarVigenciaTarifaFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("Tarifa finalizado exitosamente");
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
	public ResponseEntity<Respuesta<TarifaDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<TarifaDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new TarifaDTO.Builder().id(id).build();
			ConsultarTarifaPorIdFachada fachada = new ConsultarTarifaPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("Tarifa consultado exitosamente");
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
	public ResponseEntity<Respuesta<TarifaDTO>> consultarTodos() {
		Respuesta<TarifaDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosTarifasFachada fachada = new ConsultarTodosTarifasFachadaImpl();
			var resultados = fachada.ejecutar(new TarifaDTO.Builder().build());
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
