package projt4.praja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "utilizado_dia")
public class UtilizadoDia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "utilizado_dia_id")
	private Integer id;
	@Column(name = "utilizado_dia_qtd", nullable=false)
	private Integer qtd;
	@Column(name="utilizado_dia_destino", nullable=false)
	private Integer destino;
	@Column(name="utilizado_dia_data", nullable=false)
	private Date data;
	@Column(name="utilizado_dia_status", nullable=false)
	private Integer status;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ingrediente_id", nullable = false)
	private Ingrediente ingrediente;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="producao_dia_id", nullable = false)
	private ProducaoDia producaoDia;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="producao_id", nullable = false)
	private Producao producao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public Integer getDestino() {
		return destino;
	}

	public void setDestino(Integer destino) {
		this.destino = destino;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	public ProducaoDia getProducaoDia() {
		return producaoDia;
	}

	public void setProducaoDia(ProducaoDia producaoDia) {
		this.producaoDia = producaoDia;
	}

	public Producao getProducao() {
		return producao;
	}

	public void setProducao(Producao producao) {
		this.producao = producao;
	}
}
