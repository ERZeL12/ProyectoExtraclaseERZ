package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class PaisDominio {

	private UUID id;
	private String nombre;

	private PaisDominio(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
	}

	public UUID getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}

	public static class Builder {

		private UUID id;
		private String nombre;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public PaisDominio build() {
			return new PaisDominio(this);
		}
	}

}