package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilLocalDateTime;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.time.LocalDateTime;
import java.util.UUID;


public class EntradaEntidad {

	private UUID id;
	private LocalDateTime fechaHoraEntrada;
	private VehiculoEntidad vehiculo;
	private ServicioEntidad servicio;

	private EntradaEntidad(final Builder builder) {
		setId(builder.id);
		setFechaHoraEntrada(builder.fechaHoraEntrada);
		setVehiculo(builder.vehiculo);
		setServicio(builder.servicio);
	}

	public UUID getId() {
		return id;
	}

	public LocalDateTime getFechaHoraEntrada() {
		return fechaHoraEntrada;
	}

	public VehiculoEntidad getVehiculo() {
		return vehiculo;
	}

	public ServicioEntidad getServicio() {
		return servicio;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setFechaHoraEntrada(final LocalDateTime fechaHoraEntrada) {
		this.fechaHoraEntrada = UtilLocalDateTime.obtenerValorDefecto(fechaHoraEntrada);
	}

	private void setVehiculo(final VehiculoEntidad vehiculo) {
		this.vehiculo = UtilObjeto.obtenerValorDefecto(vehiculo, null);
	}

	private void setServicio(final ServicioEntidad servicio) {
		this.servicio = UtilObjeto.obtenerValorDefecto(servicio, null);
	}

	public static class Builder {

		private UUID id;
		private LocalDateTime fechaHoraEntrada;
		private VehiculoEntidad vehiculo;
		private ServicioEntidad servicio;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder fechaHoraEntrada(final LocalDateTime fechaHoraEntrada) {
			this.fechaHoraEntrada = UtilLocalDateTime.obtenerValorDefecto(fechaHoraEntrada);
			return this;
		}

		public Builder vehiculo(final VehiculoEntidad vehiculo) {
			this.vehiculo = vehiculo;
			return this;
		}

		public Builder servicio(final ServicioEntidad servicio) {
			this.servicio = servicio;
			return this;
		}

		public EntradaEntidad build() {
			return new EntradaEntidad(this);
		}
	}

}
