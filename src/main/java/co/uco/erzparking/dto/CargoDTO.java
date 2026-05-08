package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class CargoDTO {

	private UUID id;
	private String nombreCargo;
	private String descripcion;
	private ParqueaderoDTO parqueadero;

	private CargoDTO(final Builder builder) {
		setId(builder.id);
		setNombreCargo(builder.nombreCargo);
		setDescripcion(builder.descripcion);
		setParqueadero(builder.parqueadero);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreCargo() {
		return nombreCargo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public ParqueaderoDTO getParqueadero() {
		return parqueadero;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreCargo(final String nombreCargo) {
		this.nombreCargo = UtilTexto.aplicarTrim(nombreCargo);
	}

	private void setDescripcion(final String descripcion) {
		this.descripcion = UtilTexto.aplicarTrim(descripcion);
	}

	private void setParqueadero(final ParqueaderoDTO parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	public static class Builder {

		private UUID id;
		private String nombreCargo;
		private String descripcion;
		private ParqueaderoDTO parqueadero;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreCargo(final String nombreCargo) {
			this.nombreCargo = UtilTexto.aplicarTrim(nombreCargo);
			return this;
		}

		public Builder descripcion(final String descripcion) {
			this.descripcion = UtilTexto.aplicarTrim(descripcion);
			return this;
		}

		public Builder parqueadero(final ParqueaderoDTO parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public CargoDTO build() {
			return new CargoDTO(this);
		}
	}

}