package projt4.praja.entity.dto.request.fichaTecnica;


import jakarta.validation.constraints.NotEmpty;

public record FichaTecnicaDTORequest (
		@NotEmpty String nome,
    String descricao,
    Integer grupo
){}
