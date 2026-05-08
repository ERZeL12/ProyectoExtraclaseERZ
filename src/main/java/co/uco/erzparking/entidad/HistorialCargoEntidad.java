package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilFecha;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.Date;
import java.util.UUID;


public class HistorialCargoEntidad {

	private UUID id;
	private OperarioEntidad operario;
	private CargoEntidad cargo;
	private Date fechaInicioEjercicioCargo;
	private Date fechaFinEjercicioCargo;

	private HistorialCargoEntidad(final Builder builder) {
		setId(builder.id);
		setOperario(builder.operario);
		setCargo(builder.cargo);
		setFechaInicioEjercicioCargo(builder.fechaInicioEjercicioCargo);
		setFechaFinEjercicioCargo(builder.fechaFinEjercicioCargo);
	}

	public UUID getId() {
		return id;
	}

	public OperarioEntidad getOperario() {
		return operario;
	}

	public CargoEntidad getCargo() {
		return cargo;
	}

	public Date getFechaInicioEjercicioCargo() {
		return fechaInicioEjercicioCargo;
	}

	public Date getFechaFinEjercicioCargo() {
		return fechaFinEjercicioCargo;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setOperario(final OperarioEntidad operario) {
		this.operario = UtilObjeto.obtenerValorDefecto(operario, null);
	}

	private void setCargo(final CargoEntidad cargo) {
		this.cargo = UtilObjeto.obtenerValorDefecto(cargo, null);
	}

	private void setFechaInicioEjercicioCargo(final Date fechaInicioEjercicioCargo) {
		this.fechaInicioEjercicioCargo = UtilFecha.obtenerValorDefecto(fechaInicioEjercicioCargo);
	}

	private void setFechaFinEjercicioCargo(final Date fechaFinEjercicioCargo) {
		this.fechaFinEjercicioCargo = UtilFecha.obtenerValorDefecto(fechaFinEjercicioCargo);
	}

	public static class Builder {

		private UUID id;
		private OperarioEntidad operario;
		private CargoEntidad cargo;
		private Date fechaInicioEjercicioCargo;
		private Date fechaFinEjercicioCargo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder operario(final OperarioEntidad operario) {
			this.operario = operario;
			return this;
		}

		public Builder cargo(final CargoEntidad cargo) {
			this.cargo = cargo;
			return this;
		}

		public Builder fechaInicioEjercicioCargo(final Date fechaInicioEjercicioCargo) {
			this.fechaInicioEjercicioCargo = UtilFecha.obtenerValorDefecto(fechaInicioEjercicioCargo);
			return this;
		}

		public Builder fechaFinEjercicioCargo(final Date fechaFinEjercicioCargo) {
			this.fechaFinEjercicioCargo = UtilFecha.obtenerValorDefecto(fechaFinEjercicioCargo);
			return this;
		}

		public HistorialCargoEntidad build() {
			return new HistorialCargoEntidad(this);
		}
	}

}