package projt4.praja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "estoque")
public class Estoque {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "estoque_id")
	private Integer id;
	@Column(name = "estoque_dia", nullable=false)
	private LocalDate entrada;
	@Column(name = "estoque_validade")
	private LocalDate validade;
	@Column(name = "estoque_qtd", nullable=false)
	private Integer quantidade;
	@Column(name="estoque_status", nullable=false)
	private Integer status;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ingrediente_id", nullable = false)
	private Ingrediente ingrediente;

	@OneToMany(mappedBy = "estoque")
	private Set<Producao> producaoSet;

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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer qtd) {
		this.quantidade = quantidade;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Set<Producao> getProducaoSet() {
		return producaoSet;
	}

	public void setProducaoSet(Set<Producao> producaoSet) {
		this.producaoSet = producaoSet;
	}
}
