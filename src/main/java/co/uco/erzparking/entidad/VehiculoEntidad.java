package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class VehiculoEntidad {

	private UUID id;
	private String placaVehiculo;
	private TipoVehiculoEntidad tipoVehiculo;

	private VehiculoEntidad(final Builder builder) {
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

	public TipoVehiculoEntidad getTipoVehiculo() {
		return tipoVehiculo;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setPlacaVehiculo(final String placaVehiculo) {
		this.placaVehiculo = UtilTexto.eliminarEspaciosInternos(placaVehiculo);
	}

	private void setTipoVehiculo(final TipoVehiculoEntidad tipoVehiculo) {
		this.tipoVehiculo = UtilObjeto.obtenerValorDefecto(tipoVehiculo, null);
	}

	public static class Builder {

		private UUID id;
		private String placaVehiculo;
		private TipoVehiculoEntidad tipoVehiculo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder placaVehiculo(final String placaVehiculo) {
			this.placaVehiculo = UtilTexto.eliminarEspaciosInternos(placaVehiculo);
			return this;
		}

		public Builder tipoVehiculo(final TipoVehiculoEntidad tipoVehiculo) {
			this.tipoVehiculo = tipoVehiculo;
			return this;
		}

		public VehiculoEntidad build() {
			return new VehiculoEntidad(this);
		}
	}

}