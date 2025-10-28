package projt4.praja.entity.dto.request.grupo;

import jakarta.validation.constraints.NotBlank;

public record AlterarCorDTORequest(
		@NotBlank String cor
) {
}
