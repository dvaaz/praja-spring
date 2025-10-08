package projt4.praja.entity.dto.response.usuario;

import java.util.List;

public class UsuarioDTOResponse {
    private Integer id;
    private String nome;
    private String telefone;
    private Integer status;
		private List<String> roleList;

		public Integer getId() {
				return id;
		}

		public void setId(Integer id) {
				this.id = id;
		}

		public String getNome() {
				return nome;
		}

		public void setNome(String nome) {
				this.nome = nome;
		}

		public String getTelefone() {
				return telefone;
		}

		public void setTelefone(String telefone) {
				this.telefone = telefone;
		}

		public List<String> getRoleList() {
				return roleList;
		}

		public void setRoleList(List<String> roleList) {
				this.roleList = roleList;
		}

		public Integer getStatus() {
				return status;
		}

		public void setStatus(Integer status) {
				this.status = status;
		}
}
