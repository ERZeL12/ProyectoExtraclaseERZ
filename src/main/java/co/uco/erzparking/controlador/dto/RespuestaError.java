package co.uco.erzparking.controlador.dto;

import java.time.LocalDateTime;

import co.uco.erzparking.transversal.UtilTexto;

public record RespuestaError(String mensaje, LocalDateTime fecha) {

	public static RespuestaError crear(final String mensaje) {
		return new RespuestaError(UtilTexto.aplicarTrim(mensaje), LocalDateTime.now());
	}

}
