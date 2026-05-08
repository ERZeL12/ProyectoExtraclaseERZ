package co.uco.erzparking.transversal;

import java.util.Date;

public final class UtilFecha {

	// Fecha por defecto: 01/01/1900 — valor coherente que nunca se usara en el negocio
	public static final Date VALOR_DEFECTO = new Date(-2208988800000L);

	private UtilFecha() {
		super();
	}

	public static boolean esNula(final Date fecha) {
		return UtilObjeto.esNulo(fecha);
	}

	public static Date obtenerValorDefecto(final Date fecha) {
		return UtilObjeto.obtenerValorDefecto(fecha, VALOR_DEFECTO);
	}

	public static Date obtenerValorDefecto(final Date fecha, final Date valorDefecto) {
		return UtilObjeto.obtenerValorDefecto(fecha, valorDefecto);
	}

}
