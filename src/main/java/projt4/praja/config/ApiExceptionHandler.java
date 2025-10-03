package projt4.praja.config;

import io.swagger.v3.oas.annotations.Hidden;
import projt4.praja.exception.GrupoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe de configuracao para tratar a Exceptions personalizados
 * no RestController
 */
@RestControllerAdvice
@Hidden
public class ApiExceptionHandler {

	@ExceptionHandler(GrupoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleGrupoNaoEncontrado(GrupoException ex){
		return new ApiError("Grupo_nao_encontrado", ex.getMessage());
	}
	public record ApiError(String code, String message) {}

}
