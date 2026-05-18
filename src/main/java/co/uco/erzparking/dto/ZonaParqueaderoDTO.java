package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class ZonaParqueaderoDTO {

	private UUID id;
	private String nombreZona;
	private ParqueaderoDTO parqueadero;

	public ZonaParqueaderoDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setNombreZona("");
		setParqueadero(new ParqueaderoDTO());
	}

	private ZonaParqueaderoDTO(final Builder builder) {
		setId(builder.id);
		setNombreZona(builder.nombreZona);
		setParqueadero(builder.parqueadero);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public ParqueaderoDTO getParqueadero() {
		return parqueadero;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreZona(final String nombreZona) {
		this.nombreZona = UtilTexto.aplicarTrim(nombreZona);
	}

	private void setParqueadero(final ParqueaderoDTO parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	public static class Builder {

		private UUID id;
		private String nombreZona;
		private ParqueaderoDTO parqueadero;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreZona(final String nombreZona) {
			this.nombreZona = UtilTexto.aplicarTrim(nombreZona);
			return this;
		}

		public Builder parqueadero(final ParqueaderoDTO parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public ZonaParqueaderoDTO build() {
			return new ZonaParqueaderoDTO(this);
		}
	}

}