package projt4.praja.entity.dto.request.ingredienteFichaTecnica;

import jakarta.validation.constraints.Min;

public record AlterarQtdIngredienteFichaDTORequest (
  @Min(1) Integer quantidade
){}