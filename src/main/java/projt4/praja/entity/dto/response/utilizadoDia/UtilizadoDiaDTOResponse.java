package projt4.praja.entity.dto.response.utilizadoDia;

import projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse;
import java.util.Date;

public class UtilizadoDiaDTOResponse {
  private Integer id;
  private Integer qtd;
  private Integer destino;
	private Date data;
	private IngredienteDTOResponse ingrediente;
	private Integer idProducao;
	private String idProducaoDia;
  private Integer status;

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

	public IngredienteDTOResponse getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(IngredienteDTOResponse ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Integer getIdProducao() {
		return idProducao;
	}

	public void setIdProducao(Integer idProducao) {
		this.idProducao = idProducao;
	}

	public String getIdProducaoDia() {
		return idProducaoDia;
	}

	public void setIdProducaoDia(String idProducaoDia) {
		this.idProducaoDia = idProducaoDia;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}