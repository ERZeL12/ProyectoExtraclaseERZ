package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilLocalDateTime;
import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.time.LocalDateTime;
import java.util.UUID;


public class SalidaDTO {

	private UUID id;
	private LocalDateTime fechaHoraSalida;
	private EntradaDTO entrada;

	private SalidaDTO(final Builder builder) {
		setId(builder.id);
		setFechaHoraSalida(builder.fechaHoraSalida);
		setEntrada(builder.entrada);
	}

	public UUID getId() {
		return id;
	}

	public LocalDateTime getFechaHoraSalida() {
		return fechaHoraSalida;
	}

	public EntradaDTO getEntrada() {
		return entrada;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setFechaHoraSalida(final LocalDateTime fechaHoraSalida) {
		this.fechaHoraSalida = UtilLocalDateTime.obtenerValorDefecto(fechaHoraSalida);
	}

	private void setEntrada(final EntradaDTO entrada) {
		this.entrada = UtilObjeto.obtenerValorDefecto(entrada, null);
	}

	public static class Builder {

		private UUID id;
		private LocalDateTime fechaHoraSalida;
		private EntradaDTO entrada;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder fechaHoraSalida(final LocalDateTime fechaHoraSalida) {
			this.fechaHoraSalida = UtilLocalDateTime.obtenerValorDefecto(fechaHoraSalida);
			return this;
		}

		public Builder entrada(final EntradaDTO entrada) {
			this.entrada = entrada;
			return this;
		}

		public SalidaDTO build() {
			return new SalidaDTO(this);
		}
	}

}
