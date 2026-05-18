package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilFecha;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.Date;
import java.util.UUID;


public class TarifaDTO {

	private UUID id;
	private double valorServicio;
	private Date fechaInicioVigenciaTarifa;
	private Date fechaFinVigenciaTarifa;
	private TipoVehiculoDTO tipoVehiculo;
	private ServicioDTO servicio;
	private boolean estadoActual;

	public TarifaDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setValorServicio(0.0);
		setFechaInicioVigenciaTarifa(java.sql.Date.valueOf("0001-01-01"));
		setFechaFinVigenciaTarifa(java.sql.Date.valueOf("0001-01-01"));
		setTipoVehiculo(new TipoVehiculoDTO());
		setServicio(new ServicioDTO());
		setEstadoActual(true);
	}

	private TarifaDTO(final Builder builder) {
		setId(builder.id);
		setValorServicio(builder.valorServicio);
		setFechaInicioVigenciaTarifa(builder.fechaInicioVigenciaTarifa);
		setFechaFinVigenciaTarifa(builder.fechaFinVigenciaTarifa);
		setTipoVehiculo(builder.tipoVehiculo);
		setServicio(builder.servicio);
		setEstadoActual(builder.estadoActual);
	}

	public UUID getId() {
		return id;
	}

	public double getValorServicio() {
		return valorServicio;
	}

	public Date getFechaInicioVigenciaTarifa() {
		return fechaInicioVigenciaTarifa;
	}

	public Date getFechaFinVigenciaTarifa() {
		return fechaFinVigenciaTarifa;
	}

	public TipoVehiculoDTO getTipoVehiculo() {
		return tipoVehiculo;
	}

	public ServicioDTO getServicio() {
		return servicio;
	}

	public boolean isEstadoActual() {
		return estadoActual;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setValorServicio(final double valorServicio) {
		this.valorServicio = valorServicio;
	}

	private void setFechaInicioVigenciaTarifa(final Date fechaInicioVigenciaTarifa) {
		this.fechaInicioVigenciaTarifa = UtilFecha.obtenerValorDefecto(fechaInicioVigenciaTarifa);
	}

	private void setFechaFinVigenciaTarifa(final Date fechaFinVigenciaTarifa) {
		this.fechaFinVigenciaTarifa = UtilFecha.obtenerValorDefecto(fechaFinVigenciaTarifa);
	}

	private void setTipoVehiculo(final TipoVehiculoDTO tipoVehiculo) {
		this.tipoVehiculo = UtilObjeto.obtenerValorDefecto(tipoVehiculo, null);
	}

	private void setServicio(final ServicioDTO servicio) {
		this.servicio = UtilObjeto.obtenerValorDefecto(servicio, null);
	}

	private void setEstadoActual(final boolean estadoActual) {
		this.estadoActual = estadoActual;
	}

	public static class Builder {

		private UUID id;
		private double valorServicio;
		private Date fechaInicioVigenciaTarifa;
		private Date fechaFinVigenciaTarifa;
		private TipoVehiculoDTO tipoVehiculo;
		private ServicioDTO servicio;
		private boolean estadoActual = true;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder valorServicio(final double valorServicio) {
			this.valorServicio = valorServicio;
			return this;
		}

		public Builder fechaInicioVigenciaTarifa(final Date fechaInicioVigenciaTarifa) {
			this.fechaInicioVigenciaTarifa = UtilFecha.obtenerValorDefecto(fechaInicioVigenciaTarifa);
			return this;
		}

		public Builder fechaFinVigenciaTarifa(final Date fechaFinVigenciaTarifa) {
			this.fechaFinVigenciaTarifa = UtilFecha.obtenerValorDefecto(fechaFinVigenciaTarifa);
			return this;
		}

		public Builder tipoVehiculo(final TipoVehiculoDTO tipoVehiculo) {
			this.tipoVehiculo = tipoVehiculo;
			return this;
		}

		public Builder servicio(final ServicioDTO servicio) {
			this.servicio = servicio;
			return this;
		}

		public Builder estadoActual(final boolean estadoActual) {
			this.estadoActual = estadoActual;
			return this;
		}

		public TarifaDTO build() {
			return new TarifaDTO(this);
		}
	}

}