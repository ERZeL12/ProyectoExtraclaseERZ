package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;

public class EstadoEspacioFisicoDominio {

	private UUID id;
	private String nombreEstadoEspacioFisico;

	private EstadoEspacioFisicoDominio(final Builder builder) {
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

		public EstadoEspacioFisicoDominio build() {
			return new EstadoEspacioFisicoDominio(this);
		}
	}

}