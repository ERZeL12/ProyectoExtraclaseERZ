package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilTexto;


public class EstadoEspacioFisicoDominio {

	private String nombreEstadoEspacioFisico;

	private EstadoEspacioFisicoDominio(final Builder builder) {
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

		public EstadoEspacioFisicoDominio build() {
			return new EstadoEspacioFisicoDominio(this);
		}
	}

}