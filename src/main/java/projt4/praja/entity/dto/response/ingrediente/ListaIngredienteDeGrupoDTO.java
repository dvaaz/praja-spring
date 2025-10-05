package projt4.praja.entity.dto.response.ingrediente;

import java.util.List;

public class ListaIngredienteDeGrupoDTO {
	private Integer idGrupo;
	private String nomeGrupo;
	private String corGrupo;
	private List<IngredienteDTOResponse> ingredientes;

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

		public List<IngredienteDTOResponse> getIngredientes() {
				return ingredientes;
		}

		public void setIngredientes(List<IngredienteDTOResponse> ingredientes) {
				this.ingredientes = ingredientes;
		}
}
