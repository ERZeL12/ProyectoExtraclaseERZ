package co.uco.erzparking.datos.dao;

import co.uco.erzparking.datos.ActualizarDAO;
import co.uco.erzparking.datos.ConsultarPorFiltroDAO;
import co.uco.erzparking.datos.ConsultarPorIdDAO;
import co.uco.erzparking.datos.ConsultarTodosDAO;
import co.uco.erzparking.datos.CrearDAO;
import co.uco.erzparking.datos.EliminarDAO;
import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;

public interface EstadoEspacioFisicoDAO extends CrearDAO<EstadoEspacioFisicoEntidad>, ActualizarDAO<EstadoEspacioFisicoEntidad>, EliminarDAO, ConsultarPorIdDAO<EstadoEspacioFisicoEntidad>, ConsultarTodosDAO<EstadoEspacioFisicoEntidad>, ConsultarPorFiltroDAO<EstadoEspacioFisicoEntidad> {

}
