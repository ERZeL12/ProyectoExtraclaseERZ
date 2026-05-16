package co.uco.erzparking.controlador;

import co.uco.erzparking.controlador.dto.Respuesta;
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.fachada.tipovehiculo.ConsultarTipoVehiculoPorIdFachada;
import co.uco.erzparking.negocio.fachada.tipovehiculo.ConsultarTodosTipoVehiculosFachada;
import co.uco.erzparking.negocio.fachada.tipovehiculo.QuitarTipoVehiculoFachada;
import co.uco.erzparking.negocio.fachada.tipovehiculo.RegistrarTipoVehiculoFachada;
import co.uco.erzparking.negocio.fachada.tipovehiculo.impl.ConsultarTipoVehiculoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipovehiculo.impl.ConsultarTodosTipoVehiculosFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipovehiculo.impl.QuitarTipoVehiculoFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipovehiculo.impl.RegistrarTipoVehiculoFachadaImpl;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erzparking/v1/tiposvehiculo")
public class TipoVehiculoControlador {

	@PostMapping
	public ResponseEntity<Respuesta<TipoVehiculoDTO>> registrar(@RequestBody TipoVehiculoDTO datos) {
		Respuesta<TipoVehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			RegistrarTipoVehiculoFachada fachada = new RegistrarTipoVehiculoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("TipoVehiculo registrado exitosamente");
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
	public ResponseEntity<Respuesta<TipoVehiculoDTO>> quitar(@PathVariable UUID id) {
		Respuesta<TipoVehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new TipoVehiculoDTO.Builder().id(id).build();
			QuitarTipoVehiculoFachada fachada = new QuitarTipoVehiculoFachadaImpl();
			fachada.ejecutar(datos);
			respuesta.agregarMensaje("TipoVehiculo eliminado exitosamente");
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
	public ResponseEntity<Respuesta<TipoVehiculoDTO>> consultarPorId(@PathVariable UUID id) {
		Respuesta<TipoVehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			var datos = new TipoVehiculoDTO.Builder().id(id).build();
			ConsultarTipoVehiculoPorIdFachada fachada = new ConsultarTipoVehiculoPorIdFachadaImpl();
			var resultado = fachada.ejecutar(datos);
			respuesta.setDatos(List.of(resultado));
			respuesta.agregarMensaje("TipoVehiculo consultado exitosamente");
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
	public ResponseEntity<Respuesta<TipoVehiculoDTO>> consultarTodos() {
		Respuesta<TipoVehiculoDTO> respuesta = Respuesta.crearRespuestaExitosa();
		HttpStatusCode codigoEstado = HttpStatus.OK;
		try {
			ConsultarTodosTipoVehiculosFachada fachada = new ConsultarTodosTipoVehiculosFachadaImpl();
			var resultados = fachada.ejecutar(new TipoVehiculoDTO.Builder().build());
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
