package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.EspacioFisicoDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.EspacioFisicoDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class EspacioFisicoDTOAssembler implements DTOAssembler<EspacioFisicoDominio, EspacioFisicoDTO> {

	// Patron Singleton
	private static DTOAssembler<EspacioFisicoDominio, EspacioFisicoDTO> INSTANCE;

	private EspacioFisicoDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<EspacioFisicoDominio, EspacioFisicoDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new EspacioFisicoDTOAssembler();
		}
		return INSTANCE;
	}

	// Convierte DTO a Dominio
	@Override
	public EspacioFisicoDominio ensamblarDominio(final EspacioFisicoDTO dto) {
		var espacioFisicoAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new EspacioFisicoDTO.Builder().build());
		return new EspacioFisicoDominio.Builder()
				.id(espacioFisicoAEnsamblar.getId())
				.numeroEspacioFisico(espacioFisicoAEnsamblar.getNumeroEspacioFisico())
				.tipoServicio(TipoServicioDTOAssembler.getInstance().ensamblarDominio(espacioFisicoAEnsamblar.getTipoServicio()))
				.estadoEspacioFisico(EstadoEspacioFisicoDTOAssembler.getInstance().ensamblarDominio(espacioFisicoAEnsamblar.getEstadoEspacioFisico()))
				.zonaEspacioFisico(ZonaParqueaderoDTOAssembler.getInstance().ensamblarDominio(espacioFisicoAEnsamblar.getZonaEspacioFisico()))
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDominio(espacioFisicoAEnsamblar.getParqueadero()))
				.build();
	}

	// Convierte Dominio a DTO
	@Override
	public EspacioFisicoDTO ensamblarDTO(final EspacioFisicoDominio dominio) {
		var espacioFisicoAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EspacioFisicoDominio.Builder().build());
		return new EspacioFisicoDTO.Builder()
				.id(espacioFisicoAEnsamblar.getId())
				.numeroEspacioFisico(espacioFisicoAEnsamblar.getNumeroEspacioFisico())
				.tipoServicio(TipoServicioDTOAssembler.getInstance().ensamblarDTO(espacioFisicoAEnsamblar.getTipoServicio()))
				.estadoEspacioFisico(EstadoEspacioFisicoDTOAssembler.getInstance().ensamblarDTO(espacioFisicoAEnsamblar.getEstadoEspacioFisico()))
				.zonaEspacioFisico(ZonaParqueaderoDTOAssembler.getInstance().ensamblarDTO(espacioFisicoAEnsamblar.getZonaEspacioFisico()))
				.parqueadero(ParqueaderoDTOAssembler.getInstance().ensamblarDTO(espacioFisicoAEnsamblar.getParqueadero()))
				.build();
	}

}