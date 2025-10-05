package projt4.praja.controller;




import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projt4.praja.entity.dto.request.ingrediente.IngredienteDTORequest;
import projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse;
import projt4.praja.entity.dto.response.ingrediente.ListaIngredienteDeGrupoDTO;
import projt4.praja.service.IngredienteService;

import java.util.List;


@RestController
@RequestMapping("/api/ingrediente")
@Tag(name="Ingrediente", description = "Api para gerenciamento de Fciha Tecnica")
public class IngredienteController {

    private final IngredienteService service;

		public IngredienteController(IngredienteService service) {
				this.service = service;
		}

		@PostMapping("/criar")
    @Operation(summary ="Criar nova Ingrediente", description = "Endpoint para o registro de nova ingrediente")
    public ResponseEntity<IngredienteDTOResponse> criar(
        @RequestBody IngredienteDTORequest dtoRequest) {
        IngredienteDTOResponse dtoResponse = this.service.criar(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    @GetMapping("/listar")
    @Operation(summary ="Listar Ingrediente", description = "Endpoint para listar ingredientes tecnicas")
    public ResponseEntity<List<IngredienteDTOResponse>> listar() {
        List<IngredienteDTOResponse> dtoResponse = this.service.listar();
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }

    @GetMapping("/listar/grupo/{id}")
    @Operation(summary ="Listar Ingrediente de um grupo", description = "Endpoint para listar ingredientes tecnicas de um grupo")
    public ResponseEntity<ListaIngredienteDeGrupoDTO> listarPorGrupo(
        @PathVariable Integer id
    ){
        ListaIngredienteDeGrupoDTO dtoResponse = this.service.listarPorGrupo(id);
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary ="Buscar Ingrediente", description = "Endpoint para buscar ingrediente")
    public ResponseEntity<IngredienteDTOResponse> buscarPorId(
        @PathVariable Integer id
    ){
        IngredienteDTOResponse dtoResponse = this.service.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }

}
