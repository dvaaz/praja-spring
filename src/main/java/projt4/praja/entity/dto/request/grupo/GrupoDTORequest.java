package projt4.praja.entity.dto.request.grupo;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record GrupoDTORequest (

		@NotEmpty String nome,
		@NotEmpty String cor,

		@Min(1)@Max(2) Integer  tipo
		){}
