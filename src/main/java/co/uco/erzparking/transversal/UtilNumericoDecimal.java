package co.uco.erzparking.transversal;

public final class UtilNumericoDecimal {

	public static final double VALOR_DEFECTO = 0.0;

	private UtilNumericoDecimal() {
		super();
	}

	public static boolean esValorDefecto(final double valor) {
		return valor == VALOR_DEFECTO;
	}

	public static double obtenerValorDefecto(final double valor) {
		return valor;
	}

}
