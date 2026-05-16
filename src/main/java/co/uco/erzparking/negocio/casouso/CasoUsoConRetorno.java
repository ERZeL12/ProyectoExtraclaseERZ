package co.uco.erzparking.negocio.casouso;

public interface CasoUsoConRetorno<E, S> {
	S ejecutar(E datos);
}
