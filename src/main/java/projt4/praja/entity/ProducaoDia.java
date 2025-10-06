package projt4.praja.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "producao_dia")
public class ProducaoDia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producao_dia_id")
	private Integer id;
	@Column(name = "producao_dia_data", nullable=false)
	private Date data;
	@Column(name="producao_dia_status", nullable=false)
	private Integer status;
	@OneToMany(mappedBy = "producaoDia")
	private Set<Producao> producaoDia;

		public Integer getId() {
				return id;
		}

		public void setId(Integer id) {
				this.id = id;
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

		public Set<Producao> getProducaoDia() {
				return producaoDia;
		}

		public void setProducaoDia(Set<Producao> producaoDia) {
				this.producaoDia = producaoDia;
		}
}
