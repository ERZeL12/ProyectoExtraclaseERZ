package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilFecha;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.Date;
import java.util.UUID;


public class ContratoMensualidadDTO {

	private UUID id;
	private Date fechaInicioContrato;
	private Date fechaFinContrato;
	private TarifaDTO tarifa;
	private UsuarioVehiculoDTO usuarioVehiculo;
	private EspacioFisicoDTO espacioFisico;
	private boolean estadoActual;

	public ContratoMensualidadDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setFechaInicioContrato(java.sql.Date.valueOf("0001-01-01"));
		setFechaFinContrato(java.sql.Date.valueOf("0001-01-01"));
		setTarifa(new TarifaDTO());
		setUsuarioVehiculo(new UsuarioVehiculoDTO());
		setEspacioFisico(new EspacioFisicoDTO());
		setEstadoActual(true);
	}

	private ContratoMensualidadDTO(final Builder builder) {
		setId(builder.id);
		setFechaInicioContrato(builder.fechaInicioContrato);
		setFechaFinContrato(builder.fechaFinContrato);
		setTarifa(builder.tarifa);
		setUsuarioVehiculo(builder.usuarioVehiculo);
		setEspacioFisico(builder.espacioFisico);
		setEstadoActual(builder.estadoActual);
	}

	public UUID getId() {
		return id;
	}

	public Date getFechaInicioContrato() {
		return fechaInicioContrato;
	}

	public Date getFechaFinContrato() {
		return fechaFinContrato;
	}

	public TarifaDTO getTarifa() {
		return tarifa;
	}

	public UsuarioVehiculoDTO getUsuarioVehiculo() {
		return usuarioVehiculo;
	}

	public EspacioFisicoDTO getEspacioFisico() {
		return espacioFisico;
	}

	public boolean isEstadoActual() {
		return estadoActual;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setFechaInicioContrato(final Date fechaInicioContrato) {
		this.fechaInicioContrato = UtilFecha.obtenerValorDefecto(fechaInicioContrato);
	}

	private void setFechaFinContrato(final Date fechaFinContrato) {
		this.fechaFinContrato = UtilFecha.obtenerValorDefecto(fechaFinContrato);
	}

	private void setTarifa(final TarifaDTO tarifa) {
		this.tarifa = UtilObjeto.obtenerValorDefecto(tarifa, null);
	}

	private void setUsuarioVehiculo(final UsuarioVehiculoDTO usuarioVehiculo) {
		this.usuarioVehiculo = UtilObjeto.obtenerValorDefecto(usuarioVehiculo, null);
	}

	private void setEspacioFisico(final EspacioFisicoDTO espacioFisico) {
		this.espacioFisico = UtilObjeto.obtenerValorDefecto(espacioFisico, null);
	}

	private void setEstadoActual(final boolean estadoActual) {
		this.estadoActual = estadoActual;
	}

	public static class Builder {

		private UUID id;
		private Date fechaInicioContrato;
		private Date fechaFinContrato;
		private TarifaDTO tarifa;
		private UsuarioVehiculoDTO usuarioVehiculo;
		private EspacioFisicoDTO espacioFisico;
		private boolean estadoActual = true;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder fechaInicioContrato(final Date fechaInicioContrato) {
			this.fechaInicioContrato = UtilFecha.obtenerValorDefecto(fechaInicioContrato);
			return this;
		}

		public Builder fechaFinContrato(final Date fechaFinContrato) {
			this.fechaFinContrato = UtilFecha.obtenerValorDefecto(fechaFinContrato);
			return this;
		}

		public Builder tarifa(final TarifaDTO tarifa) {
			this.tarifa = tarifa;
			return this;
		}

		public Builder usuarioVehiculo(final UsuarioVehiculoDTO usuarioVehiculo) {
			this.usuarioVehiculo = usuarioVehiculo;
			return this;
		}

		public Builder espacioFisico(final EspacioFisicoDTO espacioFisico) {
			this.espacioFisico = espacioFisico;
			return this;
		}

		public Builder estadoActual(final boolean estadoActual) {
			this.estadoActual = estadoActual;
			return this;
		}

		public ContratoMensualidadDTO build() {
			return new ContratoMensualidadDTO(this);
		}
	}

}