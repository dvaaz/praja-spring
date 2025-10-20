package projt4.praja.entity.dto.request.usuario;


import projt4.praja.Enum.RoleName;

import java.util.List;

public class UsuarioDTORequest {
    private String nome;
    private String telefone;
    private String senha;
    private List<RoleName> roleList;

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

		public String getSenha() {
				return senha;
		}

		public void setSenha(String senha) {
				this.senha = senha;
		}

		public List<RoleName> getRoleList() {
				return roleList;
		}

		public void setRoleList(List<RoleName> roleList) {
				this.roleList = roleList;
		}
}
