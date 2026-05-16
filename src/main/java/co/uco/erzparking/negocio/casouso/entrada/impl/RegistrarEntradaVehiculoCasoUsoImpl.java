package co.uco.erzparking.negocio.casouso.entrada.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import co.uco.erzparking.datos.dao.sql.factoria.DAOFactory;
import co.uco.erzparking.datos.dao.sql.sqlserver.ContratoMensualidadSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.EntradaSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.EspacioFisicoSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.TarifaSQLServerDAO;
import co.uco.erzparking.datos.dao.sql.sqlserver.UsuarioVehiculoSQLServerDAO;
import co.uco.erzparking.entidad.EntradaEntidad;
import co.uco.erzparking.entidad.EspacioFisicoEntidad;
import co.uco.erzparking.entidad.EstadoEspacioFisicoEntidad;
import co.uco.erzparking.entidad.OperarioEntidad;
import co.uco.erzparking.entidad.ServicioEntidad;
import co.uco.erzparking.entidad.VehiculoEntidad;
import co.uco.erzparking.negocio.casouso.entrada.RegistrarEntradaVehiculoCasoUso;
import co.uco.erzparking.negocio.dominio.EntradaDominio;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.excepcion.ERZParkingExcepcion;

public class RegistrarEntradaVehiculoCasoUsoImpl implements RegistrarEntradaVehiculoCasoUso {

	private DAOFactory daoFactory;

	public RegistrarEntradaVehiculoCasoUsoImpl(final DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final EntradaDominio datos) {
		validarIntegridadDatos(datos);
		validarOperario(datos);
		var vehiculo = consultarYValidarVehiculo(datos);
		validarVehiculoTieneUsuarioRegistrado(vehiculo);
		validarVehiculoSinEntradaActiva(datos);
		validarTarifaVigente(vehiculo, datos);
		var espacioAsignado = determinarEspacioFisico(datos, vehiculo);
		validarEspacioDisponible(espacioAsignado);
		guardarEntrada(datos, espacioAsignado, vehiculo);
		actualizarEstadoEspacio(espacioAsignado);
	}

	// 1. Validar integridad de los datos recibidos
	private void validarIntegridadDatos(final EntradaDominio entrada) {
		if (UtilObjeto.esNulo(entrada)) {
			throw ERZParkingExcepcion.crear("Los datos de la entrada son obligatorios");
		}
		if (UtilObjeto.esNulo(entrada.getVehiculo()) || UtilObjeto.esNulo(entrada.getVehiculo().getId())) {
			throw ERZParkingExcepcion.crear("El vehiculo es obligatorio para registrar una entrada");
		}
		if (UtilObjeto.esNulo(entrada.getServicio()) || UtilObjeto.esNulo(entrada.getServicio().getId())) {
			throw ERZParkingExcepcion.crear("El servicio es obligatorio para registrar una entrada");
		}
		if (UtilObjeto.esNulo(entrada.getOperario()) || UtilObjeto.esNulo(entrada.getOperario().getId())) {
			throw ERZParkingExcepcion.crear("El operario es obligatorio para registrar una entrada");
		}
	}

	// 2. Validar que el operario exista en el sistema
	private void validarOperario(final EntradaDominio entrada) {
		var operario = daoFactory.getOperarioDAO().consultarPorId(entrada.getOperario().getId());
		if (UtilObjeto.esNulo(operario)) {
			throw ERZParkingExcepcion.crear("El operario no se encuentra registrado en el sistema", "Operario con id " + entrada.getOperario().getId() + " no encontrado en BD");
		}
	}

	// 3. Consultar el vehiculo completo desde BD y validar que exista
	private VehiculoEntidad consultarYValidarVehiculo(final EntradaDominio entrada) {
		var vehiculo = daoFactory.getVehiculoDAO().consultarPorId(entrada.getVehiculo().getId());
		if (UtilObjeto.esNulo(vehiculo)) {
			throw ERZParkingExcepcion.crear("El vehiculo no se encuentra registrado en el sistema", "Vehiculo con id " + entrada.getVehiculo().getId() + " no encontrado en BD");
		}
		if (UtilObjeto.esNulo(vehiculo.getTipoVehiculo())) {
			throw ERZParkingExcepcion.crear("El vehiculo no tiene un tipo de vehiculo asociado", "TipoVehiculo nulo para vehiculo " + vehiculo.getId());
		}
		return vehiculo;
	}

	// 4. Validar que el vehiculo tenga un usuario dueno registrado
	private void validarVehiculoTieneUsuarioRegistrado(final VehiculoEntidad vehiculo) {
		var usuarioVehiculoDAO = (UsuarioVehiculoSQLServerDAO) daoFactory.getUsuarioVehiculoDAO();
		var asociacion = usuarioVehiculoDAO.consultarPorVehiculo(vehiculo.getId());
		if (UtilObjeto.esNulo(asociacion)) {
			throw ERZParkingExcepcion.crear("El vehiculo no tiene un usuario propietario registrado en el sistema. No puede ingresar al parqueadero", "UsuarioVehiculo no encontrado para vehiculo " + vehiculo.getId());
		}
	}

	// 5. Regla: Un vehiculo no puede tener una entrada activa sin salida registrada
	private void validarVehiculoSinEntradaActiva(final EntradaDominio entrada) {
		var entradaDAO = (EntradaSQLServerDAO) daoFactory.getEntradaDAO();
		var entradaActiva = entradaDAO.consultarEntradaActivaPorVehiculo(entrada.getVehiculo().getId());
		if (!UtilObjeto.esNulo(entradaActiva)) {
			throw ERZParkingExcepcion.crear("El vehiculo ya tiene una entrada activa sin salida registrada. No puede ingresar dos veces al mismo tiempo", "Entrada activa encontrada con id " + entradaActiva.getId());
		}
	}

