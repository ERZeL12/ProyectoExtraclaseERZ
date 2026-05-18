package co.uco.erzparking.datos.dao;

import java.util.UUID;

import co.uco.erzparking.datos.ActualizarDAO;
import co.uco.erzparking.datos.ConsultarPorFiltroDAO;
import co.uco.erzparking.datos.ConsultarPorIdDAO;
import co.uco.erzparking.datos.ConsultarTodosDAO;
import co.uco.erzparking.datos.CrearDAO;
import co.uco.erzparking.datos.EliminarDAO;
import co.uco.erzparking.entidad.ServicioEntidad;

public interface ServicioDAO extends CrearDAO<ServicioEntidad>, ActualizarDAO<ServicioEntidad>, EliminarDAO, ConsultarPorIdDAO<ServicioEntidad>, ConsultarTodosDAO<ServicioEntidad>, ConsultarPorFiltroDAO<ServicioEntidad> {

	void cambiarEstadoActual(UUID id, boolean nuevoEstado);

}
