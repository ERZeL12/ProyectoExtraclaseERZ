package co.uco.erzparking.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.uco.erzparking.controlador.dto.RespuestaExito;
import co.uco.erzparking.dto.CargoDTO;
import co.uco.erzparking.negocio.fachada.cargo.ActivarCargoFachada;
import co.uco.erzparking.negocio.fachada.cargo.ActualizarCargoFachada;
import co.uco.erzparking.negocio.fachada.cargo.ConsultarCargoPorIdFachada;
import co.uco.erzparking.negocio.fachada.cargo.ConsultarTodosCargosFachada;
import co.uco.erzparking.negocio.fachada.cargo.DesactivarCargoFachada;
import co.uco.erzparking.negocio.fachada.cargo.QuitarCargoFachada;
import co.uco.erzparking.negocio.fachada.cargo.RegistrarCargoFachada;
import co.uco.erzparking.negocio.fachada.cargo.impl.ActivarCargoFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.ActualizarCargoFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.ConsultarCargoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.ConsultarTodosCargosFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.DesactivarCargoFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.QuitarCargoFachadaImpl;
import co.uco.erzparking.negocio.fachada.cargo.impl.RegistrarCargoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/cargos")
public class CargoControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoCargo(@RequestBody CargoDTO cargo) {
		RegistrarCargoFachada fachada = new RegistrarCargoFachadaImpl();
		fachada.ejecutar(cargo);

		return new ResponseEntity<>(RespuestaExito.crear("El cargo se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionCargoExistente(@PathVariable UUID id, @RequestBody CargoDTO cargo) {
		var cargoConId = new CargoDTO.Builder()
				.id(id)
				.nombreCargo(cargo.getNombreCargo())
				.descripcion(cargo.getDescripcion())
				.parqueadero(cargo.getParqueadero())
				.build();

		ActualizarCargoFachada fachada = new ActualizarCargoFachadaImpl();
		fachada.ejecutar(cargoConId);

		return new ResponseEntity<>(RespuestaExito.crear("El cargo se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaCargoExistente(@PathVariable UUID id) {
		var cargo = new CargoDTO.Builder().id(id).build();
		QuitarCargoFachada fachada = new QuitarCargoFachadaImpl();
		fachada.ejecutar(cargo);

		return new ResponseEntity<>(RespuestaExito.crear("El cargo se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<CargoDTO>> consultarCargoPorId(@PathVariable UUID id) {
		var cargo = new CargoDTO.Builder().id(id).build();
		ConsultarCargoPorIdFachada fachada = new ConsultarCargoPorIdFachadaImpl();
		var resultado = fachada.ejecutar(cargo);

		return new ResponseEntity<>(RespuestaExito.crear("El cargo se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<CargoDTO>>> consultarCargos() {
		ConsultarTodosCargosFachada fachada = new ConsultarTodosCargosFachadaImpl();
		var resultado = fachada.ejecutar(new CargoDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Cargos consultados exitosamente.", resultado), HttpStatus.OK);
	}

	@PatchMapping("/{id}/activar")
	public ResponseEntity<RespuestaExito<String>> activarCargoExistente(@PathVariable UUID id) {
		var cargo = new CargoDTO.Builder().id(id).build();
		ActivarCargoFachada fachada = new ActivarCargoFachadaImpl();
		fachada.ejecutar(cargo);

		return new ResponseEntity<>(RespuestaExito.crear("El cargo se ha activado exitosamente.", ""), HttpStatus.OK);
	}

	@PatchMapping("/{id}/desactivar")
	public ResponseEntity<RespuestaExito<String>> desactivarCargoExistente(@PathVariable UUID id) {
		var cargo = new CargoDTO.Builder().id(id).build();
		DesactivarCargoFachada fachada = new DesactivarCargoFachadaImpl();
		fachada.ejecutar(cargo);

		return new ResponseEntity<>(RespuestaExito.crear("El cargo se ha desactivado exitosamente.", ""), HttpStatus.OK);
	}

}
