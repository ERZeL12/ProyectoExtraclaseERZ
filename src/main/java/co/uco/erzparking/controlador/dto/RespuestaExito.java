package co.uco.erzparking.controlador.dto;

import java.time.LocalDateTime;

import co.uco.erzparking.transversal.UtilTexto;

public record RespuestaExito<T>(String mensaje, LocalDateTime fecha, T datos) {

	public static <T> RespuestaExito<T> crear(final String mensaje, final T datos) {
		return new RespuestaExito<>(UtilTexto.aplicarTrim(mensaje), LocalDateTime.now(), datos);
	}

}
