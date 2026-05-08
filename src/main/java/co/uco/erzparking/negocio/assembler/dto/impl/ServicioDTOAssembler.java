package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.ServicioDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.ServicioDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class ServicioDTOAssembler implements DTOAssembler<ServicioDominio, ServicioDTO> {

	// Patron Singleton
	private static DTOAssembler<ServicioDominio, ServicioDTO> INSTANCE;

	private ServicioDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<ServicioDominio, ServicioDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new ServicioDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public ServicioDominio ensamblarDominio(final ServicioDTO dto) {
		var servicioAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new ServicioDTO.Builder().build());
		return new ServicioDominio.Builder()
				.id(servicioAEnsamblar.getId())
				.nombreServicio(servicioAEnsamblar.getNombreServicio())
				.tipoServicio(TipoServicioDTOAssembler.getInstance().ensamblarDominio(servicioAEnsamblar.getTipoServicio()))
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDominio(servicioAEnsamblar.getParqueadero()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public ServicioDTO ensamblarDTO(final ServicioDominio dominio) {
		var servicioAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new ServicioDominio.Builder().build());
		return new ServicioDTO.Builder()
				.id(servicioAEnsamblar.getId())
				.nombreServicio(servicioAEnsamblar.getNombreServicio())
				.tipoServicio(TipoServicioDTOAssembler.getInstance().ensamblarDTO(servicioAEnsamblar.getTipoServicio()))
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDTO(servicioAEnsamblar.getParqueadero()))
				.build();
	}

}