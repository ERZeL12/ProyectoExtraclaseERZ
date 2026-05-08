package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class EspacioFisicoDTO {

	private UUID id;
	private String numeroEspacioFisico;
	private TipoServicioDTO tipoServicio;
	private EstadoEspacioFisicoDTO estadoEspacioFisico;
	private ZonaParqueaderoDTO zonaEspacioFisico;
	private ParqueaderoDTO parqueadero;

	private EspacioFisicoDTO(final Builder builder) {
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

	public TipoServicioDTO getTipoServicio() {
		return tipoServicio;
	}

	public EstadoEspacioFisicoDTO getEstadoEspacioFisico() {
		return estadoEspacioFisico;
	}

	public ZonaParqueaderoDTO getZonaEspacioFisico() {
		return zonaEspacioFisico;
	}

	public ParqueaderoDTO getParqueadero() {
		return parqueadero;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNumeroEspacioFisico(final String numeroEspacioFisico) {
		this.numeroEspacioFisico = UtilTexto.aplicarTrim(numeroEspacioFisico);
	}

	private void setTipoServicio(final TipoServicioDTO tipoServicio) {
		this.tipoServicio = UtilObjeto.obtenerValorDefecto(tipoServicio, null);
	}

	private void setEstadoEspacioFisico(final EstadoEspacioFisicoDTO estadoEspacioFisico) {
		this.estadoEspacioFisico = UtilObjeto.obtenerValorDefecto(estadoEspacioFisico, null);
	}

	private void setZonaEspacioFisico(final ZonaParqueaderoDTO zonaEspacioFisico) {
		this.zonaEspacioFisico = UtilObjeto.obtenerValorDefecto(zonaEspacioFisico, null);
	}

	private void setParqueadero(final ParqueaderoDTO parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	public static class Builder {

		private UUID id;
		private String numeroEspacioFisico;
		private TipoServicioDTO tipoServicio;
		private EstadoEspacioFisicoDTO estadoEspacioFisico;
		private ZonaParqueaderoDTO zonaEspacioFisico;
		private ParqueaderoDTO parqueadero;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder numeroEspacioFisico(final String numeroEspacioFisico) {
			this.numeroEspacioFisico = UtilTexto.aplicarTrim(numeroEspacioFisico);
			return this;
		}

		public Builder tipoServicio(final TipoServicioDTO tipoServicio) {
			this.tipoServicio = tipoServicio;
			return this;
		}

		public Builder estadoEspacioFisico(final EstadoEspacioFisicoDTO estadoEspacioFisico) {
			this.estadoEspacioFisico = estadoEspacioFisico;
			return this;
		}

		public Builder zonaEspacioFisico(final ZonaParqueaderoDTO zonaEspacioFisico) {
			this.zonaEspacioFisico = zonaEspacioFisico;
			return this;
		}

		public Builder parqueadero(final ParqueaderoDTO parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public EspacioFisicoDTO build() {
			return new EspacioFisicoDTO(this);
		}
	}

}