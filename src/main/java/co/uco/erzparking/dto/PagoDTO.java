package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilLocalDateTime;
import co.uco.erzparking.transversal.UtilNumericoDecimal;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.time.LocalDateTime;
import java.util.UUID;


public class PagoDTO {

	private UUID id;
	private double montoTotal;
	private LocalDateTime fechaHoraPago;
	private MetodoPagoDTO metodoPago;
	private SalidaDTO salida;
	private OperarioDTO operario;
	private TarifaDTO tarifa;
	private AtencionVehiculoDTO atencionVehiculo;

	private PagoDTO(final Builder builder) {
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

	public MetodoPagoDTO getMetodoPago() {
		return metodoPago;
	}

	public SalidaDTO getSalida() {
		return salida;
	}

	public OperarioDTO getOperario() {
		return operario;
	}

	public TarifaDTO getTarifa() {
		return tarifa;
	}

	public AtencionVehiculoDTO getAtencionVehiculo() {
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

	private void setMetodoPago(final MetodoPagoDTO metodoPago) {
		this.metodoPago = UtilObjeto.obtenerValorDefecto(metodoPago, null);
	}

	private void setSalida(final SalidaDTO salida) {
		this.salida = UtilObjeto.obtenerValorDefecto(salida, null);
	}

	private void setOperario(final OperarioDTO operario) {
		this.operario = UtilObjeto.obtenerValorDefecto(operario, null);
	}

	private void setTarifa(final TarifaDTO tarifa) {
		this.tarifa = UtilObjeto.obtenerValorDefecto(tarifa, null);
	}

	private void setAtencionVehiculo(final AtencionVehiculoDTO atencionVehiculo) {
		this.atencionVehiculo = UtilObjeto.obtenerValorDefecto(atencionVehiculo, null);
	}

	public static class Builder {

		private UUID id;
		private double montoTotal;
		private LocalDateTime fechaHoraPago;
		private MetodoPagoDTO metodoPago;
		private SalidaDTO salida;
		private OperarioDTO operario;
		private TarifaDTO tarifa;
		private AtencionVehiculoDTO atencionVehiculo;

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

		public Builder metodoPago(final MetodoPagoDTO metodoPago) {
			this.metodoPago = metodoPago;
			return this;
		}

		public Builder salida(final SalidaDTO salida) {
			this.salida = salida;
			return this;
		}

		public Builder operario(final OperarioDTO operario) {
			this.operario = operario;
			return this;
		}

		public Builder tarifa(final TarifaDTO tarifa) {
			this.tarifa = tarifa;
			return this;
		}

		public Builder atencionVehiculo(final AtencionVehiculoDTO atencionVehiculo) {
			this.atencionVehiculo = atencionVehiculo;
			return this;
		}

		public PagoDTO build() {
			return new PagoDTO(this);
		}
	}

}
