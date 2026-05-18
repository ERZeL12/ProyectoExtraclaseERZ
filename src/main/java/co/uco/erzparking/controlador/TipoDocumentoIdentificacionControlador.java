package co.uco.erzparking.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.uco.erzparking.controlador.dto.RespuestaExito;
import co.uco.erzparking.dto.TipoDocumentoIdentificacionDTO;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.ConsultarTipoDocumentoIdentificacionPorIdFachada;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.ConsultarTodosTipoDocumentoIdentificacionsFachada;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.QuitarTipoDocumentoIdentificacionFachada;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.RegistrarTipoDocumentoIdentificacionFachada;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl.ConsultarTipoDocumentoIdentificacionPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl.ConsultarTodosTipoDocumentoIdentificacionsFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl.QuitarTipoDocumentoIdentificacionFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipodocumentoidentificacion.impl.RegistrarTipoDocumentoIdentificacionFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/tiposdocumentoidentificacion")
public class TipoDocumentoIdentificacionControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoTipoDocumentoIdentificacion(@RequestBody TipoDocumentoIdentificacionDTO tipo) {
		RegistrarTipoDocumentoIdentificacionFachada fachada = new RegistrarTipoDocumentoIdentificacionFachadaImpl();
		fachada.ejecutar(tipo);

		return new ResponseEntity<>(RespuestaExito.crear("El tipo de documento de identificacion se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaTipoDocumentoIdentificacionExistente(@PathVariable UUID id) {
		var tipo = new TipoDocumentoIdentificacionDTO.Builder().id(id).build();
		QuitarTipoDocumentoIdentificacionFachada fachada = new QuitarTipoDocumentoIdentificacionFachadaImpl();
		fachada.ejecutar(tipo);

		return new ResponseEntity<>(RespuestaExito.crear("El tipo de documento de identificacion se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<TipoDocumentoIdentificacionDTO>> consultarTipoDocumentoIdentificacionPorId(@PathVariable UUID id) {
		var tipo = new TipoDocumentoIdentificacionDTO.Builder().id(id).build();
		ConsultarTipoDocumentoIdentificacionPorIdFachada fachada = new ConsultarTipoDocumentoIdentificacionPorIdFachadaImpl();
		var resultado = fachada.ejecutar(tipo);

		return new ResponseEntity<>(RespuestaExito.crear("El tipo de documento de identificacion se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<TipoDocumentoIdentificacionDTO>>> consultarTiposDocumentoIdentificacion() {
		ConsultarTodosTipoDocumentoIdentificacionsFachada fachada = new ConsultarTodosTipoDocumentoIdentificacionsFachadaImpl();
		var resultado = fachada.ejecutar(new TipoDocumentoIdentificacionDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Tipos de documento de identificacion consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
