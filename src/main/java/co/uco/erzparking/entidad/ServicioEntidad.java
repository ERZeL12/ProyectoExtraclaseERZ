package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class ServicioEntidad {

	private UUID id;
	private String nombreServicio;
	private TipoServicioEntidad tipoServicio;
	private ParqueaderoEntidad parqueadero;
	private boolean estadoActual;

	private ServicioEntidad(final Builder builder) {
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

	public TipoServicioEntidad getTipoServicio() {
		return tipoServicio;
	}

	public ParqueaderoEntidad getParqueadero() {
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

	private void setTipoServicio(final TipoServicioEntidad tipoServicio) {
		this.tipoServicio = UtilObjeto.obtenerValorDefecto(tipoServicio, null);
	}

	private void setParqueadero(final ParqueaderoEntidad parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	private void setEstadoActual(final boolean estadoActual) {
		this.estadoActual = estadoActual;
	}

	public static class Builder {

		private UUID id;
		private String nombreServicio;
		private TipoServicioEntidad tipoServicio;
		private ParqueaderoEntidad parqueadero;
		private boolean estadoActual = true;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreServicio(final String nombreServicio) {
			this.nombreServicio = UtilTexto.aplicarTrim(nombreServicio);
			return this;
		}

		public Builder tipoServicio(final TipoServicioEntidad tipoServicio) {
			this.tipoServicio = tipoServicio;
			return this;
		}

		public Builder parqueadero(final ParqueaderoEntidad parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public Builder estadoActual(final boolean estadoActual) {
			this.estadoActual = estadoActual;
			return this;
		}

		public ServicioEntidad build() {
			return new ServicioEntidad(this);
		}
	}

}