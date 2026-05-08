package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.AtencionVehiculoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.AtencionVehiculoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class AtencionVehiculoDTOAssembler implements DTOAssembler<AtencionVehiculoDominio, AtencionVehiculoDTO> {

	// Patron Singleton
	private static DTOAssembler<AtencionVehiculoDominio, AtencionVehiculoDTO> INSTANCE;

	private AtencionVehiculoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<AtencionVehiculoDominio, AtencionVehiculoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new AtencionVehiculoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public AtencionVehiculoDominio ensamblarDominio(final AtencionVehiculoDTO dto) {
		var atencionVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new AtencionVehiculoDTO.Builder().build());
		return new AtencionVehiculoDominio.Builder()
				.id(atencionVehiculoAEnsamblar.getId())
				.entrada(EntradaDTOAssembler.getInstance().ensamblarDominio(atencionVehiculoAEnsamblar.getEntrada()))
				.servicio(ServicioDTOAssembler.getInstance().ensamblarDominio(atencionVehiculoAEnsamblar.getServicio()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public AtencionVehiculoDTO ensamblarDTO(final AtencionVehiculoDominio dominio) {
		var atencionVehiculoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new AtencionVehiculoDominio.Builder().build());
		return new AtencionVehiculoDTO.Builder()
				.id(atencionVehiculoAEnsamblar.getId())
				.entrada(EntradaDTOAssembler.getInstance().ensamblarDTO(atencionVehiculoAEnsamblar.getEntrada()))
				.servicio(ServicioDTOAssembler.getInstance().ensamblarDTO(atencionVehiculoAEnsamblar.getServicio()))
				.build();
	}

}