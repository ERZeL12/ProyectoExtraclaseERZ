package co.uco.erzparking.transversal;

public final class UtilNumericoEnteroLargo {

	public static final long VALOR_DEFECTO = 0L;

	private UtilNumericoEnteroLargo() {
		super();
	}

	public static boolean esValorDefecto(final long valor) {
		return valor == VALOR_DEFECTO;
	}

	public static long obtenerValorDefecto(final long valor) {
		return valor;
	}

}
