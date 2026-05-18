package co.uco.erzparking.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.uco.erzparking.controlador.dto.RespuestaExito;
import co.uco.erzparking.dto.TarifaDTO;
import co.uco.erzparking.negocio.fachada.tarifa.ActivarTarifaFachada;
import co.uco.erzparking.negocio.fachada.tarifa.ConsultarTarifaPorIdFachada;
import co.uco.erzparking.negocio.fachada.tarifa.ConsultarTodosTarifasFachada;
import co.uco.erzparking.negocio.fachada.tarifa.DesactivarTarifaFachada;
import co.uco.erzparking.negocio.fachada.tarifa.FinalizarVigenciaTarifaFachada;
import co.uco.erzparking.negocio.fachada.tarifa.RegistrarTarifaFachada;
import co.uco.erzparking.negocio.fachada.tarifa.impl.ActivarTarifaFachadaImpl;
import co.uco.erzparking.negocio.fachada.tarifa.impl.ConsultarTarifaPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.tarifa.impl.ConsultarTodosTarifasFachadaImpl;
import co.uco.erzparking.negocio.fachada.tarifa.impl.DesactivarTarifaFachadaImpl;
import co.uco.erzparking.negocio.fachada.tarifa.impl.FinalizarVigenciaTarifaFachadaImpl;
import co.uco.erzparking.negocio.fachada.tarifa.impl.RegistrarTarifaFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/tarifas")
public class TarifaControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevaTarifa(@RequestBody TarifaDTO tarifa) {
		RegistrarTarifaFachada fachada = new RegistrarTarifaFachadaImpl();
		fachada.ejecutar(tarifa);

		return new ResponseEntity<>(RespuestaExito.crear("La tarifa se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/finalizar/{id}")
	public ResponseEntity<RespuestaExito<String>> finalizarVigenciaTarifaExistente(@PathVariable UUID id) {
		var tarifa = new TarifaDTO.Builder().id(id).build();
		FinalizarVigenciaTarifaFachada fachada = new FinalizarVigenciaTarifaFachadaImpl();
		fachada.ejecutar(tarifa);

		return new ResponseEntity<>(RespuestaExito.crear("La vigencia de la tarifa se ha finalizado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<TarifaDTO>> consultarTarifaPorId(@PathVariable UUID id) {
		var tarifa = new TarifaDTO.Builder().id(id).build();
		ConsultarTarifaPorIdFachada fachada = new ConsultarTarifaPorIdFachadaImpl();
		var resultado = fachada.ejecutar(tarifa);

		return new ResponseEntity<>(RespuestaExito.crear("La tarifa se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<TarifaDTO>>> consultarTarifas() {
		ConsultarTodosTarifasFachada fachada = new ConsultarTodosTarifasFachadaImpl();
		var resultado = fachada.ejecutar(new TarifaDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Tarifas consultadas exitosamente.", resultado), HttpStatus.OK);
	}

	@PatchMapping("/{id}/activar")
	public ResponseEntity<RespuestaExito<String>> activarTarifaExistente(@PathVariable UUID id) {
		var tarifa = new TarifaDTO.Builder().id(id).build();
		ActivarTarifaFachada fachada = new ActivarTarifaFachadaImpl();
		fachada.ejecutar(tarifa);

		return new ResponseEntity<>(RespuestaExito.crear("La tarifa se ha activado exitosamente.", ""), HttpStatus.OK);
	}

	@PatchMapping("/{id}/desactivar")
	public ResponseEntity<RespuestaExito<String>> desactivarTarifaExistente(@PathVariable UUID id) {
		var tarifa = new TarifaDTO.Builder().id(id).build();
		DesactivarTarifaFachada fachada = new DesactivarTarifaFachadaImpl();
		fachada.ejecutar(tarifa);

		return new ResponseEntity<>(RespuestaExito.crear("La tarifa se ha desactivado exitosamente.", ""), HttpStatus.OK);
	}

}
