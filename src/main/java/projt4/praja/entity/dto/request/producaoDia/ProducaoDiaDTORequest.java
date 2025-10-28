package projt4.praja.entity.dto.request.producaoDia;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record ProducaoDiaDTORequest(
		@NotBlank Date data
		){}
