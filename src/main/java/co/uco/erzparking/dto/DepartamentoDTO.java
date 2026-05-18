package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class DepartamentoDTO {

	private UUID id;
	private String nombre;
	private PaisDTO pais;

	public DepartamentoDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setNombre("");
		setPais(new PaisDTO());
	}

	private DepartamentoDTO(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setPais(builder.pais);
	}

	public UUID getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public PaisDTO getPais() {
		return pais;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}

	private void setPais(final PaisDTO pais) {
		this.pais = UtilObjeto.obtenerValorDefecto(pais, null);
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private PaisDTO pais;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public Builder pais(final PaisDTO pais) {
			this.pais = pais;
			return this;
		}

		public DepartamentoDTO build() {
			return new DepartamentoDTO(this);
		}
	}

}