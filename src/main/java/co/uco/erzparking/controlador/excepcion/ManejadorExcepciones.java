package co.uco.erzparking.controlador.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.uco.erzparking.controlador.dto.RespuestaError;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

@RestControllerAdvice
public class ManejadorExcepciones {

	@ExceptionHandler(ERZParkingExcepcion.class)
	public ResponseEntity<RespuestaError> gestionarERZParkingExcepcion(final ERZParkingExcepcion excepcion) {
		System.err.println(excepcion.getMensajeTecnico());
		excepcion.getExcepcionRaiz().printStackTrace();

		return new ResponseEntity<>(RespuestaError.crear(excepcion.getMensajeUsuario()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RespuestaError> gestionarExcepcion(final Exception excepcion) {
		System.err.println("Excepcion no controlada.....");
		excepcion.printStackTrace();

		return new ResponseEntity<>(
				RespuestaError.crear("Se ha presentado un problema no esperado. Por favor intente de nuevo. Si el problema persiste, contacte al administrador de la aplicacion."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
