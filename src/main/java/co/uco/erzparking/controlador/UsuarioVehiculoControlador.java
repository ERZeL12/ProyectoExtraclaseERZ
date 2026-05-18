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
import co.uco.erzparking.dto.UsuarioVehiculoDTO;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.ConsultarTodosUsuarioVehiculosFachada;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.ConsultarUsuarioVehiculoPorIdFachada;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.QuitarUsuarioVehiculoFachada;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.RegistrarUsuarioVehiculoFachada;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.impl.ConsultarTodosUsuarioVehiculosFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.impl.ConsultarUsuarioVehiculoPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.impl.QuitarUsuarioVehiculoFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuariovehiculo.impl.RegistrarUsuarioVehiculoFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/usuariosvehiculo")
public class UsuarioVehiculoControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevaAsociacionUsuarioVehiculo(@RequestBody UsuarioVehiculoDTO usuarioVehiculo) {
		RegistrarUsuarioVehiculoFachada fachada = new RegistrarUsuarioVehiculoFachadaImpl();
		fachada.ejecutar(usuarioVehiculo);

		return new ResponseEntity<>(RespuestaExito.crear("La asociacion usuario-vehiculo se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaAsociacionUsuarioVehiculoExistente(@PathVariable UUID id) {
		var usuarioVehiculo = new UsuarioVehiculoDTO.Builder().id(id).build();
		QuitarUsuarioVehiculoFachada fachada = new QuitarUsuarioVehiculoFachadaImpl();
		fachada.ejecutar(usuarioVehiculo);

		return new ResponseEntity<>(RespuestaExito.crear("La asociacion usuario-vehiculo se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<UsuarioVehiculoDTO>> consultarAsociacionUsuarioVehiculoPorId(@PathVariable UUID id) {
		var usuarioVehiculo = new UsuarioVehiculoDTO.Builder().id(id).build();
		ConsultarUsuarioVehiculoPorIdFachada fachada = new ConsultarUsuarioVehiculoPorIdFachadaImpl();
		var resultado = fachada.ejecutar(usuarioVehiculo);

		return new ResponseEntity<>(RespuestaExito.crear("La asociacion usuario-vehiculo se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<UsuarioVehiculoDTO>>> consultarAsociacionesUsuarioVehiculo() {
		ConsultarTodosUsuarioVehiculosFachada fachada = new ConsultarTodosUsuarioVehiculosFachadaImpl();
		var resultado = fachada.ejecutar(new UsuarioVehiculoDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Asociaciones usuario-vehiculo consultadas exitosamente.", resultado), HttpStatus.OK);
	}

}
