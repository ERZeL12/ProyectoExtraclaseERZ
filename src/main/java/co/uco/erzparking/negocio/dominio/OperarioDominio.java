package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class OperarioDominio {

	private UUID id;
	private TipoDocumentoIdentificacionDominio tipoDocumentoIdentificacion;
	private String numeroIdentificacion;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private long numeroTelefonico;
	private CargoDominio cargo;
	private ParqueaderoDominio parqueadero;

	private OperarioDominio(final Builder builder) {
		setId(builder.id);
		setTipoDocumentoIdentificacion(builder.tipoDocumentoIdentificacion);
		setNumeroIdentificacion(builder.numeroIdentificacion);
		setPrimerNombre(builder.primerNombre);
		setSegundoNombre(builder.segundoNombre);
		setPrimerApellido(builder.primerApellido);
		setSegundoApellido(builder.segundoApellido);
		setNumeroTelefonico(builder.numeroTelefonico);
		setCargo(builder.cargo);
		setParqueadero(builder.parqueadero);
	}

	public UUID getId() {
		return id;
	}

	public TipoDocumentoIdentificacionDominio getTipoDocumentoIdentificacion() {
		return tipoDocumentoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public long getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public CargoDominio getCargo() {
		return cargo;
	}

	public ParqueaderoDominio getParqueadero() {
		return parqueadero;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setTipoDocumentoIdentificacion(final TipoDocumentoIdentificacionDominio tipoDocumentoIdentificacion) {
		this.tipoDocumentoIdentificacion = UtilObjeto.obtenerValorDefecto(tipoDocumentoIdentificacion, null);
	}

	private void setNumeroIdentificacion(final String numeroIdentificacion) {
		this.numeroIdentificacion = UtilTexto.aplicarTrim(numeroIdentificacion);
	}

	private void setPrimerNombre(final String primerNombre) {
		this.primerNombre = UtilTexto.aplicarTrim(primerNombre);
	}

	private void setSegundoNombre(final String segundoNombre) {
		this.segundoNombre = UtilTexto.aplicarTrim(segundoNombre);
	}

	private void setPrimerApellido(final String primerApellido) {
		this.primerApellido = UtilTexto.aplicarTrim(primerApellido);
	}

	private void setSegundoApellido(final String segundoApellido) {
		this.segundoApellido = UtilTexto.aplicarTrim(segundoApellido);
	}

	private void setNumeroTelefonico(final long numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	private void setCargo(final CargoDominio cargo) {
		this.cargo = UtilObjeto.obtenerValorDefecto(cargo, null);
	}

	private void setParqueadero(final ParqueaderoDominio parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	public static class Builder {

		private UUID id;
		private TipoDocumentoIdentificacionDominio tipoDocumentoIdentificacion;
		private String numeroIdentificacion;
		private String primerNombre;
		private String segundoNombre;
		private String primerApellido;
		private String segundoApellido;
		private long numeroTelefonico;
		private CargoDominio cargo;
		private ParqueaderoDominio parqueadero;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder tipoDocumentoIdentificacion(final TipoDocumentoIdentificacionDominio tipoDocumentoIdentificacion) {
			this.tipoDocumentoIdentificacion = tipoDocumentoIdentificacion;
			return this;
		}

		public Builder numeroIdentificacion(final String numeroIdentificacion) {
			this.numeroIdentificacion = UtilTexto.aplicarTrim(numeroIdentificacion);
			return this;
		}

		public Builder primerNombre(final String primerNombre) {
			this.primerNombre = UtilTexto.aplicarTrim(primerNombre);
			return this;
		}

		public Builder segundoNombre(final String segundoNombre) {
			this.segundoNombre = UtilTexto.aplicarTrim(segundoNombre);
			return this;
		}

		public Builder primerApellido(final String primerApellido) {
			this.primerApellido = UtilTexto.aplicarTrim(primerApellido);
			return this;
		}

		public Builder segundoApellido(final String segundoApellido) {
			this.segundoApellido = UtilTexto.aplicarTrim(segundoApellido);
			return this;
		}

		public Builder numeroTelefonico(final long numeroTelefonico) {
			this.numeroTelefonico = numeroTelefonico;
			return this;
		}

		public Builder cargo(final CargoDominio cargo) {
			this.cargo = cargo;
			return this;
		}

		public Builder parqueadero(final ParqueaderoDominio parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public OperarioDominio build() {
			return new OperarioDominio(this);
		}
	}

}
