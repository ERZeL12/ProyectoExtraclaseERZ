package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class ServicioDominio {

	private UUID id;
	private String nombreServicio;
	private TipoServicioDominio tipoServicio;
	private ParqueaderoDominio parqueadero;

	private ServicioDominio(final Builder builder) {
		setId(builder.id);
		setNombreServicio(builder.nombreServicio);
		setTipoServicio(builder.tipoServicio);
		setParqueadero(builder.parqueadero);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public TipoServicioDominio getTipoServicio() {
		return tipoServicio;
	}

	public ParqueaderoDominio getParqueadero() {
		return parqueadero;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreServicio(final String nombreServicio) {
		this.nombreServicio = UtilTexto.aplicarTrim(nombreServicio);
	}

	private void setTipoServicio(final TipoServicioDominio tipoServicio) {
		this.tipoServicio = UtilObjeto.obtenerValorDefecto(tipoServicio, null);
	}

	private void setParqueadero(final ParqueaderoDominio parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	public static class Builder {

		private UUID id;
		private String nombreServicio;
		private TipoServicioDominio tipoServicio;
		private ParqueaderoDominio parqueadero;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreServicio(final String nombreServicio) {
			this.nombreServicio = UtilTexto.aplicarTrim(nombreServicio);
			return this;
		}

		public Builder tipoServicio(final TipoServicioDominio tipoServicio) {
			this.tipoServicio = tipoServicio;
			return this;
		}

		public Builder parqueadero(final ParqueaderoDominio parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public ServicioDominio build() {
			return new ServicioDominio(this);
		}
	}

}