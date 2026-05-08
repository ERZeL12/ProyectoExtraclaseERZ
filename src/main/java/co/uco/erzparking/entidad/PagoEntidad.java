package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilLocalDateTime;
import co.uco.erzparking.transversal.UtilNumericoDecimal;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.time.LocalDateTime;
import java.util.UUID;


public class PagoEntidad {

	private UUID id;
	private double montoTotal;
	private LocalDateTime fechaHoraPago;
	private MetodoPagoEntidad metodoPago;
	private SalidaEntidad salida;
	private OperarioEntidad operario;
	private TarifaEntidad tarifa;
	private AtencionVehiculoEntidad atencionVehiculo;

	private PagoEntidad(final Builder builder) {
		setId(builder.id);
		setMontoTotal(builder.montoTotal);
		setFechaHoraPago(builder.fechaHoraPago);
		setMetodoPago(builder.metodoPago);
		setSalida(builder.salida);
		setOperario(builder.operario);
		setTarifa(builder.tarifa);
		setAtencionVehiculo(builder.atencionVehiculo);
	}

	public UUID getId() {
		return id;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public LocalDateTime getFechaHoraPago() {
		return fechaHoraPago;
	}

	public MetodoPagoEntidad getMetodoPago() {
		return metodoPago;
	}

	public SalidaEntidad getSalida() {
		return salida;
	}

	public OperarioEntidad getOperario() {
		return operario;
	}

	public TarifaEntidad getTarifa() {
		return tarifa;
	}

	public AtencionVehiculoEntidad getAtencionVehiculo() {
		return atencionVehiculo;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setMontoTotal(final double montoTotal) {
		this.montoTotal = UtilNumericoDecimal.obtenerValorDefecto(montoTotal);
	}

	private void setFechaHoraPago(final LocalDateTime fechaHoraPago) {
		this.fechaHoraPago = UtilLocalDateTime.obtenerValorDefecto(fechaHoraPago);
	}

	private void setMetodoPago(final MetodoPagoEntidad metodoPago) {
		this.metodoPago = UtilObjeto.obtenerValorDefecto(metodoPago, null);
	}

	private void setSalida(final SalidaEntidad salida) {
		this.salida = UtilObjeto.obtenerValorDefecto(salida, null);
	}

	private void setOperario(final OperarioEntidad operario) {
		this.operario = UtilObjeto.obtenerValorDefecto(operario, null);
	}

	private void setTarifa(final TarifaEntidad tarifa) {
		this.tarifa = UtilObjeto.obtenerValorDefecto(tarifa, null);
	}

	private void setAtencionVehiculo(final AtencionVehiculoEntidad atencionVehiculo) {
		this.atencionVehiculo = UtilObjeto.obtenerValorDefecto(atencionVehiculo, null);
	}

	public static class Builder {

		private UUID id;
		private double montoTotal;
		private LocalDateTime fechaHoraPago;
		private MetodoPagoEntidad metodoPago;
		private SalidaEntidad salida;
		private OperarioEntidad operario;
		private TarifaEntidad tarifa;
		private AtencionVehiculoEntidad atencionVehiculo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder montoTotal(final double montoTotal) {
			this.montoTotal = montoTotal;
			return this;
		}

		public Builder fechaHoraPago(final LocalDateTime fechaHoraPago) {
			this.fechaHoraPago = UtilLocalDateTime.obtenerValorDefecto(fechaHoraPago);
			return this;
		}

		public Builder metodoPago(final MetodoPagoEntidad metodoPago) {
			this.metodoPago = metodoPago;
			return this;
		}

		public Builder salida(final SalidaEntidad salida) {
			this.salida = salida;
			return this;
		}

		public Builder operario(final OperarioEntidad operario) {
			this.operario = operario;
			return this;
		}

		public Builder tarifa(final TarifaEntidad tarifa) {
			this.tarifa = tarifa;
			return this;
		}

		public Builder atencionVehiculo(final AtencionVehiculoEntidad atencionVehiculo) {
			this.atencionVehiculo = atencionVehiculo;
			return this;
		}

		public PagoEntidad build() {
			return new PagoEntidad(this);
		}
	}

}
