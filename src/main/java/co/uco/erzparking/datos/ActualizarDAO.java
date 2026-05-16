package co.uco.erzparking.datos;

import java.util.UUID;

public interface ActualizarDAO<E> {
	void actualizar(UUID id, E entidad);
}
