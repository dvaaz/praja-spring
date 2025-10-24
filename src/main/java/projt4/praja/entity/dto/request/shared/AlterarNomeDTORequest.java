package projt4.praja.entity.dto.request.shared;

import jakarta.validation.constraints.NotBlank;

public record AlterarNomeDTORequest(
    @NotBlank String nome
		){}
