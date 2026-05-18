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
import co.uco.erzparking.dto.ContratoMensualidadDTO;
import co.uco.erzparking.negocio.fachada.contratomensualidad.ActivarContratoMensualidadFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.ConsultarContratoMensualidadPorIdFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.ConsultarTodosContratoMensualidadsFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.DesactivarContratoMensualidadFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.FinalizarContratoMensualidadFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.RegistrarContratoMensualidadFachada;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.ActivarContratoMensualidadFachadaImpl;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.ConsultarContratoMensualidadPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.ConsultarTodosContratoMensualidadsFachadaImpl;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.DesactivarContratoMensualidadFachadaImpl;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.FinalizarContratoMensualidadFachadaImpl;
import co.uco.erzparking.negocio.fachada.contratomensualidad.impl.RegistrarContratoMensualidadFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/contratosmensualidad")
public class ContratoMensualidadControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoContratoMensualidad(@RequestBody ContratoMensualidadDTO contrato) {
		RegistrarContratoMensualidadFachada fachada = new RegistrarContratoMensualidadFachadaImpl();
		fachada.ejecutar(contrato);

		return new ResponseEntity<>(RespuestaExito.crear("El contrato de mensualidad se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/finalizar/{id}")
	public ResponseEntity<RespuestaExito<String>> finalizarContratoMensualidadExistente(@PathVariable UUID id) {
		var contrato = new ContratoMensualidadDTO.Builder().id(id).build();
		FinalizarContratoMensualidadFachada fachada = new FinalizarContratoMensualidadFachadaImpl();
		fachada.ejecutar(contrato);

		return new ResponseEntity<>(RespuestaExito.crear("El contrato de mensualidad se ha finalizado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<ContratoMensualidadDTO>> consultarContratoMensualidadPorId(@PathVariable UUID id) {
		var contrato = new ContratoMensualidadDTO.Builder().id(id).build();
		ConsultarContratoMensualidadPorIdFachada fachada = new ConsultarContratoMensualidadPorIdFachadaImpl();
		var resultado = fachada.ejecutar(contrato);

		return new ResponseEntity<>(RespuestaExito.crear("El contrato de mensualidad se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<ContratoMensualidadDTO>>> consultarContratosMensualidad() {
		ConsultarTodosContratoMensualidadsFachada fachada = new ConsultarTodosContratoMensualidadsFachadaImpl();
		var resultado = fachada.ejecutar(new ContratoMensualidadDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Contratos de mensualidad consultados exitosamente.", resultado), HttpStatus.OK);
	}

	@PatchMapping("/{id}/activar")
	public ResponseEntity<RespuestaExito<String>> activarContratoMensualidadExistente(@PathVariable UUID id) {
		var contrato = new ContratoMensualidadDTO.Builder().id(id).build();
		ActivarContratoMensualidadFachada fachada = new ActivarContratoMensualidadFachadaImpl();
		fachada.ejecutar(contrato);

		return new ResponseEntity<>(RespuestaExito.crear("El contrato de mensualidad se ha activado exitosamente.", ""), HttpStatus.OK);
	}

	@PatchMapping("/{id}/desactivar")
	public ResponseEntity<RespuestaExito<String>> desactivarContratoMensualidadExistente(@PathVariable UUID id) {
		var contrato = new ContratoMensualidadDTO.Builder().id(id).build();
		DesactivarContratoMensualidadFachada fachada = new DesactivarContratoMensualidadFachadaImpl();
		fachada.ejecutar(contrato);

		return new ResponseEntity<>(RespuestaExito.crear("El contrato de mensualidad se ha desactivado exitosamente.", ""), HttpStatus.OK);
	}

}
