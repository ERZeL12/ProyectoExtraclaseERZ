package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilFecha;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.Date;
import java.util.UUID;


public class ContratoMensualidadDominio {

	private UUID id;
	private Date fechaInicioContrato;
	private Date fechaFinContrato;
	private TarifaDominio tarifa;
	private UsuarioVehiculoDominio usuarioVehiculo;
	private EspacioFisicoDominio espacioFisico;

	private ContratoMensualidadDominio(final Builder builder) {
		setId(builder.id);
		setFechaInicioContrato(builder.fechaInicioContrato);
		setFechaFinContrato(builder.fechaFinContrato);
		setTarifa(builder.tarifa);
		setUsuarioVehiculo(builder.usuarioVehiculo);
		setEspacioFisico(builder.espacioFisico);
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

	public TarifaDominio getTarifa() {
		return tarifa;
	}

	public UsuarioVehiculoDominio getUsuarioVehiculo() {
		return usuarioVehiculo;
	}

	public EspacioFisicoDominio getEspacioFisico() {
		return espacioFisico;
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

	private void setTarifa(final TarifaDominio tarifa) {
		this.tarifa = UtilObjeto.obtenerValorDefecto(tarifa, null);
	}

	private void setUsuarioVehiculo(final UsuarioVehiculoDominio usuarioVehiculo) {
		this.usuarioVehiculo = UtilObjeto.obtenerValorDefecto(usuarioVehiculo, null);
	}

	private void setEspacioFisico(final EspacioFisicoDominio espacioFisico) {
		this.espacioFisico = UtilObjeto.obtenerValorDefecto(espacioFisico, null);
	}

	public static class Builder {

		private UUID id;
		private Date fechaInicioContrato;
		private Date fechaFinContrato;
		private TarifaDominio tarifa;
		private UsuarioVehiculoDominio usuarioVehiculo;
		private EspacioFisicoDominio espacioFisico;

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

		public Builder tarifa(final TarifaDominio tarifa) {
			this.tarifa = tarifa;
			return this;
		}

		public Builder usuarioVehiculo(final UsuarioVehiculoDominio usuarioVehiculo) {
			this.usuarioVehiculo = usuarioVehiculo;
			return this;
		}

		public Builder espacioFisico(final EspacioFisicoDominio espacioFisico) {
			this.espacioFisico = espacioFisico;
			return this;
		}

		public ContratoMensualidadDominio build() {
			return new ContratoMensualidadDominio(this);
		}
	}

}