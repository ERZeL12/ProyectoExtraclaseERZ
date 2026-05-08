package co.uco.erzparking.transversal;

import java.time.LocalDateTime;

public final class UtilLocalDateTime {

	// FechaHora por defecto: 01/01/1900 00:00:00
	public static final LocalDateTime VALOR_DEFECTO = LocalDateTime.of(1900, 1, 1, 0, 0, 0);

	private UtilLocalDateTime() {
		super();
	}

	public static boolean esNulo(final LocalDateTime fechaHora) {
		return UtilObjeto.esNulo(fechaHora);
	}

	public static LocalDateTime obtenerValorDefecto(final LocalDateTime fechaHora) {
		return UtilObjeto.obtenerValorDefecto(fechaHora, VALOR_DEFECTO);
	}

	public static LocalDateTime obtenerValorDefecto(final LocalDateTime fechaHora, final LocalDateTime valorDefecto) {
		return UtilObjeto.obtenerValorDefecto(fechaHora, valorDefecto);
	}

}
