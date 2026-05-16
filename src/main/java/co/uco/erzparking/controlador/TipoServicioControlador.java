package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.fachada.tiposervicio.ConsultarTipoServicioPorIdFachada;
import co.uco.erzparking.negocio.fachada.tiposervicio.ConsultarTodosTipoServiciosFachada;
import co.uco.erzparking.negocio.fachada.tiposervicio.QuitarTipoServicioFachada;
import co.uco.erzparking.negocio.fachada.tiposervicio.RegistrarTipoServicioFachada;
import co.uco.erzparking.negocio.fachada.tiposervicio.impl.ConsultarTipoServicioPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.tiposervicio.impl.ConsultarTodosTipoServiciosFachadaImpl;
import co.uco.erzparking.negocio.fachada.tiposervicio.impl.QuitarTipoServicioFachadaImpl;
import co.uco.erzparking.negocio.fachada.tiposervicio.impl.RegistrarTipoServicioFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/tiposservicio")
public class TipoServicioControlador {

	@PostMapping
	public ResponseEntity<Respuesta<TipoServicioDTO>> registrar(@RequestBody TipoServicioDTO datos) {
		Respuesta<TipoServicioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarTipoServicioFachada fachada = new RegistrarTipoServicioFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("TipoServicio registrado exitosamente");
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
	public ResponseEntity<Respuesta<TipoServicioDTO>> quitar(@PathVariable UUID id) {
		Respuesta<TipoServicioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new TipoServicioDTO.Builder().id(id).build();
			QuitarTipoServicioFachada fachada = new QuitarTipoServicioFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("TipoServicio eliminado exitosamente");
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
	public ResponseEntity<Respuesta<TipoServicioDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<TipoServicioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new TipoServicioDTO.Builder().id(id).build();
			ConsultarTipoServicioPorIdFachada fachada = new ConsultarTipoServicioPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("TipoServicio consultado exitosamente");
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
	public ResponseEntity<Respuesta<TipoServicioDTO>> consultarTodos() {
		Respuesta<TipoServicioDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosTipoServiciosFachada fachada = new ConsultarTodosTipoServiciosFachadaImpl();
			var resultados = fachada.ejecutar(new TipoServicioDTO.Builder().build());
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
