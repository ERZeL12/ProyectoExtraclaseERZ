package co.uco.erzparking.negocio.assembler.dto.impl;

import co.uco.erzparking.dto.EntradaDTO;
import co.uco.erzparking.negocio.assembler.dto.DTOAssembler;
import co.uco.erzparking.negocio.dominio.EntradaDominio;
import co.uco.erzparking.transversal.UtilObjeto;


public final class EntradaDTOAssembler implements DTOAssembler<EntradaDominio, EntradaDTO> {

	private static DTOAssembler<EntradaDominio, EntradaDTO> INSTANCE;

	private EntradaDTOAssembler() {
		super();
	}

	public synchronized static final DTOAssembler<EntradaDominio, EntradaDTO> getInstance() {
		if (UtilObjeto.esNulo(INSTANCE)) {
			INSTANCE = new EntradaDTOAssembler();
		}
		return INSTANCE;
	}

	@Override
	public EntradaDominio ensamblarDominio(final EntradaDTO dto) {
		var entradaAEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new EntradaDTO.Builder().build());
		return new EntradaDominio.Builder()
				.id(entradaAEnsamblar.getId())
				.fechaHoraEntrada(entradaAEnsamblar.getFechaHoraEntrada())
				.vehiculo(VehiculoDTOAssembler.getInstance().ensamblarDominio(entradaAEnsamblar.getVehiculo()))
				.servicio(ServicioDTOAssembler.getInstance().ensamblarDominio(entradaAEnsamblar.getServicio()))
				.build();
	}

	@Override
	public EntradaDTO ensamblarDTO(final EntradaDominio dominio) {
		var entradaAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new EntradaDominio.Builder().build());
		return new EntradaDTO.Builder()
				.id(entradaAEnsamblar.getId())
				.fechaHoraEntrada(entradaAEnsamblar.getFechaHoraEntrada())
				.vehiculo(VehiculoDTOAssembler.getInstance().ensamblarDTO(entradaAEnsamblar.getVehiculo()))
				.servicio(ServicioDTOAssembler.getInstance().ensamblarDTO(entradaAEnsamblar.getServicio()))
				.build();
	}

}
