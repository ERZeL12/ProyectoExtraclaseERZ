package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class TipoDocumentoIdentificacionDominio {

	private UUID id;
	private String nombreDocumentoIdentificacion;
	private String descripcion;

	private TipoDocumentoIdentificacionDominio(final Builder builder) {
		setId(builder.id);
		setNombreDocumentoIdentificacion(builder.nombreDocumentoIdentificacion);
		setDescripcion(builder.descripcion);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreDocumentoIdentificacion() {
		return nombreDocumentoIdentificacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreDocumentoIdentificacion(final String nombreDocumentoIdentificacion) {
		this.nombreDocumentoIdentificacion = UtilTexto.aplicarTrim(nombreDocumentoIdentificacion);
	}

	private void setDescripcion(final String descripcion) {
		this.descripcion = UtilTexto.aplicarTrim(descripcion);
	}

	public static class Builder {

		private UUID id;
		private String nombreDocumentoIdentificacion;
		private String descripcion;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreDocumentoIdentificacion(final String nombreDocumentoIdentificacion) {
			this.nombreDocumentoIdentificacion = UtilTexto.aplicarTrim(nombreDocumentoIdentificacion);
			return this;
		}

		public Builder descripcion(final String descripcion) {
			this.descripcion = UtilTexto.aplicarTrim(descripcion);
			return this;
		}

		public TipoDocumentoIdentificacionDominio build() {
			return new TipoDocumentoIdentificacionDominio(this);
		}
	}

}