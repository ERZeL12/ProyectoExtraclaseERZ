package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class TipoVehiculoDominio {

	private UUID id;
	private String nombreVehiculo;

	private TipoVehiculoDominio(final Builder builder) {
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

		public TipoVehiculoDominio build() {
			return new TipoVehiculoDominio(this);
		}
	}

}
