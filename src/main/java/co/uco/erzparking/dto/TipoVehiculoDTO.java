package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class TipoVehiculoDTO {

	private UUID id;
	private String nombreVehiculo;

	private TipoVehiculoDTO(final Builder builder) {
		setId(builder.id);
		setNombreVehiculo(builder.nombreVehiculo);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreVehiculo() {
		return nombreVehiculo;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreVehiculo(final String nombreVehiculo) {
		this.nombreVehiculo = UtilTexto.aplicarTrim(nombreVehiculo);
	}

	public static class Builder {

		private UUID id;
		private String nombreVehiculo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreVehiculo(final String nombreVehiculo) {
			this.nombreVehiculo = UtilTexto.aplicarTrim(nombreVehiculo);
			return this;
		}

		public TipoVehiculoDTO build() {
			return new TipoVehiculoDTO(this);
		}
	}

}
