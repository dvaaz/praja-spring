package projt4.praja.entity.dto.request.estoque;


import jakarta.validation.constraints.Min;

public record EstoqueQtdDTORequest (
  @Min(1) Integer qtd
){}
