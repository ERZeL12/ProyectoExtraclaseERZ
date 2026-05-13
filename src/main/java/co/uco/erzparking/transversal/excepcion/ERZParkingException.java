package co.uco.erzparking.transversal.excepcion;

public class ERZParkingException extends Exception {

	private static final long serialVersionUID = 1L;

	public ERZParkingException(final String mensaje) {
		super(mensaje);
	}
    
	public ERZParkingException(final String mensaje, final Throwable causa) {
		super(mensaje, causa);
	}

}
