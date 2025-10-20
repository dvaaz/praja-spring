package projt4.praja.entity.dto.request.ingredienteFichaTecnica;

public class IngredienteFichaTecnicaDTORequest {

    private Integer unidadeMedida;
    private Integer qtd;
    private String detalhe;
    private Integer ingrediente;
    private Integer fichaTecnica;

    public Integer getUnidadeMedida() {
    return unidadeMedida;
    }

    public void setUnidadeMedida(Integer unidadeMedida) {
    this.unidadeMedida = unidadeMedida;
    }

    public Integer getQtd() {
    return qtd;
    }

    public void setQtd(Integer qtd) {
    this.qtd = qtd;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public Integer getIngrediente() {
    return ingrediente;
    }

    public void setIngrediente(Integer ingrediente) {
    this.ingrediente = ingrediente;
    }

    public Integer getFichaTecnica() {
    return fichaTecnica;
    }

    public void setFichaTecnica(Integer fichaTecnica) {
    this.fichaTecnica = fichaTecnica;
    }

    }
