package projt4.praja.entity.dto.request.estoque;



import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;

public record EstoqueDTORequest (
    @PastOrPresent(message = "A data de validade tem de ser presente ou passado")
		Date entrada,
		 @FutureOrPresent(message = "A data de validade tem de ser presente ou futura")
     Date validade,
		 @Min(1)
     Integer qtd,
		 @NotBlank
     Integer ingrediente
){}