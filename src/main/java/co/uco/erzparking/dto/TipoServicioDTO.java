package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class TipoServicioDTO {

	private UUID id;
	private String nombreServicio;
	private String descripcion;

	public TipoServicioDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setNombreServicio("");
		setDescripcion("");
	}

	private TipoServicioDTO(final Builder builder) {
		setId(builder.id);
		setNombreServicio(builder.nombreServicio);
		setDescripcion(builder.descripcion);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public String getDescripcion() {
	    return descripcion;
	}
	
	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreServicio(final String nombreServicio) {
		this.nombreServicio = UtilTexto.aplicarTrim(nombreServicio);
	}
	
	private void setDescripcion(final String descripcion) {
	    this.descripcion = UtilTexto.aplicarTrim(descripcion);
	}

	public static class Builder {

		private UUID id;
		private String nombreServicio;
		private String descripcion;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreServicio(final String nombreServicio) {
			this.nombreServicio = UtilTexto.aplicarTrim(nombreServicio);
			return this;
		}
		
		public Builder descripcion(final String descripcion) {
		    this.descripcion = UtilTexto.aplicarTrim(descripcion);
		    return this;
		}

		public TipoServicioDTO build() {
			return new TipoServicioDTO(this);
		}
	}

}
