package co.uco.erzparking.negocio.fachada.entrada.impl;

import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.dto.EntradaDTO;
import co.uco.erzparking.dto.OperarioDTO;
import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.dto.VehiculoDTO;
import co.uco.erzparking.negocio.assembler.dto.impl.EntradaDTOAssembler;
import co.uco.erzparking.negocio.casouso.entrada.RegistrarEntradaVehiculoCasoUso;
import co.uco.erzparking.negocio.casouso.entrada.impl.RegistrarEntradaVehiculoCasoUsoImpl;
import co.uco.erzparking.negocio.fachada.entrada.RegistrarEntradaVehiculoFachada;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarEntradaVehiculoFachadaImpl implements RegistrarEntradaVehiculoFachada {

	private DAOFactory daoFactory;
	private RegistrarEntradaVehiculoCasoUso casoUso;

	public RegistrarEntradaVehiculoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarEntradaVehiculoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final EntradaDTO datos) {
		try {
			daoFactory.iniciarTransaccion();
			var dominio = EntradaDTOAssembler.getInstance().ensamblarDominio(datos);
			casoUso.ejecutar(dominio);
			daoFactory.confirmarTransaccion();
		} catch (ERZParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw ERZParkingExcepcion.crear(excepcion, "Se presento un error inesperado al registrar la entrada del vehiculo", "Exception no controlada en fachada: " + excepcion.getMessage());
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	public static void main(final String[] args) {
		try {
			var dto = new EntradaDTO.Builder()
					.vehiculo(new VehiculoDTO.Builder()
							.id(UUID.fromString("be6c0e80-dcea-4fb0-b443-3b0d728a08a9"))
							.build())
					.servicio(new ServicioDTO.Builder()
							.id(UUID.fromString("54d2035f-120c-47e4-a54d-06b149349c11"))
							.build())
					.operario(new OperarioDTO.Builder()
							.id(UUID.fromString("4318b80c-b391-490c-a49e-e94dc3efd7c0"))
							.build())
					.build();
			new RegistrarEntradaVehiculoFachadaImpl().ejecutar(dto);
			System.out.println("Entrada del vehiculo registrada exitosamente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
