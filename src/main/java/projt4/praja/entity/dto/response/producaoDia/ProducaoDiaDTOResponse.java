package projt4.praja.entity.dto.response.producaoDia;

import java.time.LocalDate;


public class ProducaoDiaDTOResponse {
    private Integer id;
    private LocalDate data;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}