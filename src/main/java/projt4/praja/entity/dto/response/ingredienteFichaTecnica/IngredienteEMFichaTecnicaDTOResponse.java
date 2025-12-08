package projt4.praja.entity.dto.response.ingredienteFichaTecnica;


public class IngredienteEMFichaTecnicaDTOResponse {
    private Integer id;
    private String detalhe;
    private Integer unidadeMedida;
    private Integer quantidade;
    private Integer idIngrediente;
    private String nomeIngrediente;

		public Integer getId() {
				return id;
		}

		public void setId(Integer id) {
				this.id = id;
		}

		public String getDetalhe() {
				return detalhe;
		}

		public void setDetalhe(String detalhe) {
				this.detalhe = detalhe;
		}

		public Integer getUnidadeMedida() {
				return unidadeMedida;
		}

		public void setUnidadeMedida(Integer unidadeMedida) {
				this.unidadeMedida = unidadeMedida;
		}


		public Integer getsetQuantidade() {
				return quantidade;
		}

		public void setQuantidade(Integer quantidade) {
				this.quantidade = quantidade;
		}

		public Integer getIdIngrediente() {
				return idIngrediente;
		}

		public void setIdIngrediente(Integer idIngrediente) {
				this.idIngrediente = idIngrediente;
		}

		public String getNomeIngrediente() {
				return nomeIngrediente;
		}

		public void setNomeIngrediente(String nomeIngrediente) {
				this.nomeIngrediente = nomeIngrediente;
		}
}
