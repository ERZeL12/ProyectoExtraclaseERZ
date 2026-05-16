package co.uco.erzparking.datos.dao;

import co.uco.erzparking.datos.ActualizarDAO;
import co.uco.erzparking.datos.ConsultarPorFiltroDAO;
import co.uco.erzparking.datos.ConsultarPorIdDAO;
import co.uco.erzparking.datos.ConsultarTodosDAO;
import co.uco.erzparking.datos.CrearDAO;
import co.uco.erzparking.entidad.ContratoMensualidadEntidad;

public interface ContratoMensualidadDAO extends CrearDAO<ContratoMensualidadEntidad>, ActualizarDAO<ContratoMensualidadEntidad>, ConsultarPorIdDAO<ContratoMensualidadEntidad>, ConsultarTodosDAO<ContratoMensualidadEntidad>, ConsultarPorFiltroDAO<ContratoMensualidadEntidad> {

}