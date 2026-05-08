package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class ZonaParqueaderoDominio {

	private UUID id;
	private String nombreZona;
	private ParqueaderoDominio parqueadero;

	private ZonaParqueaderoDominio(final Builder builder) {
		setId(builder.id);
		setNombreZona(builder.nombreZona);
		setParqueadero(builder.parqueadero);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public ParqueaderoDominio getParqueadero() {
		return parqueadero;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreZona(final String nombreZona) {
		this.nombreZona = UtilTexto.aplicarTrim(nombreZona);
	}

	private void setParqueadero(final ParqueaderoDominio parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	public static class Builder {

		private UUID id;
		private String nombreZona;
		private ParqueaderoDominio parqueadero;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreZona(final String nombreZona) {
			this.nombreZona = UtilTexto.aplicarTrim(nombreZona);
			return this;
		}

		public Builder parqueadero(final ParqueaderoDominio parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public ZonaParqueaderoDominio build() {
			return new ZonaParqueaderoDominio(this);
		}
	}

}