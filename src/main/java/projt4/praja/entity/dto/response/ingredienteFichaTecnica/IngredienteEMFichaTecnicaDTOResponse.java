package projt4.praja.entity.dto.response.ingredienteFichaTecnica;


public class IngredienteEMFichaTecnicaDTOResponse {
    private Integer id;
    private String detalhe;
    private Integer unidadeMedida;
		private String unidadeMedidaString;
    private Integer qtd;
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

		public String getUnidadeMedidaString() {
				return unidadeMedidaString;
		}

		public void setUnidadeMedidaString(String unidadeMedidaString) {
				this.unidadeMedidaString = unidadeMedidaString;
		}

		public Integer getQtd() {
				return qtd;
		}

		public void setQtd(Integer qtd) {
				this.qtd = qtd;
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
