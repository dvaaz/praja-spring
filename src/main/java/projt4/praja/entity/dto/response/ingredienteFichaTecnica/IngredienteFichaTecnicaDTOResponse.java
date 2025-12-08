package projt4.praja.entity.dto.response.ingredienteFichaTecnica;

public class IngredienteFichaTecnicaDTOResponse {
    private Integer id;
    private Integer unidadeMedida;
    private Integer qtd;
    private String detalhe;
    private Integer ingrediente;
    private Integer fichaTecnica;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public Integer getUnidadeMedida() {
        return unidadeMedida;
    }

    public Integer getQtd() {
        return qtd;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public Integer getIngrediente() {
        return ingrediente;
    }

    public Integer getFichaTecnica() {
        return fichaTecnica;
    }

    public Integer getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUnidadeMedida(Integer unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public void setIngrediente(Integer ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void setFichaTecnica(Integer fichaTecnica) {
        this.fichaTecnica = fichaTecnica;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
