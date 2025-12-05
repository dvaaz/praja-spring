package projt4.praja.entity.dto.response.shared;

public class AlterarStatusDTOResponse {
    private Integer id;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

		public boolean isEmpty(){
			return this.id == null && this.status == null;
		}
}
