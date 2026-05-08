package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class MetodoPagoDominio {

	private UUID id;
	private String nombreMetodoPago;
	private String descripcion;

	private MetodoPagoDominio(final Builder builder) {
		setId(builder.id);
		setNombreMetodoPago(builder.nombreMetodoPago);
		setDescripcion(builder.descripcion);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreMetodoPago() {
		return nombreMetodoPago;
	}

	public String getDescripcion() {
		return descripcion;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreMetodoPago(final String nombreMetodoPago) {
		this.nombreMetodoPago = UtilTexto.aplicarTrim(nombreMetodoPago);
	}

	private void setDescripcion(final String descripcion) {
		this.descripcion = UtilTexto.aplicarTrim(descripcion);
	}

	public static class Builder {

		private UUID id;
		private String nombreMetodoPago;
		private String descripcion;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreMetodoPago(final String nombreMetodoPago) {
			this.nombreMetodoPago = UtilTexto.aplicarTrim(nombreMetodoPago);
			return this;
		}

		public Builder descripcion(final String descripcion) {
			this.descripcion = UtilTexto.aplicarTrim(descripcion);
			return this;
		}

		public MetodoPagoDominio build() {
			return new MetodoPagoDominio(this);
		}
	}

}