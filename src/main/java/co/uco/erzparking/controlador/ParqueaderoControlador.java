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
import co.uco.erzparking.dto.ParqueaderoDTO;
import co.uco.erzparking.negocio.fachada.parqueadero.ActualizarParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.ConsultarParqueaderoPorIdFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.ConsultarTodosParqueaderosFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.QuitarParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.RegistrarParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.ActualizarParqueaderoFachadaImpl;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.ConsultarParqueaderoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.ConsultarTodosParqueaderosFachadaImpl;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.QuitarParqueaderoFachadaImpl;
import co.uco.erzparking.negocio.fachada.parqueadero.impl.RegistrarParqueaderoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/parqueaderos")
public class ParqueaderoControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoParqueadero(@RequestBody ParqueaderoDTO parqueadero) {
		RegistrarParqueaderoFachada fachada = new RegistrarParqueaderoFachadaImpl();
		fachada.ejecutar(parqueadero);

		return new ResponseEntity<>(RespuestaExito.crear("El parqueadero se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionParqueaderoExistente(@PathVariable UUID id, @RequestBody ParqueaderoDTO parqueadero) {
		var parqueaderoConId = new ParqueaderoDTO.Builder()
				.id(id)
				.nombreEstablecimiento(parqueadero.getNombreEstablecimiento())
				.numeroTelefonico(parqueadero.getNumeroTelefonico())
				.correoElectronico(parqueadero.getCorreoElectronico())
				.direccionEstablecimiento(parqueadero.getDireccionEstablecimiento())
				.ciudad(parqueadero.getCiudad())
				.build();

		ActualizarParqueaderoFachada fachada = new ActualizarParqueaderoFachadaImpl();
		fachada.ejecutar(parqueaderoConId);

		return new ResponseEntity<>(RespuestaExito.crear("El parqueadero se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaParqueaderoExistente(@PathVariable UUID id) {
		var parqueadero = new ParqueaderoDTO.Builder().id(id).build();
		QuitarParqueaderoFachada fachada = new QuitarParqueaderoFachadaImpl();
		fachada.ejecutar(parqueadero);

		return new ResponseEntity<>(RespuestaExito.crear("El parqueadero se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<ParqueaderoDTO>> consultarParqueaderoPorId(@PathVariable UUID id) {
		var parqueadero = new ParqueaderoDTO.Builder().id(id).build();
		ConsultarParqueaderoPorIdFachada fachada = new ConsultarParqueaderoPorIdFachadaImpl();
		var resultado = fachada.ejecutar(parqueadero);

		return new ResponseEntity<>(RespuestaExito.crear("El parqueadero se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<ParqueaderoDTO>>> consultarParqueaderos() {
		ConsultarTodosParqueaderosFachada fachada = new ConsultarTodosParqueaderosFachadaImpl();
		var resultado = fachada.ejecutar(new ParqueaderoDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Parqueaderos consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
