package co.uco.erzparking.transversal.excepcion;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;

public final class ERZParkingExcepcion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Throwable excepcionRaiz;
	private final String mensajeUsuario;
	private final String mensajeTecnico;

	private ERZParkingExcepcion(final Throwable excepcionRaiz, final String mensajeUsuario, final String mensajeTecnico) {
		super(mensajeTecnico);
		this.excepcionRaiz = UtilObjeto.obtenerValorDefecto(excepcionRaiz, new Exception());
		this.mensajeUsuario = UtilTexto.aplicarTrim(mensajeUsuario);
		this.mensajeTecnico = UtilTexto.aplicarTrim(mensajeTecnico);
	}

	public static ERZParkingExcepcion crear(final String mensajeUsuario) {
		return new ERZParkingExcepcion(new Exception(), mensajeUsuario, mensajeUsuario);
	}

	public static ERZParkingExcepcion crear(final String mensajeUsuario, final String mensajeTecnico) {
		return new ERZParkingExcepcion(new Exception(), mensajeUsuario, mensajeTecnico);
	}

	public static ERZParkingExcepcion crear(final Throwable excepcionRaiz, final String mensajeUsuario, final String mensajeTecnico) {
		return new ERZParkingExcepcion(excepcionRaiz, mensajeUsuario, mensajeTecnico);
	}

	public Throwable getExcepcionRaiz() {
		return excepcionRaiz;
	}

	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	public String getMensajeTecnico() {
		return mensajeTecnico;
	}

}
