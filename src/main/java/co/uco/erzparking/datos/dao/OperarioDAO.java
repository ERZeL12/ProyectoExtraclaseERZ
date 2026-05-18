package co.uco.erzparking.datos.dao;

import java.util.UUID;

import co.uco.erzparking.datos.ActualizarDAO;
import co.uco.erzparking.datos.ConsultarPorFiltroDAO;
import co.uco.erzparking.datos.ConsultarPorIdDAO;
import co.uco.erzparking.datos.ConsultarTodosDAO;
import co.uco.erzparking.datos.CrearDAO;
import co.uco.erzparking.datos.EliminarDAO;
import co.uco.erzparking.entidad.OperarioEntidad;

public interface OperarioDAO extends CrearDAO<OperarioEntidad>, ActualizarDAO<OperarioEntidad>, EliminarDAO, ConsultarPorIdDAO<OperarioEntidad>, ConsultarTodosDAO<OperarioEntidad>, ConsultarPorFiltroDAO<OperarioEntidad> {

	void cambiarEstadoActual(UUID id, boolean nuevoEstado);

	OperarioEntidad consultarPorNumeroIdentificacion(String numeroIdentificacion);

}