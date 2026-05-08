package co.uco.erzparking.transversal;

import java.util.UUID;

public final class UtilUUID {

	public static final UUID VALOR_DEFECTO = UUID.fromString("00000000-0000-0000-0000-000000000000");

	private UtilUUID() {
		super();
	}

	public static boolean esNulo(final UUID uuid) {
		return UtilObjeto.esNulo(uuid);
	}

	public static UUID obtenerValorDefecto(final UUID uuid) {
		return UtilObjeto.obtenerValorDefecto(uuid, VALOR_DEFECTO);
	}

	public static UUID obtenerValorDefecto(final UUID uuid, final UUID valorDefecto) {
		return UtilObjeto.obtenerValorDefecto(uuid, valorDefecto);
	}

	public static UUID generarNuevoUUID() {
		return UUID.randomUUID();
	}

}
