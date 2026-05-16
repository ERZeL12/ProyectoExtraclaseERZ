package co.uco.erzparking.datos.dao;

import co.uco.erzparking.datos.ConsultarPorFiltroDAO;
import co.uco.erzparking.datos.ConsultarPorIdDAO;
import co.uco.erzparking.datos.ConsultarTodosDAO;
import co.uco.erzparking.datos.CrearDAO;
import co.uco.erzparking.datos.EliminarDAO;
import co.uco.erzparking.entidad.TipoDocumentoIdentificacionEntidad;

public interface TipoDocumentoIdentificacionDAO extends CrearDAO<TipoDocumentoIdentificacionEntidad>, EliminarDAO, ConsultarPorIdDAO<TipoDocumentoIdentificacionEntidad>, ConsultarTodosDAO<TipoDocumentoIdentificacionEntidad>, ConsultarPorFiltroDAO<TipoDocumentoIdentificacionEntidad> {

}
