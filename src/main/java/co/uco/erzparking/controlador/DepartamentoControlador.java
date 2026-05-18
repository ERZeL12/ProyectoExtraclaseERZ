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
import co.uco.erzparking.dto.DepartamentoDTO;
import co.uco.erzparking.negocio.fachada.departamento.ActualizarDepartamentoFachada;
import co.uco.erzparking.negocio.fachada.departamento.ConsultarDepartamentoPorIdFachada;
import co.uco.erzparking.negocio.fachada.departamento.ConsultarTodosDepartamentosFachada;
import co.uco.erzparking.negocio.fachada.departamento.QuitarDepartamentoFachada;
import co.uco.erzparking.negocio.fachada.departamento.RegistrarDepartamentoFachada;
import co.uco.erzparking.negocio.fachada.departamento.impl.ActualizarDepartamentoFachadaImpl;
import co.uco.erzparking.negocio.fachada.departamento.impl.ConsultarDepartamentoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.departamento.impl.ConsultarTodosDepartamentosFachadaImpl;
import co.uco.erzparking.negocio.fachada.departamento.impl.QuitarDepartamentoFachadaImpl;
import co.uco.erzparking.negocio.fachada.departamento.impl.RegistrarDepartamentoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/departamentos")
public class DepartamentoControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoDepartamento(@RequestBody DepartamentoDTO departamento) {
		RegistrarDepartamentoFachada fachada = new RegistrarDepartamentoFachadaImpl();
		fachada.ejecutar(departamento);

		return new ResponseEntity<>(RespuestaExito.crear("El departamento se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionDepartamentoExistente(@PathVariable UUID id, @RequestBody DepartamentoDTO departamento) {
		var departamentoConId = new DepartamentoDTO.Builder()
				.id(id)
				.nombre(departamento.getNombre())
				.pais(departamento.getPais())
				.build();

		ActualizarDepartamentoFachada fachada = new ActualizarDepartamentoFachadaImpl();
		fachada.ejecutar(departamentoConId);

		return new ResponseEntity<>(RespuestaExito.crear("El departamento se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaDepartamentoExistente(@PathVariable UUID id) {
		var departamento = new DepartamentoDTO.Builder().id(id).build();
		QuitarDepartamentoFachada fachada = new QuitarDepartamentoFachadaImpl();
		fachada.ejecutar(departamento);

		return new ResponseEntity<>(RespuestaExito.crear("El departamento se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<DepartamentoDTO>> consultarDepartamentoPorId(@PathVariable UUID id) {
		var departamento = new DepartamentoDTO.Builder().id(id).build();
		ConsultarDepartamentoPorIdFachada fachada = new ConsultarDepartamentoPorIdFachadaImpl();
		var resultado = fachada.ejecutar(departamento);

		return new ResponseEntity<>(RespuestaExito.crear("El departamento se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<DepartamentoDTO>>> consultarDepartamentos() {
		ConsultarTodosDepartamentosFachada fachada = new ConsultarTodosDepartamentosFachadaImpl();
		var resultado = fachada.ejecutar(new DepartamentoDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Departamentos consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
