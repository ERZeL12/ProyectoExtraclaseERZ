package co.uco.erzparking.negocio.fachada;

public interface FachadaConRetorno<E, S> {
	S ejecutar(E datos);
}
