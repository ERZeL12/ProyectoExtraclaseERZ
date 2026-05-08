package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class EspacioFisicoEntidad {

	private UUID id;
	private String numeroEspacioFisico;
	private TipoServicioEntidad tipoServicio;
	private EstadoEspacioFisicoEntidad estadoEspacioFisico;
	private ZonaParqueaderoEntidad zonaEspacioFisico;
	private ParqueaderoEntidad parqueadero;

	private EspacioFisicoEntidad(final Builder builder) {
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

	public TipoServicioEntidad getTipoServicio() {
		return tipoServicio;
	}

	public EstadoEspacioFisicoEntidad getEstadoEspacioFisico() {
		return estadoEspacioFisico;
	}

	public ZonaParqueaderoEntidad getZonaEspacioFisico() {
		return zonaEspacioFisico;
	}

	public ParqueaderoEntidad getParqueadero() {
		return parqueadero;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNumeroEspacioFisico(final String numeroEspacioFisico) {
		this.numeroEspacioFisico = UtilTexto.aplicarTrim(numeroEspacioFisico);
	}

	private void setTipoServicio(final TipoServicioEntidad tipoServicio) {
		this.tipoServicio = UtilObjeto.obtenerValorDefecto(tipoServicio, null);
	}

	private void setEstadoEspacioFisico(final EstadoEspacioFisicoEntidad estadoEspacioFisico) {
		this.estadoEspacioFisico = UtilObjeto.obtenerValorDefecto(estadoEspacioFisico, null);
	}

	private void setZonaEspacioFisico(final ZonaParqueaderoEntidad zonaEspacioFisico) {
		this.zonaEspacioFisico = UtilObjeto.obtenerValorDefecto(zonaEspacioFisico, null);
	}

	private void setParqueadero(final ParqueaderoEntidad parqueadero) {
		this.parqueadero = UtilObjeto.obtenerValorDefecto(parqueadero, null);
	}

	public static class Builder {

		private UUID id;
		private String numeroEspacioFisico;
		private TipoServicioEntidad tipoServicio;
		private EstadoEspacioFisicoEntidad estadoEspacioFisico;
		private ZonaParqueaderoEntidad zonaEspacioFisico;
		private ParqueaderoEntidad parqueadero;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder numeroEspacioFisico(final String numeroEspacioFisico) {
			this.numeroEspacioFisico = UtilTexto.aplicarTrim(numeroEspacioFisico);
			return this;
		}

		public Builder tipoServicio(final TipoServicioEntidad tipoServicio) {
			this.tipoServicio = tipoServicio;
			return this;
		}

		public Builder estadoEspacioFisico(final EstadoEspacioFisicoEntidad estadoEspacioFisico) {
			this.estadoEspacioFisico = estadoEspacioFisico;
			return this;
		}

		public Builder zonaEspacioFisico(final ZonaParqueaderoEntidad zonaEspacioFisico) {
			this.zonaEspacioFisico = zonaEspacioFisico;
			return this;
		}

		public Builder parqueadero(final ParqueaderoEntidad parqueadero) {
			this.parqueadero = parqueadero;
			return this;
		}

		public EspacioFisicoEntidad build() {
			return new EspacioFisicoEntidad(this);
		}
	}

}