package co.uco.erzparking.datos.dao;

import co.uco.erzparking.datos.ConsultarPorFiltroDAO;
import co.uco.erzparking.datos.ConsultarPorIdDAO;
import co.uco.erzparking.datos.ConsultarTodosDAO;
import co.uco.erzparking.datos.CrearDAO;
import co.uco.erzparking.datos.EliminarDAO;
import co.uco.erzparking.entidad.UsuarioVehiculoEntidad;

public interface UsuarioVehiculoDAO extends CrearDAO<UsuarioVehiculoEntidad>, EliminarDAO, ConsultarPorIdDAO<UsuarioVehiculoEntidad>, ConsultarTodosDAO<UsuarioVehiculoEntidad>, ConsultarPorFiltroDAO<UsuarioVehiculoEntidad> {

}
