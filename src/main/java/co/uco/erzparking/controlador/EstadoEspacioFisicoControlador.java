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
import co.uco.erzparking.dto.EstadoEspacioFisicoDTO;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.ConsultarEstadoEspacioFisicoPorIdFachada;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.ConsultarTodosEstadoEspacioFisicosFachada;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.QuitarEstadoEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.RegistrarEstadoEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl.ConsultarEstadoEspacioFisicoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl.ConsultarTodosEstadoEspacioFisicosFachadaImpl;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl.QuitarEstadoEspacioFisicoFachadaImpl;
import co.uco.erzparking.negocio.fachada.estadoespaciofisico.impl.RegistrarEstadoEspacioFisicoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/estadosespaciofisico")
public class EstadoEspacioFisicoControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoEstadoEspacioFisico(@RequestBody EstadoEspacioFisicoDTO estado) {
		RegistrarEstadoEspacioFisicoFachada fachada = new RegistrarEstadoEspacioFisicoFachadaImpl();
		fachada.ejecutar(estado);

		return new ResponseEntity<>(RespuestaExito.crear("El estado de espacio fisico se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaEstadoEspacioFisicoExistente(@PathVariable UUID id) {
		var estado = new EstadoEspacioFisicoDTO.Builder().id(id).build();
		QuitarEstadoEspacioFisicoFachada fachada = new QuitarEstadoEspacioFisicoFachadaImpl();
		fachada.ejecutar(estado);

		return new ResponseEntity<>(RespuestaExito.crear("El estado de espacio fisico se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<EstadoEspacioFisicoDTO>> consultarEstadoEspacioFisicoPorId(@PathVariable UUID id) {
		var estado = new EstadoEspacioFisicoDTO.Builder().id(id).build();
		ConsultarEstadoEspacioFisicoPorIdFachada fachada = new ConsultarEstadoEspacioFisicoPorIdFachadaImpl();
		var resultado = fachada.ejecutar(estado);

		return new ResponseEntity<>(RespuestaExito.crear("El estado de espacio fisico se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<EstadoEspacioFisicoDTO>>> consultarEstadosEspacioFisico() {
		ConsultarTodosEstadoEspacioFisicosFachada fachada = new ConsultarTodosEstadoEspacioFisicosFachadaImpl();
		var resultado = fachada.ejecutar(new EstadoEspacioFisicoDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Estados de espacio fisico consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
