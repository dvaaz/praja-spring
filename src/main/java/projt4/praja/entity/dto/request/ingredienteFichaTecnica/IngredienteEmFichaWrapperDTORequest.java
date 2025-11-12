package projt4.praja.entity.dto.request.ingredienteFichaTecnica;

import jakarta.validation.Valid;

import java.util.List;

public class IngredienteEmFichaWrapperDTORequest{
    List<IngredienteFichaTecnicaDTORequest> ingredienteFichaTecnicaDTORequest;

  public List<IngredienteFichaTecnicaDTORequest> getIngredienteFichaTecnicaDTORequest() {
    return ingredienteFichaTecnicaDTORequest;
  }

  public void setIngredienteFichaTecnicaDTORequest(List<IngredienteFichaTecnicaDTORequest> ingredienteFichaTecnicaDTORequest) {
    this.ingredienteFichaTecnicaDTORequest = ingredienteFichaTecnicaDTORequest;
  }
}
