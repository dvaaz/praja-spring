package projt4.praja.entity.dto.request.shared;

import jakarta.validation.constraints.Min;

public record MudarDeGrupoDTORequest(
		@Min(1) Integer grupo
){}
