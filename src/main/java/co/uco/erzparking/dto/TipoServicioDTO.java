package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class TipoServicioDTO {

	private UUID id;
	private String nombreServicio;

	private TipoServicioDTO(final Builder builder) {
		setId(builder.id);
		setNombreServicio(builder.nombreServicio);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreServicio(final String nombreServicio) {
		this.nombreServicio = UtilTexto.aplicarTrim(nombreServicio);
	}

	public static class Builder {

		private UUID id;
		private String nombreServicio;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreServicio(final String nombreServicio) {
			this.nombreServicio = UtilTexto.aplicarTrim(nombreServicio);
			return this;
		}

		public TipoServicioDTO build() {
			return new TipoServicioDTO(this);
		}
	}

}
