package projt4.praja.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projt4.praja.entity.dto.request.ingredienteFichaTecnica.IngredienteEmFichaWrapperDTORequest;
import projt4.praja.entity.dto.request.ingredienteFichaTecnica.IngredienteFichaTecnicaDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarUnidadeMedidaDTORequest;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.AlterarUnidadeMedidaIngredienteFichaDTOResponse;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.IngredienteEMFichaTecnicaDTOResponse;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.IngredienteFichaTecnicaDTOResponse;
import projt4.praja.service.IngredienteFichaTecnicaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredientefichatecnica")
@Tag(name="Detalhes da Ficha Tecnica", description = "Api para gerenciamento de 'ingrediente ficha tecnica'")
public class IngredienteFichaTecnicaController {
  private final IngredienteFichaTecnicaService service;
  public IngredienteFichaTecnicaController( IngredienteFichaTecnicaService service) {
    this.service = service;
  }

  @PostMapping("/criar")
  @Operation(summary = "Registro de detalhes da Ficha Tecnica", description = "Endpoint para a criacao de uma nova entrada de Ingrediente Ficha Tecnica")
  public ResponseEntity<List<IngredienteEMFichaTecnicaDTOResponse>> criarRelacaoIngredienteFichaTecnica(
      @RequestBody IngredienteEmFichaWrapperDTORequest dto
  ) {
//			List<IngredienteEMFichaTecnicaDTOResponse> dtoResponse = service.criar(dto);
//      for ( IngredienteFichaTecnicaDTORequest ingrediente : dto ) {
//          IngredienteEMFichaTecnicaDTOResponse ingredienteAdicionado = service.adicionarItem(ingrediente);
//          dtoResponse.add(ingredienteAdicionado);
//      }
//			if(dtoResponse.isEmpty()) {
//					return ResponseEntity.noContent().build();
//			} else return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
        List<IngredienteEMFichaTecnicaDTOResponse> dtoResponse= new ArrayList<IngredienteEMFichaTecnicaDTOResponse>();
      for (IngredienteFichaTecnicaDTORequest ingrediente : dto.ingredienteFichaTecnicaDTORequest()) {
          System.out.println( ingrediente );
//          dtoResponse.add(service.adicionarItem(ingrediente));
      }
//
//      if (dtoResponse.isEmpty()){
          return ResponseEntity.badRequest().build();
//
//      }          return ResponseEntity.ok().body(dtoResponse);
  }
	// teste
		@PostMapping("/teste")
		@Operation(summary = "Teste de criacao de um item")
		public ResponseEntity<IngredienteEMFichaTecnicaDTOResponse> testeCriar(
				@Valid @RequestBody IngredienteFichaTecnicaDTORequest dto
		){
				IngredienteEMFichaTecnicaDTOResponse dtoResponse = service.adicionarItem(dto);
				if (dtoResponse == null) { return ResponseEntity.noContent().build(); }
				return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
		}

	@GetMapping("buscar/{id}")
	@Operation(summary = "Busca dados de uma Ficha Tecnica", description = "Endpoint que lista todos os ingredientes de uma ficha tecnica")
	public ResponseEntity<List<IngredienteEMFichaTecnicaDTOResponse>> listarIngredientesEmFichaTecnica(
			@Valid @PathVariable("id") Integer id
	){
			List<IngredienteEMFichaTecnicaDTOResponse> dtoResponses = this.service.listarIngredientesEmFichaTecnica(id);
			if(dtoResponses==null){
					return ResponseEntity.noContent().build();
			} else return ResponseEntity.ok(dtoResponses);
	}


  @PatchMapping("/alterar/{id}/medidas")
  @Operation(summary = "Alteração de unidade de medida e quantidade", description = "Endpoint para alteração dos detalhes da medida da ficha tecnica")
  public ResponseEntity<AlterarUnidadeMedidaIngredienteFichaDTOResponse> alterarUnidadeMedida(
      @Valid @PathVariable("id") Integer id,
      @Valid @RequestBody AlterarUnidadeMedidaDTORequest dto
  ) {
    return ResponseEntity.ok(service.alterarUnidadeMedida(id, dto));
  }

}