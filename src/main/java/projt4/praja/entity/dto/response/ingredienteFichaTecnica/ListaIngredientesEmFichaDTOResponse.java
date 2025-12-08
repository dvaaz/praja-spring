package projt4.praja.entity.dto.response.ingredienteFichaTecnica;

import java.util.List;

public class ListaIngredientesEmFichaDTOResponse {
	private Integer id;
	private String nome;
	private String descricao;
	private List<IngredienteEMFichaTecnicaDTOResponse> ingredientes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<IngredienteEMFichaTecnicaDTOResponse> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteEMFichaTecnicaDTOResponse> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
