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
import co.uco.erzparking.dto.CiudadDTO;
import co.uco.erzparking.negocio.fachada.ciudad.ActualizarCiudadFachada;
import co.uco.erzparking.negocio.fachada.ciudad.ConsultarCiudadPorIdFachada;
import co.uco.erzparking.negocio.fachada.ciudad.ConsultarTodosCiudadsFachada;
import co.uco.erzparking.negocio.fachada.ciudad.QuitarCiudadFachada;
import co.uco.erzparking.negocio.fachada.ciudad.RegistrarCiudadFachada;
import co.uco.erzparking.negocio.fachada.ciudad.impl.ActualizarCiudadFachadaImpl;
import co.uco.erzparking.negocio.fachada.ciudad.impl.ConsultarCiudadPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.ciudad.impl.ConsultarTodosCiudadsFachadaImpl;
import co.uco.erzparking.negocio.fachada.ciudad.impl.QuitarCiudadFachadaImpl;
import co.uco.erzparking.negocio.fachada.ciudad.impl.RegistrarCiudadFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/ciudades")
public class CiudadControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevaCiudad(@RequestBody CiudadDTO ciudad) {
		RegistrarCiudadFachada fachada = new RegistrarCiudadFachadaImpl();
		fachada.ejecutar(ciudad);

		return new ResponseEntity<>(RespuestaExito.crear("La ciudad se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionCiudadExistente(@PathVariable UUID id, @RequestBody CiudadDTO ciudad) {
		var ciudadConId = new CiudadDTO.Builder()
				.id(id)
				.nombre(ciudad.getNombre())
				.departamento(ciudad.getDepartamento())
				.build();

		ActualizarCiudadFachada fachada = new ActualizarCiudadFachadaImpl();
		fachada.ejecutar(ciudadConId);

		return new ResponseEntity<>(RespuestaExito.crear("La ciudad se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaCiudadExistente(@PathVariable UUID id) {
		var ciudad = new CiudadDTO.Builder().id(id).build();
		QuitarCiudadFachada fachada = new QuitarCiudadFachadaImpl();
		fachada.ejecutar(ciudad);

		return new ResponseEntity<>(RespuestaExito.crear("La ciudad se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<CiudadDTO>> consultarCiudadPorId(@PathVariable UUID id) {
		var ciudad = new CiudadDTO.Builder().id(id).build();
		ConsultarCiudadPorIdFachada fachada = new ConsultarCiudadPorIdFachadaImpl();
		var resultado = fachada.ejecutar(ciudad);

		return new ResponseEntity<>(RespuestaExito.crear("La ciudad se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<CiudadDTO>>> consultarCiudades() {
		ConsultarTodosCiudadsFachada fachada = new ConsultarTodosCiudadsFachadaImpl();
		var resultado = fachada.ejecutar(new CiudadDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Ciudades consultadas exitosamente.", resultado), HttpStatus.OK);
	}

}
