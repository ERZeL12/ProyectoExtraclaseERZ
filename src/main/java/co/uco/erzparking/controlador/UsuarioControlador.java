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
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.negocio.fachada.usuario.ActualizarUsuarioFachada;
import co.uco.erzparking.negocio.fachada.usuario.ConsultarTodosUsuariosFachada;
import co.uco.erzparking.negocio.fachada.usuario.ConsultarUsuarioPorIdFachada;
import co.uco.erzparking.negocio.fachada.usuario.QuitarUsuarioFachada;
import co.uco.erzparking.negocio.fachada.usuario.RegistrarUsuarioFachada;
import co.uco.erzparking.negocio.fachada.usuario.impl.ActualizarUsuarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuario.impl.ConsultarTodosUsuariosFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuario.impl.ConsultarUsuarioPorIdFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuario.impl.QuitarUsuarioFachadaImpl;
import co.uco.erzparking.negocio.fachada.usuario.impl.RegistrarUsuarioFachadaImpl;

@RestController
@RequestMapping("/erzparking/v1/usuarios")
public class UsuarioControlador {

	@PostMapping
	public ResponseEntity<RespuestaExito<String>> registrarNuevoUsuario(@RequestBody UsuarioDTO usuario) {
		RegistrarUsuarioFachada fachada = new RegistrarUsuarioFachadaImpl();
		fachada.ejecutar(usuario);

		return new ResponseEntity<>(RespuestaExito.crear("El usuario se ha registrado exitosamente.", ""), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> modificarInformacionUsuarioExistente(@PathVariable UUID id, @RequestBody UsuarioDTO usuario) {
		var usuarioConId = new UsuarioDTO.Builder()
				.id(id)
				.tipoDocumentoIdentificacion(usuario.getTipoDocumentoIdentificacion())
				.numeroIdentificacion(usuario.getNumeroIdentificacion())
				.primerNombre(usuario.getPrimerNombre())
				.segundoNombre(usuario.getSegundoNombre())
				.primerApellido(usuario.getPrimerApellido())
				.segundoApellido(usuario.getSegundoApellido())
				.numeroTelefonico(usuario.getNumeroTelefonico())
				.correoElectronico(usuario.getCorreoElectronico())
				.ciudad(usuario.getCiudad())
				.build();

		ActualizarUsuarioFachada fachada = new ActualizarUsuarioFachadaImpl();
		fachada.ejecutar(usuarioConId);

		return new ResponseEntity<>(RespuestaExito.crear("El usuario se ha actualizado exitosamente.", ""), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaExito<String>> darDeBajaUsuarioExistente(@PathVariable UUID id) {
		var usuario = new UsuarioDTO.Builder().id(id).build();
		QuitarUsuarioFachada fachada = new QuitarUsuarioFachadaImpl();
		fachada.ejecutar(usuario);

		return new ResponseEntity<>(RespuestaExito.crear("El usuario se ha eliminado exitosamente.", ""), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaExito<UsuarioDTO>> consultarUsuarioPorId(@PathVariable UUID id) {
		var usuario = new UsuarioDTO.Builder().id(id).build();
		ConsultarUsuarioPorIdFachada fachada = new ConsultarUsuarioPorIdFachadaImpl();
		var resultado = fachada.ejecutar(usuario);

		return new ResponseEntity<>(RespuestaExito.crear("El usuario se ha consultado exitosamente.", resultado), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<RespuestaExito<List<UsuarioDTO>>> consultarUsuarios() {
		ConsultarTodosUsuariosFachada fachada = new ConsultarTodosUsuariosFachadaImpl();
		var resultado = fachada.ejecutar(new UsuarioDTO.Builder().build());

		return new ResponseEntity<>(RespuestaExito.crear("Usuarios consultados exitosamente.", resultado), HttpStatus.OK);
	}

}
