package projt4.praja.controller;

import projt4.praja.Enum.GrupoEnum;
import projt4.praja.entity.dto.request.grupo.GrupoDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarGrupoDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarStatusDTORequest;
import projt4.praja.entity.dto.response.grupo.GrupoAtualizarDTOResponse;
import projt4.praja.entity.dto.response.grupo.GrupoDTOResponse;
import projt4.praja.entity.dto.response.shared.AlterarStatusDTOResponse;
import projt4.praja.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@Tag(name="Grupo", description = "Api para gerenciamento de Grupo")
public class GrupoController {

    private final GrupoService service;

    public GrupoController(GrupoService service) {
        this.service = service;
    }

    private final Integer grupoIngrediente = GrupoEnum.ingrediente.getNumber(),
        grupoFicha = GrupoEnum.fichaTecnica.getNumber();


    @PostMapping("/criar")
    @Operation(summary = "Registro de novo grupo", description = "Endpoint para a criacao de novo objeto grupo")
    public ResponseEntity<GrupoDTOResponse> criar(
        @RequestBody GrupoDTORequest dtoRequest) {
        GrupoDTOResponse dtoResponse = this.service.criar(dtoRequest);
		    return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os grupos", description = "Endpoint para listar todos os grupos")
    public ResponseEntity<List<GrupoDTOResponse>> listar() {
        List<GrupoDTOResponse> dtoResponse = this.service.listar();
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("ingrediente/listar")
    @Operation(summary = "Listar grupos de ingredientes", description = "Endpoint para obter apenas grupos de ingredientes")
    public ResponseEntity<List<GrupoDTOResponse>> listarGruposDeIngrediente() {
        List<GrupoDTOResponse> dtoResponse = this.service.listarGruposDoTipo(grupoIngrediente);
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("fichatecnica/listar")
    @Operation(summary = "Listar grupos de ficha tecnica", description = "Endpoint para obter apenas grupos que contem fichas tecnicass")
    public ResponseEntity<List<GrupoDTOResponse>> listarGruposDeFichaTecnica() {
        List<GrupoDTOResponse> dtoResponse = this.service.listarGruposDoTipo(grupoFicha);
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "listar um grupo", description = "Endpoint para obter um grupo por id")
    public ResponseEntity<GrupoDTOResponse> buscarPorID(
         @PathVariable Integer id) {
        GrupoDTOResponse dtoResponse = this.service.buscarPorID(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PatchMapping("/alterar/{id}")
    @Operation(summary = "Alterações em um grupo", description = "Endpoint para alterar nome e cor de um grupo")
    public ResponseEntity<GrupoAtualizarDTOResponse> alterarDetalhes(
             @PathVariable Integer id,
            @RequestBody AlterarGrupoDTORequest dtoRequest) {
        GrupoAtualizarDTOResponse dtoResponse = this.service.alterarDetalhes(id, dtoRequest);
        return ResponseEntity.ok(dtoResponse);
    }

    @PatchMapping("/alterarstatus/{id}")
    @Operation(summary = "Atualizaçao de grupo", description = "Endpoint para atualização lógica de um grupo")
    public ResponseEntity<AlterarStatusDTOResponse> atualizarStatus(
         @PathVariable Integer id,
        @RequestBody AlterarStatusDTORequest dtoRequest
    ) {
        AlterarStatusDTOResponse statusResponse = this.service.atualizarStatus(id, dtoRequest);
        if (statusResponse != null){
            return ResponseEntity.ok(statusResponse);
        } else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/apagar/{id}")
    @Operation(summary = "Remover grupo", description = "Endpoint para remoção lógica de um grupo")
    public ResponseEntity apagar(
             @PathVariable Integer id
    ){
        boolean apagado = this.service.apagar(id);
        if (apagado) {
            return ResponseEntity.status(HttpStatus.OK).body("Grupo apagado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível apagar o grupo");
        }
    }
}