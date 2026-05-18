package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;

public class EstadoEspacioFisicoDTO {

	private UUID id;
	private String nombreEstadoEspacioFisico;

	public EstadoEspacioFisicoDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setNombreEstadoEspacioFisico("");
	}

	private EstadoEspacioFisicoDTO(final Builder builder) {
		setId(builder.id);
		setNombreEstadoEspacioFisico(builder.nombreEstadoEspacioFisico);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreEstadoEspacioFisico() {
		return nombreEstadoEspacioFisico;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreEstadoEspacioFisico(final String nombreEstadoEspacioFisico) {
		this.nombreEstadoEspacioFisico = UtilTexto.aplicarTrim(nombreEstadoEspacioFisico);
	}

	public static class Builder {

		private UUID id;
		private String nombreEstadoEspacioFisico;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreEstadoEspacioFisico(final String nombreEstadoEspacioFisico) {
			this.nombreEstadoEspacioFisico = UtilTexto.aplicarTrim(nombreEstadoEspacioFisico);
			return this;
		}

		public EstadoEspacioFisicoDTO build() {
			return new EstadoEspacioFisicoDTO(this);
		}
	}

}