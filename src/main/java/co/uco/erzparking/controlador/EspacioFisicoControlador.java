package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.negocio.fachada.espaciofisico.ActualizarEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.ConsultarEspacioFisicoPorIdFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.ConsultarTodosEspacioFisicosFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.QuitarEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.RegistrarEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.ActualizarEspacioFisicoFachadaImpl;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.ConsultarEspacioFisicoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.ConsultarTodosEspacioFisicosFachadaImpl;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.QuitarEspacioFisicoFachadaImpl;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.RegistrarEspacioFisicoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/espaciosfisicos")
public class EspacioFisicoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<EspacioFisicoDTO>> registrar(@RequestBody EspacioFisicoDTO datos) {
		Respuesta<EspacioFisicoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarEspacioFisicoFachada fachada = new RegistrarEspacioFisicoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("EspacioFisico registrado exitosamente");
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
	public ResponseEntity<Respuesta<EspacioFisicoDTO>> actualizar(@PathVariable UUID id, @RequestBody EspacioFisicoDTO datos) {
		Respuesta<EspacioFisicoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datosConId = new EspacioFisicoDTO.Builder().id(id).build();
			ActualizarEspacioFisicoFachada fachada = new ActualizarEspacioFisicoFachadaImpl();
			fachada.ejecutar(datosConId);
			respuesta.agregarMensaje("EspacioFisico actualizado exitosamente");
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
	public ResponseEntity<Respuesta<EspacioFisicoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<EspacioFisicoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new EspacioFisicoDTO.Builder().id(id).build();
			QuitarEspacioFisicoFachada fachada = new QuitarEspacioFisicoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("EspacioFisico eliminado exitosamente");
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
	public ResponseEntity<Respuesta<EspacioFisicoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<EspacioFisicoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new EspacioFisicoDTO.Builder().id(id).build();
			ConsultarEspacioFisicoPorIdFachada fachada = new ConsultarEspacioFisicoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("EspacioFisico consultado exitosamente");
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
	public ResponseEntity<Respuesta<EspacioFisicoDTO>> consultarTodos() {
		Respuesta<EspacioFisicoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosEspacioFisicosFachada fachada = new ConsultarTodosEspacioFisicosFachadaImpl();
			var resultados = fachada.ejecutar(new EspacioFisicoDTO.Builder().build());
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
