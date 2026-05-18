package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class TipoVehiculoEntidad {

	private UUID id;
	private String nombreVehiculo;
	private String descripcion;

	private TipoVehiculoEntidad(final Builder builder) {
		setId(builder.id);
		setNombreVehiculo(builder.nombreVehiculo);
		setDescripcion(builder.descripcion);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreVehiculo() {
		return nombreVehiculo;
	}
	
	public String getDescripcion() {
	    return descripcion;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreVehiculo(final String nombreVehiculo) {
		this.nombreVehiculo = UtilTexto.aplicarTrim(nombreVehiculo);
	}
	
	private void setDescripcion(final String descripcion) {
	    this.descripcion = UtilTexto.aplicarTrim(descripcion);
	}

	public static class Builder {

		private UUID id;
		private String nombreVehiculo;
		private String descripcion;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreVehiculo(final String nombreVehiculo) {
			this.nombreVehiculo = UtilTexto.aplicarTrim(nombreVehiculo);
			return this;
		}
		
		public Builder descripcion(final String descripcion) {
		    this.descripcion = UtilTexto.aplicarTrim(descripcion);
		    return this;
		}

		public TipoVehiculoEntidad build() {
			return new TipoVehiculoEntidad(this);
		}
	}

}
