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
import co.uco.erzparking.dto.TipoServicioDTO;
import co.uco.erzparking.negocio.fachada.tiposervicio.ConsultarTipoServicioPorIdFachada;
import co.uco.erzparking.negocio.fachada.tiposervicio.ConsultarTodosTipoServiciosFachada;
import co.uco.erzparking.negocio.fachada.tiposervicio.QuitarTipoServicioFachada;
import co.uco.erzparking.negocio.fachada.tiposervicio.RegistrarTipoServicioFachada;
import co.uco.erzparking.negocio.fachada.tiposervicio.impl.ConsultarTipoServicioPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.tiposervicio.impl.ConsultarTodosTipoServiciosFachadaImpl;
import co.uco.erzparking.negocio.fachada.tiposervicio.impl.QuitarTipoServicioFachadaImpl;
import co.uco.erzparking.negocio.fachada.tiposervicio.impl.RegistrarTipoServicioFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/tiposservicio")
public class TipoServicioControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoTipoServicio(@RequestBody TipoServicioDTO tipo) {
		RegistrarTipoServicioFachada fachada = new RegistrarTipoServicioFachadaImpl();
		fachada.ejecutar(tipo);

		return new ResponseEntity<>(RespuestaExito.crear("El tipo de servicio se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaTipoServicioExistente(@PathVariable UUID id) {
		var tipo = new TipoServicioDTO.Builder().id(id).build();
		QuitarTipoServicioFachada fachada = new QuitarTipoServicioFachadaImpl();
		fachada.ejecutar(tipo);

		return new ResponseEntity<>(RespuestaExito.crear("El tipo de servicio se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<TipoServicioDTO>> consultarTipoServicioPorId(@PathVariable UUID id) {
		var tipo = new TipoServicioDTO.Builder().id(id).build();
		ConsultarTipoServicioPorIdFachada fachada = new ConsultarTipoServicioPorIdFachadaImpl();
		var resultado = fachada.ejecutar(tipo);

		return new ResponseEntity<>(RespuestaExito.crear("El tipo de servicio se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<TipoServicioDTO>>> consultarTiposServicio() {
		ConsultarTodosTipoServiciosFachada fachada = new ConsultarTodosTipoServiciosFachadaImpl();
		var resultado = fachada.ejecutar(new TipoServicioDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Tipos de servicio consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
