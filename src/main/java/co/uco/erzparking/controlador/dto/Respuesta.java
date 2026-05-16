package co.uco.erzparking.controlador.dto;

import java.util.ArrayList;
import java.util.List;

public class Respuesta<T> {

	private List<String> mensajes;
	private List<T> datos;
	private boolean exitosa;

	public Respuesta(final boolean exitosa) {
		setExitosa(exitosa);
		setMensajes(new ArrayList<>());
		setDatos(new ArrayList<>());
	}

	public static <T> Respuesta<T> crearRespuestaExitosa() {
		return new Respuesta<>(true);
	}

	public static <T> Respuesta<T> crearRespuestaFallida() {
		return new Respuesta<>(false);
	}

	public List<String> getMensajes() {
		return mensajes;
	}

	public void setMensajes(final List<String> mensajes) {
		this.mensajes = mensajes == null ? new ArrayList<>() : mensajes;
	}

	public void agregarMensaje(final String mensaje) {
		if (mensaje != null && !mensaje.trim().isEmpty()) {
			getMensajes().add(mensaje);
		}
	}

	public List<T> getDatos() {
		return datos;
	}

	public void setDatos(final List<T> datos) {
		this.datos = datos == null ? new ArrayList<>() : datos;
	}

	public boolean isExitosa() {
		return exitosa;
	}

	public void setExitosa(final boolean exitosa) {
		this.exitosa = exitosa;
	}

}
