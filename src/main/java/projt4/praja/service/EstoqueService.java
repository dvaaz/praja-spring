package projt4.praja.service;

import java.time.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projt4.praja.entity.dto.response.estoque.EstoqueQtdDTOResponse;
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
		private final int disponivel = 1,
						indisponivel = 0,
						apagado = -1;

		/**
		 * Função para adicionar um novo item ao estoque. Nessa função a data é gerada NO backend com o fuso horário de São Paulo
		 * @param dtoRequest
		 * @return
		 */
		public EstoqueDTOResponse criar(EstoqueDTORequest dtoRequest) {
				Integer ingredienteId = dtoRequest.ingrediente();

				Ingrediente ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId)
								.orElseThrow(() -> new RuntimeException("Ingrediente não encontrado"));

				// obtem data local, fuso horario de sao paulo
				ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
				ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
				LocalDate agora = zonedDateTime.toLocalDate();

				Estoque estoque = new Estoque();
				estoque.setEntrada(agora);
				estoque.setValidade(dtoRequest.validade());
				estoque.setQuantidade(dtoRequest.quantidade());
				estoque.setIngrediente(ingrediente);
				estoque.setStatus(disponivel);

				Estoque estoqueSalvo = this.estoqueRepository.save(estoque);

				EstoqueDTOResponse dtoResponse = new EstoqueDTOResponse();
				dtoResponse.setId(estoqueSalvo.getId());
				dtoResponse.setEntrada(estoqueSalvo.getEntrada());
				dtoResponse.setValidade(estoqueSalvo.getValidade());
				dtoResponse.setQtd(estoqueSalvo.getQuantidade());
				dtoResponse.setIngrediente(estoqueSalvo.getIngrediente().getId());

				return dtoResponse;
		}

		/**
		 * Função para adicionar um novo item ao estoque. Nessa função a data é de acordo com a entrada do Json
		 * @param dtoRequest
		 * @return
		 */
		public EstoqueDTOResponse criarManualment(EstoqueDTORequest dtoRequest) {
				Integer ingredienteId = dtoRequest.ingrediente();

				Ingrediente ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId)
						.orElseThrow(() -> new RuntimeException("Ingrediente não encontrado"));

				// obtem data local, fuso horario de sao paulo

				Estoque estoque = new Estoque();
				estoque.setEntrada(dtoRequest.entrada());
				estoque.setValidade(dtoRequest.validade());
				estoque.setQuantidade(dtoRequest.quantidade());
				estoque.setIngrediente(ingrediente);
				estoque.setStatus(disponivel);

				Estoque estoqueSalvo = this.estoqueRepository.save(estoque);

				EstoqueDTOResponse dtoResponse = new EstoqueDTOResponse();
				dtoResponse.setId(estoqueSalvo.getId());
				dtoResponse.setEntrada(estoqueSalvo.getEntrada());
				dtoResponse.setValidade(estoqueSalvo.getValidade());
				dtoResponse.setQtd(estoqueSalvo.getQuantidade());
				dtoResponse.setIngrediente(estoqueSalvo.getIngrediente().getId());

				return dtoResponse;
		}

		/**
		 * Alterar a quantidade disponível em um estoque
		 * @param estoqueId
		 * @param dtoRequest
		 * @return
		 */
		public EstoqueQtdDTOResponse alterarQtd(Integer estoqueId, EstoqueDTORequest dtoRequest) {
				Estoque estoque = estoqueRepository.buscarPorId(estoqueId)
						.orElseThrow(() -> new RuntimeException("Estoque de Id: " +  estoqueId+" não encontrado"));
				estoque.setQuantidade(dtoRequest.quantidade());

				Estoque estoqueSalvo = this.estoqueRepository.save(estoque);

				EstoqueQtdDTOResponse dtoResponse = new EstoqueQtdDTOResponse();
				dtoResponse.setId(estoqueSalvo.getId());
				dtoResponse.setQtd(estoqueSalvo.getQuantidade());

				return dtoResponse;
		}

		/**
		 * Apagar logicamente
		 * @param estoqueId
		 */
		public void setApagado(int estoqueId) {
				Estoque estoque = this.estoqueRepository.buscarPorId(estoqueId)
						.orElseThrow(() -> new RuntimeException("Estoque de Id: "+ estoqueId +" não encontrado"));
				
				if(estoque.getQuantidade() <= 0) {
							estoqueRepository.updateStatus(estoque.getId(), apagado);
					}
				}


		/**
		 * Torna indisponivel o estoque que a quantidade tenha chegado a zero (0)
		 * @param estoqueId
		 */
		public void setIndisponivel(int estoqueId){
				Estoque estoque = this.estoqueRepository.buscarPorId(estoqueId)
						.orElseThrow(() -> new RuntimeException("Estoque de Id: "+ estoqueId +" não encontrado"));

				if(estoque.getQuantidade() == 0) {
						estoqueRepository.updateStatus(estoque.getId(), indisponivel);
				}
		}
}
