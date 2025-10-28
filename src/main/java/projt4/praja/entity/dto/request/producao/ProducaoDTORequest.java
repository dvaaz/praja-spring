package projt4.praja.entity.dto.request.producao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProducaoDTORequest(
	@Min(1) Integer qtd,
	@NotNull Integer estoque,
	@NotNull Integer producaoDia
	){}
