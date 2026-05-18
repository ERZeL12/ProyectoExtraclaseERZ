package co.uco.erzparking.datos.dao;

import java.util.UUID;

import co.uco.erzparking.datos.ActualizarDAO;
import co.uco.erzparking.datos.ConsultarPorFiltroDAO;
import co.uco.erzparking.datos.ConsultarPorIdDAO;
import co.uco.erzparking.datos.ConsultarTodosDAO;
import co.uco.erzparking.datos.CrearDAO;
import co.uco.erzparking.entidad.TarifaEntidad;

public interface TarifaDAO extends CrearDAO<TarifaEntidad>, ActualizarDAO<TarifaEntidad>, ConsultarPorIdDAO<TarifaEntidad>, ConsultarTodosDAO<TarifaEntidad>, ConsultarPorFiltroDAO<TarifaEntidad> {

	void cambiarEstadoActual(UUID id, boolean nuevoEstado);

}
