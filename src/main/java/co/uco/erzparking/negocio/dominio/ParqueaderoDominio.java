package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class ParqueaderoDominio {

	private UUID id;
	private String nombreEstablecimiento;
	private long numeroTelefonico;
	private String correoElectronico;
	private String direccionEstablecimiento;
	private CiudadDominio ciudad;

	private ParqueaderoDominio(final Builder builder) {
		setId(builder.id);
		setNombreEstablecimiento(builder.nombreEstablecimiento);
		setNumeroTelefonico(builder.numeroTelefonico);
		setCorreoElectronico(builder.correoElectronico);
		setDireccionEstablecimiento(builder.direccionEstablecimiento);
		setCiudad(builder.ciudad);
	}

	public UUID getId() {
		return id;
	}

	public String getNombreEstablecimiento() {
		return nombreEstablecimiento;
	}

	public long getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public String getDireccionEstablecimiento() {
		return direccionEstablecimiento;
	}

	public CiudadDominio getCiudad() {
		return ciudad;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombreEstablecimiento(final String nombreEstablecimiento) {
		this.nombreEstablecimiento = UtilTexto.aplicarTrim(nombreEstablecimiento);
	}

	private void setNumeroTelefonico(final long numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	private void setCorreoElectronico(final String correoElectronico) {
		this.correoElectronico = UtilTexto.aplicarTrim(correoElectronico);
	}

	private void setDireccionEstablecimiento(final String direccionEstablecimiento) {
		this.direccionEstablecimiento = UtilTexto.aplicarTrim(direccionEstablecimiento);
	}

	private void setCiudad(final CiudadDominio ciudad) {
		this.ciudad = UtilObjeto.obtenerValorDefecto(ciudad, null);
	}

	public static class Builder {

		private UUID id;
		private String nombreEstablecimiento;
		private long numeroTelefonico;
		private String correoElectronico;
		private String direccionEstablecimiento;
		private CiudadDominio ciudad;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombreEstablecimiento(final String nombreEstablecimiento) {
			this.nombreEstablecimiento = UtilTexto.aplicarTrim(nombreEstablecimiento);
			return this;
		}

		public Builder numeroTelefonico(final long numeroTelefonico) {
			this.numeroTelefonico = numeroTelefonico;
			return this;
		}

		public Builder correoElectronico(final String correoElectronico) {
			this.correoElectronico = UtilTexto.aplicarTrim(correoElectronico);
			return this;
		}

		public Builder direccionEstablecimiento(final String direccionEstablecimiento) {
			this.direccionEstablecimiento = UtilTexto.aplicarTrim(direccionEstablecimiento);
			return this;
		}

		public Builder ciudad(final CiudadDominio ciudad) {
			this.ciudad = ciudad;
			return this;
		}

		public ParqueaderoDominio build() {
			return new ParqueaderoDominio(this);
		}
	}

}