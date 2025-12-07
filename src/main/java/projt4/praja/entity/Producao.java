package projt4.praja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "producao")
public class Producao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producao_id")
	private Integer id;
	@Column(name = "producao_qtd", nullable=false)
	private Integer quantidade;
	@Column(name = "producao_status", nullable=false)
	private Integer status;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "producao_dia_id", nullable=false)
	private ProducaoDia producaoDia;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "estoque_id", nullable=false)
	private Estoque estoque;

		public Integer getId() {
				return id;
		}

		public void setId(Integer id) {
				this.id = id;
		}

		public Integer getQuantidade() {
				return quantidade;
		}

		public void setQuantidade(Integer quantidade) {
				this.quantidade = quantidade;
		}

		public Integer getStatus() {
				return status;
		}

		public void setStatus(Integer status) {
				this.status = status;
		}

		public ProducaoDia getProducaoDia() {
				return producaoDia;
		}

		public void setProducaoDia(ProducaoDia producaoDia) {
				this.producaoDia = producaoDia;
		}

		public Estoque getEstoque() {
				return estoque;
		}

		public void setEstoque(Estoque estoque) {
				this.estoque = estoque;
		}
}
