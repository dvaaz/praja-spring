package projt4.praja.entity.dto.response.ingrediente;

public class IngredienteSemGrupoDTOResponse {
		private Integer id;
		private String nome;
		private String descricao;
		private Integer unidadeMedida;

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

		public Integer getUnidadeMedida() {
				return unidadeMedida;
		}

		public void setUnidadeMedida(Integer unidadeMedida) {
				this.unidadeMedida = unidadeMedida;
		}
}
