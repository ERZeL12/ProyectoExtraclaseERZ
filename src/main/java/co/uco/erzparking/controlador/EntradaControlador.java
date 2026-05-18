package co.uco.erzparking.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.uco.erzparking.controlador.dto.RespuestaExito;
import co.uco.erzparking.dto.EntradaDTO;
import co.uco.erzparking.negocio.fachada.entrada.ConsultarEntradaPorIdFachada;
import co.uco.erzparking.negocio.fachada.entrada.ConsultarTodosEntradasFachada;
import co.uco.erzparking.negocio.fachada.entrada.RegistrarEntradaVehiculoFachada;
import co.uco.erzparking.negocio.fachada.entrada.impl.ConsultarEntradaPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.entrada.impl.ConsultarTodosEntradasFachadaImpl;
import co.uco.erzparking.negocio.fachada.entrada.impl.RegistrarEntradaVehiculoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/entradas")
public class EntradaControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevaEntradaVehiculo(@RequestBody EntradaDTO entrada) {
		RegistrarEntradaVehiculoFachada fachada = new RegistrarEntradaVehiculoFachadaImpl();
		fachada.ejecutar(entrada);

		return new ResponseEntity<>(RespuestaExito.crear("La entrada del vehiculo se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<EntradaDTO>> consultarEntradaPorId(@PathVariable UUID id) {
		var entrada = new EntradaDTO.Builder().id(id).build();
		ConsultarEntradaPorIdFachada fachada = new ConsultarEntradaPorIdFachadaImpl();
		var resultado = fachada.ejecutar(entrada);

		return new ResponseEntity<>(RespuestaExito.crear("La entrada se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<EntradaDTO>>> consultarEntradas() {
		ConsultarTodosEntradasFachada fachada = new ConsultarTodosEntradasFachadaImpl();
		var resultado = fachada.ejecutar(new EntradaDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Entradas consultadas exitosamente.", resultado), HttpStatus.OK);
	}

}
