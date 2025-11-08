package projt4.praja.service;

import java.time.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projt4.praja.repository.EstoqueRepository;
import projt4.praja.repository.IngredienteRepository;
import projt4.praja.entity.Estoque;
import projt4.praja.entity.Ingrediente;
import projt4.praja.entity.dto.request.estoque.EstoqueDTORequest;
import projt4.praja.entity.dto.response.estoque.EstoqueDTOResponse;


@Service
public class EstoqueService {
		private final EstoqueRepository estoqueRepository;
		private final IngredienteRepository ingredienteRepository;

		@Autowired
		public EstoqueService(EstoqueRepository estoqueRepository, IngredienteRepository ingredienteRepository) {
				this.estoqueRepository = estoqueRepository;
				this.ingredienteRepository = ingredienteRepository;
		}

		// Variaveis para melhor leitura de código
		private final int ativo = 1,
						inativo = 0;

		public EstoqueDTOResponse criar(EstoqueDTORequest dtoRequest) {
				Integer ingredienteId = dtoRequest.ingrediente();

				Ingrediente ingrediente = this.ingredienteRepository.findById(ingredienteId)
								.orElseThrow(() -> new RuntimeException("Ingrediente não encontrado"));

				// obtem data local, fuso horario de sao paulo

				Estoque estoque = new Estoque();
				estoque.setEntrada(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
				estoque.setValidade(dtoRequest.validade());
				estoque.setQtd(dtoRequest.qtd());
				estoque.setIngrediente(ingrediente);
				estoque.setStatus(ativo);

				Estoque estoqueSalvo = this.estoqueRepository.save(estoque);

				EstoqueDTOResponse dtoResponse = new EstoqueDTOResponse();
				dtoResponse.setId(estoqueSalvo.getId());
				dtoResponse.setEntrada(estoqueSalvo.getEntrada());
				dtoResponse.setValidade(estoqueSalvo.getValidade());
				dtoResponse.setQtd(estoqueSalvo.getQtd());
				dtoResponse.setIngrediente(estoqueSalvo.getIngrediente().getId());

				return dtoResponse;
		}
}
