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
import co.uco.erzparking.dto.ZonaParqueaderoDTO;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ActualizarZonaParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ConsultarTodosZonaParqueaderosFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.ConsultarZonaParqueaderoPorIdFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.QuitarZonaParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.RegistrarZonaParqueaderoFachada;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.ActualizarZonaParqueaderoFachadaImpl;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.ConsultarTodosZonaParqueaderosFachadaImpl;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.ConsultarZonaParqueaderoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.QuitarZonaParqueaderoFachadaImpl;
import co.uco.erzparking.negocio.fachada.zonaparqueadero.impl.RegistrarZonaParqueaderoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/zonasparqueadero")
public class ZonaParqueaderoControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevaZonaParqueadero(@RequestBody ZonaParqueaderoDTO zona) {
		RegistrarZonaParqueaderoFachada fachada = new RegistrarZonaParqueaderoFachadaImpl();
		fachada.ejecutar(zona);

		return new ResponseEntity<>(RespuestaExito.crear("La zona de parqueadero se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionZonaParqueaderoExistente(@PathVariable UUID id, @RequestBody ZonaParqueaderoDTO zona) {
		var zonaConId = new ZonaParqueaderoDTO.Builder()
				.id(id)
				.nombreZona(zona.getNombreZona())
				.parqueadero(zona.getParqueadero())
				.build();

		ActualizarZonaParqueaderoFachada fachada = new ActualizarZonaParqueaderoFachadaImpl();
		fachada.ejecutar(zonaConId);

		return new ResponseEntity<>(RespuestaExito.crear("La zona de parqueadero se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaZonaParqueaderoExistente(@PathVariable UUID id) {
		var zona = new ZonaParqueaderoDTO.Builder().id(id).build();
		QuitarZonaParqueaderoFachada fachada = new QuitarZonaParqueaderoFachadaImpl();
		fachada.ejecutar(zona);

		return new ResponseEntity<>(RespuestaExito.crear("La zona de parqueadero se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<ZonaParqueaderoDTO>> consultarZonaParqueaderoPorId(@PathVariable UUID id) {
		var zona = new ZonaParqueaderoDTO.Builder().id(id).build();
		ConsultarZonaParqueaderoPorIdFachada fachada = new ConsultarZonaParqueaderoPorIdFachadaImpl();
		var resultado = fachada.ejecutar(zona);

		return new ResponseEntity<>(RespuestaExito.crear("La zona de parqueadero se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<ZonaParqueaderoDTO>>> consultarZonasParqueadero() {
		ConsultarTodosZonaParqueaderosFachada fachada = new ConsultarTodosZonaParqueaderosFachadaImpl();
		var resultado = fachada.ejecutar(new ZonaParqueaderoDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Zonas de parqueadero consultadas exitosamente.", resultado), HttpStatus.OK);
	}

}
