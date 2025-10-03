package projt4.praja.entity.dto.request.estoque;


import java.util.Date;

public class EstoqueDTORequest {
    private Date entrada;
    private Date validade;
    private Integer qtd;
    private Integer ingrediente;

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
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
}
