package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class VehiculoDTO {

	private UUID id;
	private String placaVehiculo;
	private TipoVehiculoDTO tipoVehiculo;

	public VehiculoDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setPlacaVehiculo("");
		setTipoVehiculo(new TipoVehiculoDTO());
	}

	private VehiculoDTO(final Builder builder) {
		setId(builder.id);
		setPlacaVehiculo(builder.placaVehiculo);
		setTipoVehiculo(builder.tipoVehiculo);
	}

	public UUID getId() {
		return id;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public TipoVehiculoDTO getTipoVehiculo() {
		return tipoVehiculo;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setPlacaVehiculo(final String placaVehiculo) {
		this.placaVehiculo = UtilTexto.eliminarEspaciosInternos(placaVehiculo);
	}

	private void setTipoVehiculo(final TipoVehiculoDTO tipoVehiculo) {
		this.tipoVehiculo = UtilObjeto.obtenerValorDefecto(tipoVehiculo, null);
	}

	public static class Builder {

		private UUID id;
		private String placaVehiculo;
		private TipoVehiculoDTO tipoVehiculo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder placaVehiculo(final String placaVehiculo) {
			this.placaVehiculo = UtilTexto.eliminarEspaciosInternos(placaVehiculo);
			return this;
		}

		public Builder tipoVehiculo(final TipoVehiculoDTO tipoVehiculo) {
			this.tipoVehiculo = tipoVehiculo;
			return this;
		}

		public VehiculoDTO build() {
			return new VehiculoDTO(this);
		}
	}

}