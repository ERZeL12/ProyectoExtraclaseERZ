package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class EspacioFisicoDominio {

	private UUID id;
	private String numeroEspacioFisico;
	private TipoServicioDominio tipoServicio;
	private EstadoEspacioFisicoDominio estadoEspacioFisico;
	private ZonaParqueaderoDominio zonaEspacioFisico;
	private ParqueaderoDominio parqueadero;

	private EspacioFisicoDominio(final Builder builder) {
		setId(builder.id);
		setNumeroEspacioFisico(builder.numeroEspacioFisico);
		setTipoServicio(builder.tipoServicio);
		setEstadoEspacioFisico(builder.estadoEspacioFisico);
		setZonaEspacioFisico(builder.zonaEspacioFisico);
		setParqueadero(builder.parqueadero);
	}

	public UUID getId() {
		return id;
	}

	public String getNumeroEspacioFisico() {
		return numeroEspacioFisico;
	}

	public TipoServicioDominio getTipoServicio() {
		return tipoServicio;
	}

	public EstadoEspacioFisicoDominio getEstadoEspacioFisico() {
		return estadoEspacioFisico;
	}

	public ZonaParqueaderoDominio getZonaEspacioFisico() {
		return zonaEspacioFisico;
	}

	public ParqueaderoDominio getParqueadero() {
		return parqueadero;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNumeroEspacioFisico(final String numeroEspacioFisico) {
		this.numeroEspacioFisico = UtilTexto.aplicarTrim(numeroEspacioFisico);
	}

	private void setTipoServicio(final TipoServicioDominio tipoServicio) {
		this.tipoServicio = UtilObjeto.obtenerValorDefecto(tipoServicio, null);
	}

	private void setEstadoEspacioFisico(final EstadoEspacioFisicoDominio estadoEspacioFisico) {
		this.estadoEspacioFisico = UtilObjeto.obtenerValorDefecto(estadoEspacioFisico, null);
	}

	private void setZonaEspacioFisico(final ZonaParqueaderoDominio zonaEspacioFisico) {
		this.zonaEspacioFisico = UtilObjeto.obtenerValorDefecto(zonaEspacioFisico, null);
	}

	private void setParqueadero(final ParqueaderoDominio parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	public static class Builder {

		private UUID id;
		private String numeroEspacioFisico;
		private TipoServicioDominio tipoServicio;
		private EstadoEspacioFisicoDominio estadoEspacioFisico;
		private ZonaParqueaderoDominio zonaEspacioFisico;
		private ParqueaderoDominio parqueadero;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder numeroEspacioFisico(final String numeroEspacioFisico) {
			this.numeroEspacioFisico = UtilTexto.aplicarTrim(numeroEspacioFisico);
			return this;
		}

		public Builder tipoServicio(final TipoServicioDominio tipoServicio) {
			this.tipoServicio = tipoServicio;
			return this;
		}

		public Builder estadoEspacioFisico(final EstadoEspacioFisicoDominio estadoEspacioFisico) {
			this.estadoEspacioFisico = estadoEspacioFisico;
			return this;
		}

		public Builder zonaEspacioFisico(final ZonaParqueaderoDominio zonaEspacioFisico) {
			this.zonaEspacioFisico = zonaEspacioFisico;
			return this;
		}

		public Builder parqueadero(final ParqueaderoDominio parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public EspacioFisicoDominio build() {
			return new EspacioFisicoDominio(this);
		}
	}

}