package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class CiudadDTO {

	private UUID id;
	private String nombre;
	private DepartamentoDTO departamento;

	public CiudadDTO() {
		setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		setNombre("");
		setDepartamento(new DepartamentoDTO());
	}

	private CiudadDTO(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setDepartamento(builder.departamento);
	}

	public UUID getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public DepartamentoDTO getDepartamento() {
		return departamento;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}

	private void setDepartamento(final DepartamentoDTO departamento) {
		this.departamento = UtilObjeto.obtenerValorDefecto(departamento, null);
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private DepartamentoDTO departamento;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public Builder departamento(final DepartamentoDTO departamento) {
			this.departamento = departamento;
			return this;
		}

		public CiudadDTO build() {
			return new CiudadDTO(this);
		}
	}

}