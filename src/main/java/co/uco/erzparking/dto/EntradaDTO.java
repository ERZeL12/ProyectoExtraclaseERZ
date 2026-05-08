package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilLocalDateTime;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.time.LocalDateTime;
import java.util.UUID;


public class EntradaDTO {

	private UUID id;
	private LocalDateTime fechaHoraEntrada;
	private VehiculoDTO vehiculo;
	private ServicioDTO servicio;

	private EntradaDTO(final Builder builder) {
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

	public VehiculoDTO getVehiculo() {
		return vehiculo;
	}

	public ServicioDTO getServicio() {
		return servicio;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setFechaHoraEntrada(final LocalDateTime fechaHoraEntrada) {
		this.fechaHoraEntrada = UtilLocalDateTime.obtenerValorDefecto(fechaHoraEntrada);
	}

	private void setVehiculo(final VehiculoDTO vehiculo) {
		this.vehiculo = UtilObjeto.obtenerValorDefecto(vehiculo, null);
	}

	private void setServicio(final ServicioDTO servicio) {
		this.servicio = UtilObjeto.obtenerValorDefecto(servicio, null);
	}

	public static class Builder {

		private UUID id;
		private LocalDateTime fechaHoraEntrada;
		private VehiculoDTO vehiculo;
		private ServicioDTO servicio;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder fechaHoraEntrada(final LocalDateTime fechaHoraEntrada) {
			this.fechaHoraEntrada = UtilLocalDateTime.obtenerValorDefecto(fechaHoraEntrada);
			return this;
		}

		public Builder vehiculo(final VehiculoDTO vehiculo) {
			this.vehiculo = vehiculo;
			return this;
		}

		public Builder servicio(final ServicioDTO servicio) {
			this.servicio = servicio;
			return this;
		}

		public EntradaDTO build() {
			return new EntradaDTO(this);
		}
	}

}
