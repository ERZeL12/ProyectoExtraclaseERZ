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
import co.uco.erzparking.dto.TipoVehiculoDTO;
import co.uco.erzparking.negocio.fachada.tipovehiculo.ConsultarTipoVehiculoPorIdFachada;
import co.uco.erzparking.negocio.fachada.tipovehiculo.ConsultarTodosTipoVehiculosFachada;
import co.uco.erzparking.negocio.fachada.tipovehiculo.QuitarTipoVehiculoFachada;
import co.uco.erzparking.negocio.fachada.tipovehiculo.RegistrarTipoVehiculoFachada;
import co.uco.erzparking.negocio.fachada.tipovehiculo.impl.ConsultarTipoVehiculoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipovehiculo.impl.ConsultarTodosTipoVehiculosFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipovehiculo.impl.QuitarTipoVehiculoFachadaImpl;
import co.uco.erzparking.negocio.fachada.tipovehiculo.impl.RegistrarTipoVehiculoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/tiposvehiculo")
public class TipoVehiculoControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoTipoVehiculo(@RequestBody TipoVehiculoDTO tipo) {
		RegistrarTipoVehiculoFachada fachada = new RegistrarTipoVehiculoFachadaImpl();
		fachada.ejecutar(tipo);

		return new ResponseEntity<>(RespuestaExito.crear("El tipo de vehiculo se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaTipoVehiculoExistente(@PathVariable UUID id) {
		var tipo = new TipoVehiculoDTO.Builder().id(id).build();
		QuitarTipoVehiculoFachada fachada = new QuitarTipoVehiculoFachadaImpl();
		fachada.ejecutar(tipo);

		return new ResponseEntity<>(RespuestaExito.crear("El tipo de vehiculo se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<TipoVehiculoDTO>> consultarTipoVehiculoPorId(@PathVariable UUID id) {
		var tipo = new TipoVehiculoDTO.Builder().id(id).build();
		ConsultarTipoVehiculoPorIdFachada fachada = new ConsultarTipoVehiculoPorIdFachadaImpl();
		var resultado = fachada.ejecutar(tipo);

		return new ResponseEntity<>(RespuestaExito.crear("El tipo de vehiculo se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<TipoVehiculoDTO>>> consultarTiposVehiculo() {
		ConsultarTodosTipoVehiculosFachada fachada = new ConsultarTodosTipoVehiculosFachadaImpl();
		var resultado = fachada.ejecutar(new TipoVehiculoDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Tipos de vehiculo consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
