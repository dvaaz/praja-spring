package projt4.praja.entity.dto.request.shared;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record AlterarUnidadeMedidaDTORequest (
		@Min(0) @Max(2) Integer unidadeMedida
){}