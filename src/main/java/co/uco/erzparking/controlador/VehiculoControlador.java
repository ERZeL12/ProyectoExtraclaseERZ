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
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.fachada.vehiculo.ActualizarVehiculoFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.ConsultarTodosVehiculosFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.ConsultarVehiculoPorIdFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.QuitarVehiculoFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.RegistrarVehiculoFachada;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.ActualizarVehiculoFachadaImpl;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.ConsultarTodosVehiculosFachadaImpl;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.ConsultarVehiculoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.QuitarVehiculoFachadaImpl;
import co.uco.erzparking.negocio.fachada.vehiculo.impl.RegistrarVehiculoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/vehiculos")
public class VehiculoControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoVehiculo(@RequestBody VehiculoDTO vehiculo) {
		RegistrarVehiculoFachada fachada = new RegistrarVehiculoFachadaImpl();
		fachada.ejecutar(vehiculo);

		return new ResponseEntity<>(RespuestaExito.crear("El vehiculo se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionVehiculoExistente(@PathVariable UUID id, @RequestBody VehiculoDTO vehiculo) {
		var vehiculoConId = new VehiculoDTO.Builder()
				.id(id)
				.placaVehiculo(vehiculo.getPlacaVehiculo())
				.tipoVehiculo(vehiculo.getTipoVehiculo())
				.build();

		ActualizarVehiculoFachada fachada = new ActualizarVehiculoFachadaImpl();
		fachada.ejecutar(vehiculoConId);

		return new ResponseEntity<>(RespuestaExito.crear("El vehiculo se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaVehiculoExistente(@PathVariable UUID id) {
		var vehiculo = new VehiculoDTO.Builder().id(id).build();
		QuitarVehiculoFachada fachada = new QuitarVehiculoFachadaImpl();
		fachada.ejecutar(vehiculo);

		return new ResponseEntity<>(RespuestaExito.crear("El vehiculo se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<VehiculoDTO>> consultarVehiculoPorId(@PathVariable UUID id) {
		var vehiculo = new VehiculoDTO.Builder().id(id).build();
		ConsultarVehiculoPorIdFachada fachada = new ConsultarVehiculoPorIdFachadaImpl();
		var resultado = fachada.ejecutar(vehiculo);

		return new ResponseEntity<>(RespuestaExito.crear("El vehiculo se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<VehiculoDTO>>> consultarVehiculos() {
		ConsultarTodosVehiculosFachada fachada = new ConsultarTodosVehiculosFachadaImpl();
		var resultado = fachada.ejecutar(new VehiculoDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Vehiculos consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
