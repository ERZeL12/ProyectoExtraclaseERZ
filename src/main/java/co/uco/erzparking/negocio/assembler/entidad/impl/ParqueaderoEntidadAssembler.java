package co.uco.erzparking.negocio.assembler.entidad.impl;

import co.uco.erzparking.entidad.ParqueaderoEntidad;
import co.uco.erzparking.negocio.assembler.entidad.EntidadAssembler;
import co.uco.erzparking.negocio.dominio.ParqueaderoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class ParqueaderoEntidadAssembler implements EntidadAssembler<ParqueaderoDominio, ParqueaderoEntidad> {

	// Patron Singleton
	private static EntidadAssembler<ParqueaderoDominio, ParqueaderoEntidad> INSTANCE;

	private ParqueaderoEntidadAssembler() {
		super();
	}

	public synchronized static final EntidadAssembler<ParqueaderoDominio, ParqueaderoEntidad> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new ParqueaderoEntidadAssembler();
		}
		return INSTANCE;
	}

	// Convierte Dominio a Entidad
	@Override
	public ParqueaderoEntidad ensamblarEntidad(final ParqueaderoDominio dominio) {
		var parqueaderoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ParqueaderoDominio.Builder().build());
		return new ParqueaderoEntidad.Builder()
				.id(parqueaderoAEnsamblar.getId())
				.nombreEstablecimiento(parqueaderoAEnsamblar.getNombreEstablecimiento())
				.numeroTelefonico(parqueaderoAEnsamblar.getNumeroTelefonico())
				.correoElectronico(parqueaderoAEnsamblar.getCorreoElectronico())
				.direccionEstablecimiento(parqueaderoAEnsamblar.getDireccionEstablecimiento())
				.ciudad(CiudadEntidadAssembler.getInstance().ensamblarEntidad(parqueaderoAEnsamblar.getCiudad()))
				.build();
	}

	// Convierte Entidad a Dominio
	@Override
	public ParqueaderoDominio ensamblarDominio(final ParqueaderoEntidad entidad) {
		var parqueaderoAEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new ParqueaderoEntidad.Builder().build());
		return new ParqueaderoDominio.Builder()
				.id(parqueaderoAEnsamblar.getId())
				.nombreEstablecimiento(parqueaderoAEnsamblar.getNombreEstablecimiento())
				.numeroTelefonico(parqueaderoAEnsamblar.getNumeroTelefonico())
				.correoElectronico(parqueaderoAEnsamblar.getCorreoElectronico())
				.direccionEstablecimiento(parqueaderoAEnsamblar.getDireccionEstablecimiento())
				.ciudad(CiudadEntidadAssembler.getInstance().ensamblarDominio(parqueaderoAEnsamblar.getCiudad()))
				.build();
	}

}