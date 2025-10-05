package projt4.praja.entity.dto.response.fichaTecnica;

import java.util.List;

public class IngredientesEmFichaDTOResponse {
	private Integer idFicha;
	private String nomeFicha;
	private String descricaoFicha;
	private List<IngredientesEmFichaDTOResponse> ingredientes;

		public Integer getIdFicha() {
				return idFicha;
		}

		public void setIdFicha(Integer idFicha) {
				this.idFicha = idFicha;
		}

		public String getNomeFicha() {
				return nomeFicha;
		}

		public void setNomeFicha(String nomeFicha) {
				this.nomeFicha = nomeFicha;
		}

		public String getDescricaoFicha() {
				return descricaoFicha;
		}

		public void setDescricaoFicha(String descricaoFicha) {
				this.descricaoFicha = descricaoFicha;
		}

		public List<IngredientesEmFichaDTOResponse> getIngredientes() {
				return ingredientes;
		}

		public void setIngredientes(List<IngredientesEmFichaDTOResponse> ingredientes) {
				this.ingredientes = ingredientes;
		}
}
