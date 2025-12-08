package projt4.praja.entity.dto.response.grupo;

import projt4.praja.entity.dto.response.fichaTecnica.FichaDTOResponse;

import java.util.List;

public class GrupoAtivoDTOResponse {
    private Integer id;
    private String nome;
    private String cor;
    private List<FichaDTOResponse>  fichas;

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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public List<FichaDTOResponse> getFichas() {
        return fichas;
    }

    public void setFichas(List<FichaDTOResponse> fichas) {
        this.fichas = fichas;
    }
}
