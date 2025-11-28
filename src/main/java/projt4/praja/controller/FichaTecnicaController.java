package projt4.praja.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projt4.praja.entity.dto.request.fichaTecnica.FichaTecnicaDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarDescricaoDTORequest;
import projt4.praja.entity.dto.request.shared.MudarDeGrupoDTORequest;
import projt4.praja.entity.dto.response.fichaTecnica.FichaTecnicaDTOResponse;
import projt4.praja.entity.dto.response.fichaTecnica.ListaFichasDeGrupoDTO;
import projt4.praja.entity.dto.response.shared.MudarDeGrupoDTOResponse;
import projt4.praja.service.FichaTecnicaService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ficha")
@Tag(name="Ficha Tecnica", description = "Api para gerenciamento de Fciha Tecnica")
public class FichaTecnicaController {
    private final FichaTecnicaService service;
    public FichaTecnicaController(FichaTecnicaService service) {
        this.service = service;
    }

    @PostMapping("/criar")
    @Operation(summary ="Criar nova Ficha Tecnica", description = "Endpoint para o registro de nova ficha tecnica")
    public ResponseEntity<FichaTecnicaDTOResponse> criar(
        @RequestBody FichaTecnicaDTORequest dtoRequest) {
        FichaTecnicaDTOResponse dtoResponse = this.service.criar(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    @GetMapping("/listar")
    @Operation(summary ="Listar Ficha Tecnica", description = "Endpoint para listar fichas tecnicas")
    public ResponseEntity<List<FichaTecnicaDTOResponse>> listar() {
        List<FichaTecnicaDTOResponse> dtoResponse = this.service.listar();
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }
		@GetMapping("/listar/dia")
		@Operation(summary ="Listar Ficha Tecnica", description = "Endpoint para listar fichas tecnicas")
		public ResponseEntity<List<FichaTecnicaDTOResponse>> listarDia() {
				List<FichaTecnicaDTOResponse> dtoResponse = this.service.listarDia();
				return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
		}

    @GetMapping("/listar/grupo/{id}/")
    @Operation(summary ="Listar Fichas Tecnicas de um grupo", description = "Endpoint para listar fichas tecnicas de um grupo")
    public ResponseEntity<ListaFichasDeGrupoDTO> listarFichasPorGrupo(
         @PathVariable Integer id
    ){
        ListaFichasDeGrupoDTO dtoResponse = this.service.listarPorGrupo(id);
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }

    
    @GetMapping("/buscar/{id}")
    @Operation(summary ="Buscar Ficha Tecnica", description = "Endpoint para buscar ficha tecnica")
    public ResponseEntity<FichaTecnicaDTOResponse> buscarPorId(
         @PathVariable Integer id
        ){
            FichaTecnicaDTOResponse dtoResponse = this.service.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
        }

	@PatchMapping("/alterar/descricao/{id}")
	@Operation(summary = "Path de Descricao", description =" Endpoint que altera a descricao da ficah tecnica e recebe de volta o objeto")
	public ResponseEntity<FichaTecnicaDTOResponse> alterarDescricao(
		@PathVariable("id") Integer id,
		@Valid @RequestBody AlterarDescricaoDTORequest dtoRequest
	) {
		FichaTecnicaDTOResponse dtoResponse = this.service.alterarDescricao(id, dtoRequest);
		if(dtoResponse != null){
			return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
		} else{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
    @PatchMapping("/alterar/grupo/{id}")
    @Operation(summary = "Alterar o grupo", description = "Endpoint para alterar o grupo de uma ficha tecnica")
    public ResponseEntity<MudarDeGrupoDTOResponse> alterarGrupo(
         @PathVariable Integer id,
         @RequestBody MudarDeGrupoDTORequest dtoRequest
    ){
        MudarDeGrupoDTOResponse dtoResponse = this.service.alterarGrupo(id, dtoRequest);
        return ResponseEntity.ok(dtoResponse);
    }
}
