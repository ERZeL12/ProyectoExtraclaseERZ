package co.uco.erzparking.transversal;

import java.time.LocalTime;

public final class UtilLocalTime {

	// Hora por defecto: 00:00:00
	public static final LocalTime VALOR_DEFECTO = LocalTime.of(0, 0, 0);

	private UtilLocalTime() {
		super();
	}

	public static boolean esNulo(final LocalTime hora) {
		return UtilObjeto.esNulo(hora);
	}

	public static LocalTime obtenerValorDefecto(final LocalTime hora) {
		return UtilObjeto.obtenerValorDefecto(hora, VALOR_DEFECTO);
	}

	public static LocalTime obtenerValorDefecto(final LocalTime hora, final LocalTime valorDefecto) {
		return UtilObjeto.obtenerValorDefecto(hora, valorDefecto);
	}

}
