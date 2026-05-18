package co.uco.erzparking.negocio.casouso.entrada.impl;

import java.util.List;
import java.util.stream.Collectors;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.negocio.assembler.entidad.impl.EntradaEntidadAssembler;
import co.uco.erzparking.negocio.casouso.entrada.ConsultarTodosEntradasCasoUso;
import co.uco.erzparking.negocio.dominio.EntradaDominio;

public class ConsultarTodosEntradasCasoUsoImpl implements ConsultarTodosEntradasCasoUso {

	private DAOFactory daoFactory;

	public ConsultarTodosEntradasCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<EntradaDominio> ejecutar(final EntradaDominio datos) {
		var entidades = daoFactory.getEntradaDAO().consultarTodos();
		return entidades.stream()
				.map(EntradaEntidadAssembler.getInstance()::ensamblarDominio)
				.collect(Collectors.toList());
	}

}
