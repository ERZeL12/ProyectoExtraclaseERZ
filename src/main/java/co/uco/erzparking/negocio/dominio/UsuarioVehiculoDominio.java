package co.uco.erzparking.negocio.dominio;

import co.uco.erzparking.transversal.UtilObjeto;
import co.uco.erzparking.transversal.UtilUUID;
import java.util.UUID;


public class UsuarioVehiculoDominio {

	private UUID id;
	private UsuarioDominio usuario;
	private VehiculoDominio vehiculo;

	private UsuarioVehiculoDominio(final Builder builder) {
		setId(builder.id);
		setUsuario(builder.usuario);
		setVehiculo(builder.vehiculo);
	}

	public UUID getId() {
		return id;
	}

	public UsuarioDominio getUsuario() {
		return usuario;
	}

	public VehiculoDominio getVehiculo() {
		return vehiculo;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	private void setUsuario(final UsuarioDominio usuario) {
		this.usuario = UtilObjeto.obtenerValorDefecto(usuario, null);
	}

	private void setVehiculo(final VehiculoDominio vehiculo) {
		this.vehiculo = UtilObjeto.obtenerValorDefecto(vehiculo, null);
	}

	public static class Builder {

		private UUID id;
		private UsuarioDominio usuario;
		private VehiculoDominio vehiculo;

		public Builder id(final UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder usuario(final UsuarioDominio usuario) {
			this.usuario = usuario;
			return this;
		}

		public Builder vehiculo(final VehiculoDominio vehiculo) {
			this.vehiculo = vehiculo;
			return this;
		}

		public UsuarioVehiculoDominio build() {
			return new UsuarioVehiculoDominio(this);
		}
	}

}