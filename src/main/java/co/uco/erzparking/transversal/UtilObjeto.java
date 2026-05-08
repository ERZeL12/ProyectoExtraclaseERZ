package co.uco.erzparking.transversal;

public final class UtilObjeto {

	private UtilObjeto() {
		super();
	}

	// <O> es un objeto generalizado, puede ser cualquier tipo de objeto
	public static <O> boolean esNulo(final O objeto) {
		return objeto == null;
	}

	public static <O> O obtenerValorDefecto(final O objeto, final O valorDefecto) {
		return esNulo(objeto) ? valorDefecto : objeto;
	}

}
