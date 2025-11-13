package projt4.praja.entity.dto.response.estoque;

import java.time.LocalDate;


public class EstoqueDTOResponse {
	private Integer id;
	private LocalDate entrada;
	private LocalDate validade;
	private Integer qtd;
	private Integer ingrediente;
	private Integer status;

		public Integer getId() {
				return id;
		}

		public void setId(Integer id) {
				this.id = id;
		}

		public LocalDate getEntrada() {
				return entrada;
		}

		public void setEntrada(LocalDate entrada) {
				this.entrada = entrada;
		}

		public LocalDate getValidade() {
				return validade;
		}

		public void setValidade(LocalDate validade) {
				this.validade = validade;
		}

		public Integer getQtd() {
				return qtd;
		}

		public void setQtd(Integer qtd) {
				this.qtd = qtd;
		}

		public Integer getIngrediente() {
				return ingrediente;
		}

		public void setIngrediente(Integer ingrediente) {
				this.ingrediente = ingrediente;
		}

		public Integer getStatus() {
				return status;
		}

		public void setStatus(Integer status) {
				this.status = status;
		}
}
