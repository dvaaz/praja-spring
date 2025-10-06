package projt4.praja.config;


import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projt4.praja.exception.FichaTecnicaException;
import projt4.praja.exception.GrupoException;
import projt4.praja.exception.IngredienteException;

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
		@ExceptionHandler
		@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError handleAnyException(Exception ex){
//			System.err.println(ex.getMessage());
    return new ApiError("erro_interno", ex.getMessage());
	}

		@ExceptionHandler(org.hibernate.PropertyValueException.class)
		@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 para erros de validação/dados
		public ApiError handlePropertyValueException(org.hibernate.PropertyValueException ex){
				// A mensagem do Hibernate é feia, crie uma melhor para o usuário
				String campo = ex.getPropertyName();
				return new ApiError("campo_obrigatorio_nulo", "O campo '" + campo + "' é obrigatório e não foi fornecido ou está nulo.");
		}
}