	// 6. Regla: Debe existir una tarifa vigente para el tipo de vehiculo y el servicio solicitado
	private void validarTarifaVigente(final VehiculoEntidad vehiculo, final EntradaDominio entrada) {
		var tarifaDAO = (TarifaSQLServerDAO) daoFactory.getTarifaDAO();
		var tarifaVigente = tarifaDAO.consultarTarifaVigente(vehiculo.getTipoVehiculo().getId(), entrada.getServicio().getId());
		if (UtilObjeto.esNulo(tarifaVigente)) {
			throw ERZParkingExcepcion.crear("No existe una tarifa vigente para el tipo de vehiculo y el servicio solicitado en la fecha actual", "Tarifa no encontrada para tipoVehiculo " + vehiculo.getTipoVehiculo().getId() + " y servicio " + entrada.getServicio().getId());
		}
	}

	// 7. Determinar el espacio fisico a asignar
	private EspacioFisicoEntidad determinarEspacioFisico(final EntradaDominio entrada, final VehiculoEntidad vehiculo) {
		var contratoDAO = (ContratoMensualidadSQLServerDAO) daoFactory.getContratoMensualidadDAO();
		var contratoVigente = contratoDAO.consultarContratoVigentePorVehiculo(vehiculo.getId());

		if (!UtilObjeto.esNulo(contratoVigente)) {
			var espacioCompleto = daoFactory.getEspacioFisicoDAO().consultarPorId(contratoVigente.getEspacioFisico().getId());
			if (UtilObjeto.esNulo(espacioCompleto)) {
				throw ERZParkingExcepcion.crear("El espacio reservado del contrato de mensualidad no fue encontrado", "EspacioFisico con id " + contratoVigente.getEspacioFisico().getId() + " no encontrado");
			}
			return espacioCompleto;
		}

		var espacioFisicoDAO = (EspacioFisicoSQLServerDAO) daoFactory.getEspacioFisicoDAO();
		var espaciosDisponibles = espacioFisicoDAO.consultarDisponiblesPorTipoServicio(entrada.getServicio().getId());

		if (UtilObjeto.esNulo(espaciosDisponibles) || espaciosDisponibles.isEmpty()) {
			throw ERZParkingExcepcion.crear("No hay espacios fisicos disponibles para el servicio solicitado", "Sin espacios disponibles para servicio " + entrada.getServicio().getId());
		}

		return espaciosDisponibles.get(0);
	}

	// 8. Regla: El espacio fisico debe estar en estado DISPONIBLE
	private void validarEspacioDisponible(final EspacioFisicoEntidad espacioFisico) {
		if (UtilObjeto.esNulo(espacioFisico)
				|| UtilObjeto.esNulo(espacioFisico.getEstadoEspacioFisico())
				|| !"DISPONIBLE".equals(espacioFisico.getEstadoEspacioFisico().getNombreEstadoEspacioFisico())) {
			throw ERZParkingExcepcion.crear("El espacio fisico no se encuentra en estado DISPONIBLE", "Estado del espacio: " + (UtilObjeto.esNulo(espacioFisico) || UtilObjeto.esNulo(espacioFisico.getEstadoEspacioFisico()) ? "nulo" : espacioFisico.getEstadoEspacioFisico().getNombreEstadoEspacioFisico()));
		}
	}

	// Generar id unico verificando en BD
	private UUID generarIdUnicoNuevaEntrada() {
		var entradaDAO = (EntradaSQLServerDAO) daoFactory.getEntradaDAO();
		var id = UUID.randomUUID();
		while (!UtilObjeto.esNulo(entradaDAO.consultarPorId(id))) {
			id = UUID.randomUUID();
		}
		return id;
	}

	// Guardar la entrada en BD con el operario
	private void guardarEntrada(final EntradaDominio entrada, final EspacioFisicoEntidad espacioAsignado, final VehiculoEntidad vehiculo) {
		var idNuevaEntrada = generarIdUnicoNuevaEntrada();
		var vehiculoEntidad = new VehiculoEntidad.Builder()
				.id(vehiculo.getId())
				.placaVehiculo(vehiculo.getPlacaVehiculo())
				.tipoVehiculo(vehiculo.getTipoVehiculo())
				.build();
		var servicioEntidad = new ServicioEntidad.Builder()
				.id(entrada.getServicio().getId())
				.build();
		var operarioEntidad = new OperarioEntidad.Builder()
				.id(entrada.getOperario().getId())
				.build();
		var entradaEntidad = new EntradaEntidad.Builder()
				.id(idNuevaEntrada)
				.fechaHoraEntrada(LocalDateTime.now())
				.vehiculo(vehiculoEntidad)
				.servicio(servicioEntidad)
				.operario(operarioEntidad)
				.build();
		daoFactory.getEntradaDAO().crear(entradaEntidad);
	}

	// Actualizar el estado del espacio fisico a OCUPADO
	private void actualizarEstadoEspacio(final EspacioFisicoEntidad espacioFisico) {
		var estadoOcupado = new EstadoEspacioFisicoEntidad.Builder()
				.nombreEstadoEspacioFisico("OCUPADO")
				.build();
		var espacioActualizado = new EspacioFisicoEntidad.Builder()
				.id(espacioFisico.getId())
				.estadoEspacioFisico(estadoOcupado)
				.build();
		daoFactory.getEspacioFisicoDAO().actualizar(espacioFisico.getId(), espacioActualizado);
	}

}