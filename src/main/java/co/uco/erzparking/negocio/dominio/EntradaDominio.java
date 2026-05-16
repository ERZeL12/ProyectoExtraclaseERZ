package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilLocalDateTime;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.time.LocalDateTime;
import java.util.UUID;

public class EntradaDominio {

	private UUID id;
	private LocalDateTime fechaHoraEntrada;
	private VehiculoDominio vehiculo;
	private ServicioDominio servicio;
	private OperarioDominio operario;

	private EntradaDominio(final Builder builder) {
		setId(builder.id);
		setFechaHoraEntrada(builder.fechaHoraEntrada);
		setVehiculo(builder.vehiculo);
		setServicio(builder.servicio);
		setOperario(builder.operario);
	}

	public UUID getId() {
		return id;
	}

	public LocalDateTime getFechaHoraEntrada() {
		return fechaHoraEntrada;
	}

	public VehiculoDominio getVehiculo() {
		return vehiculo;
	}

	public ServicioDominio getServicio() {
		return servicio;
	}

	public OperarioDominio getOperario() {
		return operario;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setFechaHoraEntrada(final LocalDateTime fechaHoraEntrada) {
		this.fechaHoraEntrada = UtilLocalDateTime.obtenerValorDefecto(fechaHoraEntrada);
	}

	private void setVehiculo(final VehiculoDominio vehiculo) {
		this.vehiculo = UtilObjeto.obtenerValorDefecto(vehiculo, null);
	}

	private void setServicio(final ServicioDominio servicio) {
		this.servicio = UtilObjeto.obtenerValorDefecto(servicio, null);
	}

	private void setOperario(final OperarioDominio operario) {
		this.operario = UtilObjeto.obtenerValorDefecto(operario, null);
	}

	public static class Builder {

		private UUID id;
		private LocalDateTime fechaHoraEntrada;
		private VehiculoDominio vehiculo;
		private ServicioDominio servicio;
		private OperarioDominio operario;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder fechaHoraEntrada(final LocalDateTime fechaHoraEntrada) {
			this.fechaHoraEntrada = UtilLocalDateTime.obtenerValorDefecto(fechaHoraEntrada);
			return this;
		}

		public Builder vehiculo(final VehiculoDominio vehiculo) {
			this.vehiculo = vehiculo;
			return this;
		}

		public Builder servicio(final ServicioDominio servicio) {
			this.servicio = servicio;
			return this;
		}

		public Builder operario(final OperarioDominio operario) {
			this.operario = operario;
			return this;
		}

		public EntradaDominio build() {
			return new EntradaDominio(this);
		}
	}

}