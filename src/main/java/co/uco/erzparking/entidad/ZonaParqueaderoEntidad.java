package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class ZonaParqueaderoEntidad {

	private UUID id;
	private String nombreZona;
	private ParqueaderoEntidad parqueadero;

	private ZonaParqueaderoEntidad(final Builder builder) {
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

	public ParqueaderoEntidad getParqueadero() {
		return parqueadero;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreZona(final String nombreZona) {
		this.nombreZona = UtilTexto.aplicarTrim(nombreZona);
	}

	private void setParqueadero(final ParqueaderoEntidad parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	public static class Builder {

		private UUID id;
		private String nombreZona;
		private ParqueaderoEntidad parqueadero;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreZona(final String nombreZona) {
			this.nombreZona = UtilTexto.aplicarTrim(nombreZona);
			return this;
		}

		public Builder parqueadero(final ParqueaderoEntidad parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public ZonaParqueaderoEntidad build() {
			return new ZonaParqueaderoEntidad(this);
		}
	}

}