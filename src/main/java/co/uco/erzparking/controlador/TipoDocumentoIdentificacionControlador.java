package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.ConsultarTipoDocumentoIdentificacionPorIdFachada;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.ConsultarTodosTipoDocumentoIdentificacionsFachada;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.QuitarTipoDocumentoIdentificacionFachada;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.RegistrarTipoDocumentoIdentificacionFachada;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl.ConsultarTipoDocumentoIdentificacionPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl.ConsultarTodosTipoDocumentoIdentificacionsFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl.QuitarTipoDocumentoIdentificacionFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl.RegistrarTipoDocumentoIdentificacionFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/tiposdocumentoidentificacion")
public class TipoDocumentoIdentificacionControlador {

	@PostMapping
	public ResponseEntity<Respuesta<TipoDocumentoIdentificacionDTO>> registrar(@RequestBody TipoDocumentoIdentificacionDTO datos) {
		Respuesta<TipoDocumentoIdentificacionDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarTipoDocumentoIdentificacionFachada fachada = new RegistrarTipoDocumentoIdentificacionFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("TipoDocumentoIdentificacion registrado exitosamente");
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
	public ResponseEntity<Respuesta<TipoDocumentoIdentificacionDTO>> quitar(@PathVariable UUID id) {
		Respuesta<TipoDocumentoIdentificacionDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new TipoDocumentoIdentificacionDTO.Builder().id(id).build();
			QuitarTipoDocumentoIdentificacionFachada fachada = new QuitarTipoDocumentoIdentificacionFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("TipoDocumentoIdentificacion eliminado exitosamente");
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
	public ResponseEntity<Respuesta<TipoDocumentoIdentificacionDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<TipoDocumentoIdentificacionDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new TipoDocumentoIdentificacionDTO.Builder().id(id).build();
			ConsultarTipoDocumentoIdentificacionPorIdFachada fachada = new ConsultarTipoDocumentoIdentificacionPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("TipoDocumentoIdentificacion consultado exitosamente");
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
	public ResponseEntity<Respuesta<TipoDocumentoIdentificacionDTO>> consultarTodos() {
		Respuesta<TipoDocumentoIdentificacionDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosTipoDocumentoIdentificacionsFachada fachada = new ConsultarTodosTipoDocumentoIdentificacionsFachadaImpl();
			var resultados = fachada.ejecutar(new TipoDocumentoIdentificacionDTO.Builder().build());
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
