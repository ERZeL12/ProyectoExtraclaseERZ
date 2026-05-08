package co.uco.erzparking.transversal;

public final class UtilNumericoEntero {

	public static final int VALOR_DEFECTO = 0;

	private UtilNumericoEntero() {
		super();
	}

	public static boolean esValorDefecto(final int valor) {
		return valor == VALOR_DEFECTO;
	}

	public static int obtenerValorDefecto(final int valor) {
		return valor;
	}

}
