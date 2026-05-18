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
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.negocio.fachada.servicio.ActivarServicioFachada;
import co.uco.erzparking.negocio.fachada.servicio.ActualizarServicioFachada;
import co.uco.erzparking.negocio.fachada.servicio.ConsultarServicioPorIdFachada;
import co.uco.erzparking.negocio.fachada.servicio.ConsultarTodosServiciosFachada;
import co.uco.erzparking.negocio.fachada.servicio.DesactivarServicioFachada;
import co.uco.erzparking.negocio.fachada.servicio.QuitarServicioFachada;
import co.uco.erzparking.negocio.fachada.servicio.RegistrarServicioFachada;
import co.uco.erzparking.negocio.fachada.servicio.impl.ActivarServicioFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.ActualizarServicioFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.ConsultarServicioPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.ConsultarTodosServiciosFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.DesactivarServicioFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.QuitarServicioFachadaImpl;
import co.uco.erzparking.negocio.fachada.servicio.impl.RegistrarServicioFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/servicios")
public class ServicioControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoServicio(@RequestBody ServicioDTO servicio) {
		RegistrarServicioFachada fachada = new RegistrarServicioFachadaImpl();
		fachada.ejecutar(servicio);

		return new ResponseEntity<>(RespuestaExito.crear("El servicio se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionServicioExistente(@PathVariable UUID id, @RequestBody ServicioDTO servicio) {
		var servicioConId = new ServicioDTO.Builder()
				.id(id)
				.nombreServicio(servicio.getNombreServicio())
				.tipoServicio(servicio.getTipoServicio())
				.parqueadero(servicio.getParqueadero())
				.build();

		ActualizarServicioFachada fachada = new ActualizarServicioFachadaImpl();
		fachada.ejecutar(servicioConId);

		return new ResponseEntity<>(RespuestaExito.crear("El servicio se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaServicioExistente(@PathVariable UUID id) {
		var servicio = new ServicioDTO.Builder().id(id).build();
		QuitarServicioFachada fachada = new QuitarServicioFachadaImpl();
		fachada.ejecutar(servicio);

		return new ResponseEntity<>(RespuestaExito.crear("El servicio se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<ServicioDTO>> consultarServicioPorId(@PathVariable UUID id) {
		var servicio = new ServicioDTO.Builder().id(id).build();
		ConsultarServicioPorIdFachada fachada = new ConsultarServicioPorIdFachadaImpl();
		var resultado = fachada.ejecutar(servicio);

		return new ResponseEntity<>(RespuestaExito.crear("El servicio se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<ServicioDTO>>> consultarServicios() {
		ConsultarTodosServiciosFachada fachada = new ConsultarTodosServiciosFachadaImpl();
		var resultado = fachada.ejecutar(new ServicioDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Servicios consultados exitosamente.", resultado), HttpStatus.OK);
	}

	@PatchMapping("/{id}/activar")
	public ResponseEntity<RespuestaExito<String>> activarServicioExistente(@PathVariable UUID id) {
		var servicio = new ServicioDTO.Builder().id(id).build();
		ActivarServicioFachada fachada = new ActivarServicioFachadaImpl();
		fachada.ejecutar(servicio);

		return new ResponseEntity<>(RespuestaExito.crear("El servicio se ha activado exitosamente.", ""), HttpStatus.OK);
	}

	@PatchMapping("/{id}/desactivar")
	public ResponseEntity<RespuestaExito<String>> desactivarServicioExistente(@PathVariable UUID id) {
		var servicio = new ServicioDTO.Builder().id(id).build();
		DesactivarServicioFachada fachada = new DesactivarServicioFachadaImpl();
		fachada.ejecutar(servicio);

		return new ResponseEntity<>(RespuestaExito.crear("El servicio se ha desactivado exitosamente.", ""), HttpStatus.OK);
	}

}
