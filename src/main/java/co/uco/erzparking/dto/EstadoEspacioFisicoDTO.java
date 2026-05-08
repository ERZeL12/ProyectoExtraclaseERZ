package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilTexto;


public class EstadoEspacioFisicoDTO {

	private String nombreEstadoEspacioFisico;

	private EstadoEspacioFisicoDTO(final Builder builder) {
		setNombreEstadoEspacioFisico(builder.nombreEstadoEspacioFisico);
	}

	public String getNombreEstadoEspacioFisico() {
		return nombreEstadoEspacioFisico;
	}

	private void setNombreEstadoEspacioFisico(final String nombreEstadoEspacioFisico) {
		this.nombreEstadoEspacioFisico = UtilTexto.aplicarTrim(nombreEstadoEspacioFisico);
	}

	public static class Builder {

		private String nombreEstadoEspacioFisico;

		public Builder nombreEstadoEspacioFisico(final String nombreEstadoEspacioFisico) {
			this.nombreEstadoEspacioFisico = UtilTexto.aplicarTrim(nombreEstadoEspacioFisico);
			return this;
		}

		public EstadoEspacioFisicoDTO build() {
			return new EstadoEspacioFisicoDTO(this);
		}
	}

}