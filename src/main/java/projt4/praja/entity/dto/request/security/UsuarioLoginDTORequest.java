package projt4.praja.entity.dto.request.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Senhas com no minimo 6 caracteres e no maximo 12
 * @param telefone
 * @param senha
 */
public record  UsuarioLoginDTORequest (
		@NotBlank
   String telefone,
	 @Size(min=6, max=12)
   String senha
){

}