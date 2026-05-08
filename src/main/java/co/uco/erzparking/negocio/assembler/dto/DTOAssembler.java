package co.uco.erzparking.negocio.assembler.dto;

// D: Representa un dominio (Domain) y T: Representa un Transfer Object (DTO)
public interface DTOAssembler<D, T> {

	D ensamblarDominio(T dto);

	T ensamblarDTO(D dominio);

}
