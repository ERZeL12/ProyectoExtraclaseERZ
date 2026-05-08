package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class AtencionVehiculoEntidad {

	private UUID id;
	private EntradaEntidad entrada;
	private ServicioEntidad servicio;

	private AtencionVehiculoEntidad(final Builder builder) {
		setId(builder.id);
		setEntrada(builder.entrada);
		setServicio(builder.servicio);
	}

	public UUID getId() {
		return id;
	}

	public EntradaEntidad getEntrada() {
		return entrada;
	}

	public ServicioEntidad getServicio() {
		return servicio;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setEntrada(final EntradaEntidad entrada) {
		this.entrada = UtilObjeto.obtenerValorDefecto(entrada, null);
	}

	private void setServicio(final ServicioEntidad servicio) {
		this.servicio = UtilObjeto.obtenerValorDefecto(servicio, null);
	}

	public static class Builder {

		private UUID id;
		private EntradaEntidad entrada;
		private ServicioEntidad servicio;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder entrada(final EntradaEntidad entrada) {
			this.entrada = entrada;
			return this;
		}

		public Builder servicio(final ServicioEntidad servicio) {
			this.servicio = servicio;
			return this;
		}

		public AtencionVehiculoEntidad build() {
			return new AtencionVehiculoEntidad(this);
		}
	}

}