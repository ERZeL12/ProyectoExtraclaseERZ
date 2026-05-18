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
import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.negocio.fachada.operario.ActivarOperarioFachada;
import co.uco.erzparking.negocio.fachada.operario.ActualizarOperarioFachada;
import co.uco.erzparking.negocio.fachada.operario.ConsultarOperarioPorIdFachada;
import co.uco.erzparking.negocio.fachada.operario.ConsultarTodosOperariosFachada;
import co.uco.erzparking.negocio.fachada.operario.DesactivarOperarioFachada;
import co.uco.erzparking.negocio.fachada.operario.QuitarOperarioFachada;
import co.uco.erzparking.negocio.fachada.operario.RegistrarOperarioFachada;
import co.uco.erzparking.negocio.fachada.operario.impl.ActivarOperarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.ActualizarOperarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.ConsultarOperarioPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.ConsultarTodosOperariosFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.DesactivarOperarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.QuitarOperarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.operario.impl.RegistrarOperarioFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/operarios")
public class OperarioControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoOperario(@RequestBody OperarioDTO operario) {
		RegistrarOperarioFachada fachada = new RegistrarOperarioFachadaImpl();
		fachada.ejecutar(operario);

		return new ResponseEntity<>(RespuestaExito.crear("El operario se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionOperarioExistente(@PathVariable UUID id, @RequestBody OperarioDTO operario) {
		var operarioConId = new OperarioDTO.Builder()
				.id(id)
				.tipoDocumentoIdentificacion(operario.getTipoDocumentoIdentificacion())
				.numeroIdentificacion(operario.getNumeroIdentificacion())
				.primerNombre(operario.getPrimerNombre())
				.segundoNombre(operario.getSegundoNombre())
				.primerApellido(operario.getPrimerApellido())
				.segundoApellido(operario.getSegundoApellido())
				.numeroTelefonico(operario.getNumeroTelefonico())
				.cargo(operario.getCargo())
				.parqueadero(operario.getParqueadero())
				.build();

		ActualizarOperarioFachada fachada = new ActualizarOperarioFachadaImpl();
		fachada.ejecutar(operarioConId);

		return new ResponseEntity<>(RespuestaExito.crear("El operario se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaOperarioExistente(@PathVariable UUID id) {
		var operario = new OperarioDTO.Builder().id(id).build();
		QuitarOperarioFachada fachada = new QuitarOperarioFachadaImpl();
		fachada.ejecutar(operario);

		return new ResponseEntity<>(RespuestaExito.crear("El operario se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<OperarioDTO>> consultarOperarioPorId(@PathVariable UUID id) {
		var operario = new OperarioDTO.Builder().id(id).build();
		ConsultarOperarioPorIdFachada fachada = new ConsultarOperarioPorIdFachadaImpl();
		var resultado = fachada.ejecutar(operario);

		return new ResponseEntity<>(RespuestaExito.crear("El operario se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<OperarioDTO>>> consultarOperarios() {
		ConsultarTodosOperariosFachada fachada = new ConsultarTodosOperariosFachadaImpl();
		var resultado = fachada.ejecutar(new OperarioDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Operarios consultados exitosamente.", resultado), HttpStatus.OK);
	}

	@PatchMapping("/{id}/activar")
	public ResponseEntity<RespuestaExito<String>> activarOperarioExistente(@PathVariable UUID id) {
		var operario = new OperarioDTO.Builder().id(id).build();
		ActivarOperarioFachada fachada = new ActivarOperarioFachadaImpl();
		fachada.ejecutar(operario);

		return new ResponseEntity<>(RespuestaExito.crear("El operario se ha activado exitosamente.", ""), HttpStatus.OK);
	}

	@PatchMapping("/{id}/desactivar")
	public ResponseEntity<RespuestaExito<String>> desactivarOperarioExistente(@PathVariable UUID id) {
		var operario = new OperarioDTO.Builder().id(id).build();
		DesactivarOperarioFachada fachada = new DesactivarOperarioFachadaImpl();
		fachada.ejecutar(operario);

		return new ResponseEntity<>(RespuestaExito.crear("El operario se ha desactivado exitosamente.", ""), HttpStatus.OK);
	}

}
