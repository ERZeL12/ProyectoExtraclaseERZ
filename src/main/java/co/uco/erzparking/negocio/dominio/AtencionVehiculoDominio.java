package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class AtencionVehiculoDominio {

	private UUID id;
	private EntradaDominio entrada;
	private ServicioDominio servicio;

	private AtencionVehiculoDominio(final Builder builder) {
		setId(builder.id);
		setEntrada(builder.entrada);
		setServicio(builder.servicio);
	}

	public UUID getId() {
		return id;
	}

	public EntradaDominio getEntrada() {
		return entrada;
	}

	public ServicioDominio getServicio() {
		return servicio;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setEntrada(final EntradaDominio entrada) {
		this.entrada = UtilObjeto.obtenerValorDefecto(entrada, null);
	}

	private void setServicio(final ServicioDominio servicio) {
		this.servicio = UtilObjeto.obtenerValorDefecto(servicio, null);
	}

	public static class Builder {

		private UUID id;
		private EntradaDominio entrada;
		private ServicioDominio servicio;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder entrada(final EntradaDominio entrada) {
			this.entrada = entrada;
			return this;
		}

		public Builder servicio(final ServicioDominio servicio) {
			this.servicio = servicio;
			return this;
		}

		public AtencionVehiculoDominio build() {
			return new AtencionVehiculoDominio(this);
		}
	}

}