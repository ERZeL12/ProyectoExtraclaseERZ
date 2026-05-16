package co.uco.erzparking.datos;

import java.util.UUID;

public interface ConsultarPorIdDAO<E> {
	E consultarPorId(UUID id);
}
