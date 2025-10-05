package projt4.praja.config;

import io.swagger.v3.oas.annotations.Hidden;
import projt4.praja.exception.GrupoException;
import projt4.praja.exception.FichaTecnicaException;
import projt4.praja.exception.IngredienteException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe de configuracao para tratar a Exceptions personalizados
 * no RestController
 */
@RestControllerAdvice
@Hidden // exclui os handlers da documentação
public class ApiExceptionHandler {

	@ExceptionHandler(GrupoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleGrupoNaoEncontrado(GrupoException ex){
		return new ApiError("Grupo_nao_encontrado", ex.getMessage());
	}

	@ExceptionHandler(FichaTecnicaException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleFichaTecnicaNaoEncontrada(FichaTecnicaException ex){
		return new ApiError("Ficha_Tecnica_nao_encontrada", ex.getMessage());
	}
	
	@ExceptionHandler(IngredienteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleIngredienteNaoEncontrado(IngredienteException ex){
		return new ApiError("Ingrediente_nao_encontrado", ex.getMessage());
	}
	
	public record ApiError(String code, String message) {}

	// capturar exceptions ainda não projetadas
	public ApiError handleAnyException(Exception ex){
    return new ApiError("erro_interno", "Ocorreu um erro inesperado.");
	}

}

