package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class ServicioDTO {

	private UUID id;
	private String nombreServicio;
	private TipoServicioDTO tipoServicio;
	private ParqueaderoDTO parqueadero;
	private boolean estadoActual;

	public ServicioDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setNombreServicio("");
		setTipoServicio(new TipoServicioDTO());
		setParqueadero(new ParqueaderoDTO());
		setEstadoActual(true);
	}

	private ServicioDTO(final Builder builder) {
		setId(builder.id);
		setNombreServicio(builder.nombreServicio);
		setTipoServicio(builder.tipoServicio);
		setParqueadero(builder.parqueadero);
		setEstadoActual(builder.estadoActual);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public TipoServicioDTO getTipoServicio() {
		return tipoServicio;
	}

	public ParqueaderoDTO getParqueadero() {
		return parqueadero;
	}

	public boolean isEstadoActual() {
		return estadoActual;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreServicio(final String nombreServicio) {
		this.nombreServicio = UtilTexto.aplicarTrim(nombreServicio);
	}

	private void setTipoServicio(final TipoServicioDTO tipoServicio) {
		this.tipoServicio = UtilObjeto.obtenerValorDefecto(tipoServicio, null);
	}

	private void setParqueadero(final ParqueaderoDTO parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	private void setEstadoActual(final boolean estadoActual) {
		this.estadoActual = estadoActual;
	}

	public static class Builder {

		private UUID id;
		private String nombreServicio;
		private TipoServicioDTO tipoServicio;
		private ParqueaderoDTO parqueadero;
		private boolean estadoActual = true;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreServicio(final String nombreServicio) {
			this.nombreServicio = UtilTexto.aplicarTrim(nombreServicio);
			return this;
		}

		public Builder tipoServicio(final TipoServicioDTO tipoServicio) {
			this.tipoServicio = tipoServicio;
			return this;
		}

		public Builder parqueadero(final ParqueaderoDTO parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public Builder estadoActual(final boolean estadoActual) {
			this.estadoActual = estadoActual;
			return this;
		}

		public ServicioDTO build() {
			return new ServicioDTO(this);
		}
	}

}