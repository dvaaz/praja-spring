package projt4.praja.entity.dto.request.ingredienteFichaTecnica;

import jakarta.validation.Valid;

import java.util.List;

public record IngredienteEmFichaWrapperDTORequest(
    List<IngredienteFichaTecnicaDTORequest> ingredienteFichaTecnicaDTORequest
) {
}
