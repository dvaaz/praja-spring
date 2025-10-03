package projt4.praja.entity.dto.request.usuario;

import projt4.praja.Enum.RoleName;

public record CriarUsuarioDTO(
    String login,
    String senha,
    RoleName role
) {
}