package co.uco.erzparking.transversal.excepcion;

public class ERZParkingRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ERZParkingRuntimeException(final String mensaje) {
		super(mensaje);
	}

	public ERZParkingRuntimeException(final String mensaje, final Throwable causa) {
		super(mensaje, causa);
	}

}
