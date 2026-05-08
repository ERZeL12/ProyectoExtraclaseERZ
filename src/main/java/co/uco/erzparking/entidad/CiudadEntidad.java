package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilTexto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class CiudadEntidad {

	private UUID id;
	private String nombre;
	private DepartamentoEntidad departamento;

	private CiudadEntidad(final Builder builder) {
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

	public DepartamentoEntidad getDepartamento() {
		return departamento;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}

	private void setDepartamento(final DepartamentoEntidad departamento) {
		this.departamento = UtilObjeto.obtenerValorDefecto(departamento, null);
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private DepartamentoEntidad departamento;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public Builder departamento(final DepartamentoEntidad departamento) {
			this.departamento = departamento;
			return this;
		}

		public CiudadEntidad build() {
			return new CiudadEntidad(this);
		}
	}

}