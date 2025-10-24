package projt4.praja.entity.dto.request.shared;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

// talvez nao seja uma boa ideia ao chamar os ids dentro da lista... talvez
public record MudarDeGrupoEmLoteDTORequest (
		@Valid @NotNull @Min(1) List<Integer> idDosItens

){}
