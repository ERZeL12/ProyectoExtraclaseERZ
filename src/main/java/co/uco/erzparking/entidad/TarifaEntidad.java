package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilFecha;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.Date;
import java.util.UUID;


public class TarifaEntidad {

	private UUID id;
	private double valorServicio;
	private Date fechaInicioVigenciaTarifa;
	private Date fechaFinVigenciaTarifa;
	private TipoVehiculoEntidad tipoVehiculo;
	private ServicioEntidad servicio;
	private boolean estadoActual;

	private TarifaEntidad(final Builder builder) {
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

	public TipoVehiculoEntidad getTipoVehiculo() {
		return tipoVehiculo;
	}

	public ServicioEntidad getServicio() {
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

	private void setTipoVehiculo(final TipoVehiculoEntidad tipoVehiculo) {
		this.tipoVehiculo = UtilObjeto.obtenerValorDefecto(tipoVehiculo, null);
	}

	private void setServicio(final ServicioEntidad servicio) {
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
		private TipoVehiculoEntidad tipoVehiculo;
		private ServicioEntidad servicio;
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

		public Builder tipoVehiculo(final TipoVehiculoEntidad tipoVehiculo) {
			this.tipoVehiculo = tipoVehiculo;
			return this;
		}

		public Builder servicio(final ServicioEntidad servicio) {
			this.servicio = servicio;
			return this;
		}

		public Builder estadoActual(final boolean estadoActual) {
			this.estadoActual = estadoActual;
			return this;
		}

		public TarifaEntidad build() {
			return new TarifaEntidad(this);
		}
	}

}