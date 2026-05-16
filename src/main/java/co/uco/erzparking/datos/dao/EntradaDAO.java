package co.uco.erzparking.datos.dao;

import co.uco.erzparking.datos.ConsultarPorFiltroDAO;
import co.uco.erzparking.datos.ConsultarPorIdDAO;
import co.uco.erzparking.datos.ConsultarTodosDAO;
import co.uco.erzparking.datos.CrearDAO;
import co.uco.erzparking.entidad.EntradaEntidad;

public interface EntradaDAO extends CrearDAO<EntradaEntidad>, ConsultarPorIdDAO<EntradaEntidad>, ConsultarTodosDAO<EntradaEntidad>, ConsultarPorFiltroDAO<EntradaEntidad> {

}
