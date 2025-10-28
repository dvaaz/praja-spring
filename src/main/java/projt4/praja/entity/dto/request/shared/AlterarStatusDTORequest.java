package projt4.praja.entity.dto.request.shared;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record AlterarStatusDTORequest (
    @Min(1) @Max(2) Integer status
){}
