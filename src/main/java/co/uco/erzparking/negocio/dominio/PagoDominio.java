package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilLocalDateTime;
import co.uco.erzparking.transversal.UtilNumericoDecimal;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.time.LocalDateTime;
import java.util.UUID;


public class PagoDominio {

	private UUID id;
	private double montoTotal;
	private LocalDateTime fechaHoraPago;
	private MetodoPagoDominio metodoPago;
	private SalidaDominio salida;
	private OperarioDominio operario;
	private TarifaDominio tarifa;
	private AtencionVehiculoDominio atencionVehiculo;

	private PagoDominio(final Builder builder) {
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

	public MetodoPagoDominio getMetodoPago() {
		return metodoPago;
	}

	public SalidaDominio getSalida() {
		return salida;
	}

	public OperarioDominio getOperario() {
		return operario;
	}

	public TarifaDominio getTarifa() {
		return tarifa;
	}

	public AtencionVehiculoDominio getAtencionVehiculo() {
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

	private void setMetodoPago(final MetodoPagoDominio metodoPago) {
		this.metodoPago = UtilObjeto.obtenerValorDefecto(metodoPago, null);
	}

	private void setSalida(final SalidaDominio salida) {
		this.salida = UtilObjeto.obtenerValorDefecto(salida, null);
	}

	private void setOperario(final OperarioDominio operario) {
		this.operario = UtilObjeto.obtenerValorDefecto(operario, null);
	}

	private void setTarifa(final TarifaDominio tarifa) {
		this.tarifa = UtilObjeto.obtenerValorDefecto(tarifa, null);
	}

	private void setAtencionVehiculo(final AtencionVehiculoDominio atencionVehiculo) {
		this.atencionVehiculo = UtilObjeto.obtenerValorDefecto(atencionVehiculo, null);
	}

	public static class Builder {

		private UUID id;
		private double montoTotal;
		private LocalDateTime fechaHoraPago;
		private MetodoPagoDominio metodoPago;
		private SalidaDominio salida;
		private OperarioDominio operario;
		private TarifaDominio tarifa;
		private AtencionVehiculoDominio atencionVehiculo;

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

		public Builder metodoPago(final MetodoPagoDominio metodoPago) {
			this.metodoPago = metodoPago;
			return this;
		}

		public Builder salida(final SalidaDominio salida) {
			this.salida = salida;
			return this;
		}

		public Builder operario(final OperarioDominio operario) {
			this.operario = operario;
			return this;
		}

		public Builder tarifa(final TarifaDominio tarifa) {
			this.tarifa = tarifa;
			return this;
		}

		public Builder atencionVehiculo(final AtencionVehiculoDominio atencionVehiculo) {
			this.atencionVehiculo = atencionVehiculo;
			return this;
		}

		public PagoDominio build() {
			return new PagoDominio(this);
		}
	}

}
