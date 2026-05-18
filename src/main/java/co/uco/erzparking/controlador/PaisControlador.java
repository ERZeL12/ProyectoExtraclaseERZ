package co.uco.erzparking.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.uco.erzparking.controlador.dto.RespuestaExito;
import co.uco.erzparking.dto.PaisDTO;
import co.uco.erzparking.negocio.fachada.pais.ActualizarPaisFachada;
import co.uco.erzparking.negocio.fachada.pais.ConsultarPaisPorIdFachada;
import co.uco.erzparking.negocio.fachada.pais.ConsultarTodosPaissFachada;
import co.uco.erzparking.negocio.fachada.pais.QuitarPaisFachada;
import co.uco.erzparking.negocio.fachada.pais.RegistrarPaisFachada;
import co.uco.erzparking.negocio.fachada.pais.impl.ActualizarPaisFachadaImpl;
import co.uco.erzparking.negocio.fachada.pais.impl.ConsultarPaisPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.pais.impl.ConsultarTodosPaissFachadaImpl;
import co.uco.erzparking.negocio.fachada.pais.impl.QuitarPaisFachadaImpl;
import co.uco.erzparking.negocio.fachada.pais.impl.RegistrarPaisFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/paises")
public class PaisControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoPais(@RequestBody PaisDTO pais) {
		RegistrarPaisFachada fachada = new RegistrarPaisFachadaImpl();
		fachada.ejecutar(pais);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionPaisExistente(@PathVariable UUID id, @RequestBody PaisDTO pais) {
		var paisConId = new PaisDTO.Builder()
				.id(id)
				.nombre(pais.getNombre())
				.build();

		ActualizarPaisFachada fachada = new ActualizarPaisFachadaImpl();
		fachada.ejecutar(paisConId);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaPaisExistente(@PathVariable UUID id) {
		var pais = new PaisDTO.Builder().id(id).build();
		QuitarPaisFachada fachada = new QuitarPaisFachadaImpl();
		fachada.ejecutar(pais);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<PaisDTO>> consultarPaisPorId(@PathVariable UUID id) {
		var pais = new PaisDTO.Builder().id(id).build();
		ConsultarPaisPorIdFachada fachada = new ConsultarPaisPorIdFachadaImpl();
		var resultado = fachada.ejecutar(pais);

		return new ResponseEntity<>(RespuestaExito.crear("El pais se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<PaisDTO>>> consultarPaises() {
		ConsultarTodosPaissFachada fachada = new ConsultarTodosPaissFachadaImpl();
		var resultado = fachada.ejecutar(new PaisDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Paises consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
