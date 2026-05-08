package co.uco.erzparking.entidad;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class UsuarioVehiculoEntidad {

	private UUID id;
	private UsuarioEntidad usuario;
	private VehiculoEntidad vehiculo;

	private UsuarioVehiculoEntidad(final Builder builder) {
		setId(builder.id);
		setUsuario(builder.usuario);
		setVehiculo(builder.vehiculo);
	}

	public UUID getId() {
		return id;
	}

	public UsuarioEntidad getUsuario() {
		return usuario;
	}

	public VehiculoEntidad getVehiculo() {
		return vehiculo;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setUsuario(final UsuarioEntidad usuario) {
		this.usuario = UtilObjeto.obtenerValorDefecto(usuario, null);
	}

	private void setVehiculo(final VehiculoEntidad vehiculo) {
		this.vehiculo = UtilObjeto.obtenerValorDefecto(vehiculo, null);
	}

	public static class Builder {

		private UUID id;
		private UsuarioEntidad usuario;
		private VehiculoEntidad vehiculo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder usuario(final UsuarioEntidad usuario) {
			this.usuario = usuario;
			return this;
		}

		public Builder vehiculo(final VehiculoEntidad vehiculo) {
			this.vehiculo = vehiculo;
			return this;
		}

		public UsuarioVehiculoEntidad build() {
			return new UsuarioVehiculoEntidad(this);
		}
	}

}