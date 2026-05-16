package co.uco.erzparking.negocio.casouso.usuario.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.UsuarioDTO;
import co.uco.erzparking.negocio.casouso.usuario.QuitarUsuarioCasoUso;
import co.uco.erzparking.negocio.dominio.UsuarioDominio;
import co.uco.erzparking.negocio.fachada.usuario.impl.QuitarUsuarioFachadaImpl;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class QuitarUsuarioCasoUsoImpl implements QuitarUsuarioCasoUso {

	private DAOFactory daoFactory;

	public QuitarUsuarioCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UsuarioDominio datos) {
		validarIntegridadDatos(datos);
		validarExiste(datos.getId());
		quitar(datos);
	}

	private void validarIntegridadDatos(final UsuarioDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw ERZParkingExcepcion.crear("Los datos del usuario son obligatorios");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw ERZParkingExcepcion.crear("El identificador del usuario es obligatorio");
		}
	}

	private void validarExiste(final UUID id) {
		if (UtilObjeto.esNulo(daoFactory.getUsuarioDAO().consultarPorId(id))) {
			throw ERZParkingExcepcion.crear("El usuario no existe en el sistema");
		}
	}

	private void quitar(final UsuarioDominio datos) {
		daoFactory.getUsuarioDAO().eliminar(datos.getId());
	}
	
	public static void main(final String[] args) {
	    try {
	        var dto = new UsuarioDTO.Builder()
	                .id(UUID.fromString("987b6412-6801-4043-ae38-063ee8336da3"))
	                .build();
	        new QuitarUsuarioFachadaImpl().ejecutar(dto);
	        System.out.println("Usuario eliminado exitosamente.");
	    } catch (Exception e) {
	        System.err.println("Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
}
