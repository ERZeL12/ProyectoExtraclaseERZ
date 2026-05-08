package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class AtencionVehiculoDTO {  

	private UUID id;
	private EntradaDTO entrada;
	private ServicioDTO servicio;

	private AtencionVehiculoDTO(final Builder builder) {
		setId(builder.id);
		setEntrada(builder.entrada);
		setServicio(builder.servicio);
	}

	public UUID getId() {
		return id;
	}

	public EntradaDTO getEntrada() {
		return entrada;
	}

	public ServicioDTO getServicio() {
		return servicio;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setEntrada(final EntradaDTO entrada) {
		this.entrada = UtilObjeto.obtenerValorDefecto(entrada, null);
	}

	private void setServicio(final ServicioDTO servicio) {
		this.servicio = UtilObjeto.obtenerValorDefecto(servicio, null);
	}

	public static class Builder {

		private UUID id;
		private EntradaDTO entrada;
		private ServicioDTO servicio;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder entrada(final EntradaDTO entrada) {
			this.entrada = entrada;
			return this;
		}

		public Builder servicio(final ServicioDTO servicio) {
			this.servicio = servicio;
			return this;
		}

		public AtencionVehiculoDTO build() {
			return new AtencionVehiculoDTO(this);
		}
	}

}