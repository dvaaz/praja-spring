package projt4.praja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projt4.praja.entity.dto.request.shared.AlterarUnidadeMedidaDTORequest;
import projt4.praja.entity.dto.request.ingredienteFichaTecnica.IngredienteFichaTecnicaDTORequest;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.AlterarUnidadeMedidaIngredienteFichaDTOResponse;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.IngredienteFichaTecnicaDTOResponse;
import projt4.praja.service.IngredienteFichaTecnicaService;

@Controller
@RequestMapping("/api/ingredientefichatecnica")
@Tag(name="Detalhes da Ficha Tecnica", description = "Api para gerenciamento de 'ingrediente ficha tecnica'")
public class IngredienteFichaTecnicaController {
  private final IngredienteFichaTecnicaService service;
  public IngredienteFichaTecnicaController( IngredienteFichaTecnicaService service) {
    this.service = service;
  }

  @PostMapping("/criar")
  @Operation(summary = "Registro de detalhes da Ficha Tecnica", description = "Endpoint para a criacao de uma nova entrada de Ingrediente Ficha Tecnica")
  public ResponseEntity<IngredienteFichaTecnicaDTOResponse> criarRelacaoIngredienteFichaTecnica(
      @Valid @RequestBody IngredienteFichaTecnicaDTORequest dto
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
  }



  @PatchMapping("/alterar/{id}/medidas")
  @Operation(summary = "Alteração de unidade de medida e quantidade", description = "Endpoint para alteração dos detalhes da medida da ficha tecnica")
  public ResponseEntity<AlterarUnidadeMedidaIngredienteFichaDTOResponse> alterarUnidadeMedida(
      @Valid @PathVariable Integer id,
      @Valid @RequestBody AlterarUnidadeMedidaDTORequest dto
  ) {
    return ResponseEntity.ok(service.alterarUnidadeMedida(id, dto));
  }

}