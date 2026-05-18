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
	private OperarioDTO operario;

	public EntradaDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setFechaHoraEntrada(LocalDateTime.of(1, 1, 1, 0, 0, 0));
		setVehiculo(new VehiculoDTO());
		setServicio(new ServicioDTO());
		setOperario(new OperarioDTO());
	}

	private EntradaDTO(final Builder builder) {
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

	public VehiculoDTO getVehiculo() {
		return vehiculo;
	}

	public ServicioDTO getServicio() {
		return servicio;
	}

	public OperarioDTO getOperario() {
		return operario;
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

	private void setOperario(final OperarioDTO operario) {
		this.operario = UtilObjeto.obtenerValorDefecto(operario, null);
	}

	public static class Builder {

		private UUID id;
		private LocalDateTime fechaHoraEntrada;
		private VehiculoDTO vehiculo;
		private ServicioDTO servicio;
		private OperarioDTO operario;

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

		public Builder operario(final OperarioDTO operario) {
			this.operario = operario;
			return this;
		}

		public EntradaDTO build() {
			return new EntradaDTO(this);
		}
	}

}