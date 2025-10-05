package projt4.praja.entity.dto.response.fichaTecnica;

import java.util.List;

public class ListaFichasDeGrupoDTO {
	private Integer idGrupo;
	private String nomeGrupo;
	private String corGrupo;
	private List<IngredienteDTOResponse> fichasTecnicas;

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public String getCorGrupo() {
		return corGrupo;
	}

	public void setCorGrupo(String corGrupo) {
		this.corGrupo = corGrupo;
	}

	public List<IngredienteDTOResponse> getFichasTecnicas() {
		return fichasTecnicas;
	}

	public void setFichasTecnicas(List<IngredienteDTOResponse> fichasTecnicas) {
		this.fichasTecnicas = fichasTecnicas;
	}
}
