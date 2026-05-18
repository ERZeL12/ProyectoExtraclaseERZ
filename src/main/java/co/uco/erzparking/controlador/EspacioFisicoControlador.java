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
import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.negocio.fachada.espaciofisico.ActualizarEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.ConsultarEspacioFisicoPorIdFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.ConsultarTodosEspacioFisicosFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.QuitarEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.RegistrarEspacioFisicoFachada;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.ActualizarEspacioFisicoFachadaImpl;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.ConsultarEspacioFisicoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.ConsultarTodosEspacioFisicosFachadaImpl;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.QuitarEspacioFisicoFachadaImpl;
import co.uco.erzparking.negocio.fachada.espaciofisico.impl.RegistrarEspacioFisicoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/espaciosfisicos")
public class EspacioFisicoControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoEspacioFisico(@RequestBody EspacioFisicoDTO espacio) {
		RegistrarEspacioFisicoFachada fachada = new RegistrarEspacioFisicoFachadaImpl();
		fachada.ejecutar(espacio);

		return new ResponseEntity<>(RespuestaExito.crear("El espacio fisico se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionEspacioFisicoExistente(@PathVariable UUID id, @RequestBody EspacioFisicoDTO espacio) {
		var espacioConId = new EspacioFisicoDTO.Builder()
				.id(id)
				.numeroEspacioFisico(espacio.getNumeroEspacioFisico())
				.tipoServicio(espacio.getTipoServicio())
				.estadoEspacioFisico(espacio.getEstadoEspacioFisico())
				.zonaEspacioFisico(espacio.getZonaEspacioFisico())
				.parqueadero(espacio.getParqueadero())
				.build();

		ActualizarEspacioFisicoFachada fachada = new ActualizarEspacioFisicoFachadaImpl();
		fachada.ejecutar(espacioConId);

		return new ResponseEntity<>(RespuestaExito.crear("El espacio fisico se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaEspacioFisicoExistente(@PathVariable UUID id) {
		var espacio = new EspacioFisicoDTO.Builder().id(id).build();
		QuitarEspacioFisicoFachada fachada = new QuitarEspacioFisicoFachadaImpl();
		fachada.ejecutar(espacio);

		return new ResponseEntity<>(RespuestaExito.crear("El espacio fisico se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<EspacioFisicoDTO>> consultarEspacioFisicoPorId(@PathVariable UUID id) {
		var espacio = new EspacioFisicoDTO.Builder().id(id).build();
		ConsultarEspacioFisicoPorIdFachada fachada = new ConsultarEspacioFisicoPorIdFachadaImpl();
		var resultado = fachada.ejecutar(espacio);

		return new ResponseEntity<>(RespuestaExito.crear("El espacio fisico se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<EspacioFisicoDTO>>> consultarEspaciosFisicos() {
		ConsultarTodosEspacioFisicosFachada fachada = new ConsultarTodosEspacioFisicosFachadaImpl();
		var resultado = fachada.ejecutar(new EspacioFisicoDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Espacios fisicos consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
