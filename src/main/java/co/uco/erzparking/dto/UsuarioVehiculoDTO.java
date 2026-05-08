package co.uco.erzparking.dto;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class UsuarioVehiculoDTO {

	private UUID id;
	private UsuarioDTO usuario;
	private VehiculoDTO vehiculo;

	private UsuarioVehiculoDTO(final Builder builder) {
		setId(builder.id);
		setUsuario(builder.usuario);
		setVehiculo(builder.vehiculo);
	}

	public UUID getId() {
		return id;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public VehiculoDTO getVehiculo() {
		return vehiculo;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setUsuario(final UsuarioDTO usuario) {
		this.usuario = UtilObjeto.obtenerValorDefecto(usuario, null);
	}

	private void setVehiculo(final VehiculoDTO vehiculo) {
		this.vehiculo = UtilObjeto.obtenerValorDefecto(vehiculo, null);
	}

	public static class Builder {

		private UUID id;
		private UsuarioDTO usuario;
		private VehiculoDTO vehiculo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder usuario(final UsuarioDTO usuario) {
			this.usuario = usuario;
			return this;
		}

		public Builder vehiculo(final VehiculoDTO vehiculo) {
			this.vehiculo = vehiculo;
			return this;
		}

		public UsuarioVehiculoDTO build() {
			return new UsuarioVehiculoDTO(this);
		}
	}

}