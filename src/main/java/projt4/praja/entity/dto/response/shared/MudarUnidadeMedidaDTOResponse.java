package projt4.praja.entity.dto.response.shared;

public class MudarUnidadeMedidaDTOResponse {
  private Integer id;
	private Integer qtd;
  private Integer unidadeMedida;

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

  public Integer getUnidadeMedida() {
    return unidadeMedida;
  }

  public void setUnidadeMedida(Integer unidadeMedida) {
    this.unidadeMedida = unidadeMedida;
  }
}
