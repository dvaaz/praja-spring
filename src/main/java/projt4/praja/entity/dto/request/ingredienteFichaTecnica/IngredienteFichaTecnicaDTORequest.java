package projt4.praja.entity.dto.request.ingredienteFichaTecnica;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;


@Validated
public record IngredienteFichaTecnicaDTORequest (

		@Min(0) @Max(2) Integer unidadeMedida,
    @Min(1) Integer qtd,
    @NotNull String detalhe,
    @NotNull Integer ingrediente,
    @NotNull Integer fichaTecnica
){}