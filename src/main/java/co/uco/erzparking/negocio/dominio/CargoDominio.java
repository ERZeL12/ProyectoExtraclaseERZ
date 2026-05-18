package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class CargoDominio {

	private UUID id;
	private String nombreCargo;
	private String descripcion;
	private ParqueaderoDominio parqueadero;
	private boolean estadoActual;

	private CargoDominio(final Builder builder) {
		setId(builder.id);
		setNombreCargo(builder.nombreCargo);
		setDescripcion(builder.descripcion);
		setParqueadero(builder.parqueadero);
		setEstadoActual(builder.estadoActual);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreCargo() {
		return nombreCargo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public ParqueaderoDominio getParqueadero() {
		return parqueadero;
	}

	public boolean isEstadoActual() {
		return estadoActual;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreCargo(final String nombreCargo) {
		this.nombreCargo = UtilTexto.aplicarTrim(nombreCargo);
	}

	private void setDescripcion(final String descripcion) {
		this.descripcion = UtilTexto.aplicarTrim(descripcion);
	}

	private void setParqueadero(final ParqueaderoDominio parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	private void setEstadoActual(final boolean estadoActual) {
		this.estadoActual = estadoActual;
	}

	public static class Builder {

		private UUID id;
		private String nombreCargo;
		private String descripcion;
		private ParqueaderoDominio parqueadero;
		private boolean estadoActual = true;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreCargo(final String nombreCargo) {
			this.nombreCargo = UtilTexto.aplicarTrim(nombreCargo);
			return this;
		}

		public Builder descripcion(final String descripcion) {
			this.descripcion = UtilTexto.aplicarTrim(descripcion);
			return this;
		}

		public Builder parqueadero(final ParqueaderoDominio parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public Builder estadoActual(final boolean estadoActual) {
			this.estadoActual = estadoActual;
			return this;
		}

		public CargoDominio build() {
			return new CargoDominio(this);
		}
	}

}