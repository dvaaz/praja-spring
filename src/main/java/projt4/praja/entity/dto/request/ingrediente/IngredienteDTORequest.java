package projt4.praja.entity.dto.request.ingrediente;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;


public record IngredienteDTORequest (
		@NotEmpty String nome,
		String descricao,
		Integer grupo,
		@Min(0) @Max(2) Integer unidadeMedida

		){}
