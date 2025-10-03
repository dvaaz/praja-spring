package projt4.praja.entity.dto.request.shared;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class AlterarStatusDTORequest {
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
