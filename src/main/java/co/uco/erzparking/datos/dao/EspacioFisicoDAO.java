package co.uco.erzparking.datos.dao;

import co.uco.erzparking.datos.ActualizarDAO;
import co.uco.erzparking.datos.ConsultarPorFiltroDAO;
import co.uco.erzparking.datos.ConsultarPorIdDAO;
import co.uco.erzparking.datos.ConsultarTodosDAO;
import co.uco.erzparking.datos.CrearDAO;
import co.uco.erzparking.datos.EliminarDAO;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;

public interface EspacioFisicoDAO extends CrearDAO<EspacioFisicoEntidad>, ActualizarDAO<EspacioFisicoEntidad>, EliminarDAO, ConsultarPorIdDAO<EspacioFisicoEntidad>, ConsultarTodosDAO<EspacioFisicoEntidad>, ConsultarPorFiltroDAO<EspacioFisicoEntidad> {

}
