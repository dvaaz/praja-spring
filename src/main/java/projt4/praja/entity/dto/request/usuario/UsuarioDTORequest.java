package projt4.praja.entity.dto.request.usuario;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import projt4.praja.Enum.RoleName;

import java.util.List;

public record UsuarioDTORequest (
		@NotEmpty String nome,
    @NotEmpty String telefone,
    @NotEmpty @Min(6) @Max(12) String senha,
    @Valid @NotEmpty @NotEmpty List<RoleName> roleList
){}
