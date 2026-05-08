package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilTexto;


public class EstadoEspacioFisicoEntidad {

	private String nombreEstadoEspacioFisico;

	private EstadoEspacioFisicoEntidad(final Builder builder) {
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

		public EstadoEspacioFisicoEntidad build() {
			return new EstadoEspacioFisicoEntidad(this);
		}
	}

}