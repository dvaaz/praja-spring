package projt4.praja.entity.dto.request.producaoDia;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;


public record ProducaoDiaDTORequest(
		@NotBlank LocalDate data
		){}
