package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilFecha;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.Date;
import java.util.UUID;


public class HistorialCargoDominio {

	private UUID id;
	private OperarioDominio operario;
	private CargoDominio cargo;
	private Date fechaInicioEjercicioCargo;
	private Date fechaFinEjercicioCargo;

	private HistorialCargoDominio(final Builder builder) {
		setId(builder.id);
		setOperario(builder.operario);
		setCargo(builder.cargo);
		setFechaInicioEjercicioCargo(builder.fechaInicioEjercicioCargo);
		setFechaFinEjercicioCargo(builder.fechaFinEjercicioCargo);
	}

	public UUID getId() {
		return id;
	}

	public OperarioDominio getOperario() {
		return operario;
	}

	public CargoDominio getCargo() {
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

	private void setOperario(final OperarioDominio operario) {
		this.operario = UtilObjeto.obtenerValorDefecto(operario, null);
	}

	private void setCargo(final CargoDominio cargo) {
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
		private OperarioDominio operario;
		private CargoDominio cargo;
		private Date fechaInicioEjercicioCargo;
		private Date fechaFinEjercicioCargo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder operario(final OperarioDominio operario) {
			this.operario = operario;
			return this;
		}

		public Builder cargo(final CargoDominio cargo) {
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

		public HistorialCargoDominio build() {
			return new HistorialCargoDominio(this);
		}
	}

}