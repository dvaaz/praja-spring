package projt4.praja.entity.dto.request.ingrediente;

import jakarta.validation.constraints.NotBlank;

public class IngredienteDTORequest {
    @NotBlank
		private String nome;
    private String descricao;
    private Integer grupo;

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

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }
}
