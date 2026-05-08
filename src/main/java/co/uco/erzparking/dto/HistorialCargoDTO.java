package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilFecha;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.Date;
import java.util.UUID;


public class HistorialCargoDTO {

	private UUID id;
	private OperarioDTO operario;
	private CargoDTO cargo;
	private Date fechaInicioEjercicioCargo;
	private Date fechaFinEjercicioCargo;

	private HistorialCargoDTO(final Builder builder) {
		setId(builder.id);
		setOperario(builder.operario);
		setCargo(builder.cargo);
		setFechaInicioEjercicioCargo(builder.fechaInicioEjercicioCargo);
		setFechaFinEjercicioCargo(builder.fechaFinEjercicioCargo);
	}

	public UUID getId() {
		return id;
	}

	public OperarioDTO getOperario() {
		return operario;
	}

	public CargoDTO getCargo() {
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

	private void setOperario(final OperarioDTO operario) {
		this.operario = UtilObjeto.obtenerValorDefecto(operario, null);
	}

	private void setCargo(final CargoDTO cargo) {
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
		private OperarioDTO operario;
		private CargoDTO cargo;
		private Date fechaInicioEjercicioCargo;
		private Date fechaFinEjercicioCargo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder operario(final OperarioDTO operario) {
			this.operario = operario;
			return this;
		}

		public Builder cargo(final CargoDTO cargo) {
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

		public HistorialCargoDTO build() {
			return new HistorialCargoDTO(this);
		}
	}

}