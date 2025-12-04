package projt4.praja.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projt4.praja.Enum.GrupoEnum;
import projt4.praja.Enum.StatusEnum;
import projt4.praja.entity.Ingrediente;
import projt4.praja.entity.Grupo;
import projt4.praja.entity.dto.request.ingrediente.IngredienteDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarStatusDTORequest;
import projt4.praja.entity.dto.request.shared.MudarDeGrupoDTORequest;
import projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse;
import projt4.praja.entity.dto.response.ingrediente.IngredienteSemGrupoDTOResponse;
import projt4.praja.entity.dto.response.ingrediente.ListaIngredienteDeGrupoDTO;
import projt4.praja.entity.dto.response.shared.AlterarStatusDTOResponse;
import projt4.praja.entity.dto.response.shared.MudarDeGrupoDTOResponse;
import projt4.praja.entity.dto.response.shared.MudarDeGrupoEmLoteDTOResponse;
import projt4.praja.repository.GrupoRepository;
import projt4.praja.repository.IngredienteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredienteService {
		private final IngredienteRepository ingredienteRepository;
		private final GrupoRepository grupoRepository;
		private final GrupoService grupoService;

		public IngredienteService(IngredienteRepository ingredienteRepository,
		                          GrupoRepository grupoRepository, GrupoService grupoService) {
				this.ingredienteRepository = ingredienteRepository;
				this.grupoRepository = grupoRepository;
				this.grupoService = grupoService;
		}

		// Variaveis para melhor leitura de código
		private final int ativo = 1,
					inativo = 0,
					apagado = -1;
		private final int grupoIngrediente = GrupoEnum.ingrediente.getNumber();

		// métodos

		/**
		 * Criação de ingredientes. Caso o ingrediente não possua grupo ou o grupo não seja de ingredientes
		 *
		 * @param dtoRequest
		 * @return ingredienteDTOResponse
		 */
		@Transactional
		public IngredienteDTOResponse criar(IngredienteDTORequest dtoRequest) {
				Integer grupoId = dtoRequest.grupo();
				// Busca um grupo
				Grupo grupo = this.grupoRepository.buscarPorIdETipo(grupoId, grupoIngrediente)
						.orElseGet(() -> this.grupoService.buscarOuCriarGrupoIngrediente()); // caso não obtenha um grupo

				if (grupo == null) { return null ;} // Caso ainda assim não seja possível obter um grupo

				// Mapeia os dados obtidos para a criação de ingrediente
				Ingrediente novoIngrediente = new Ingrediente();
				novoIngrediente.setNome(dtoRequest.nome());
				novoIngrediente.setDescricao(dtoRequest.descricao());
				novoIngrediente.setGrupo(grupo);
        novoIngrediente.setUnidadeMedida(dtoRequest.unidadeMedida());
				novoIngrediente.setStatus(ativo);
				// Salva no banco de dados (persistence)

						Ingrediente ingredienteSave = ingredienteRepository.save(novoIngrediente);

						IngredienteDTOResponse dtoResponse = new IngredienteDTOResponse();
						dtoResponse.setId(ingredienteSave.getId());
						dtoResponse.setNome(ingredienteSave.getNome());
						dtoResponse.setDescricao(ingredienteSave.getDescricao());
            dtoResponse.setUnidadeMedida(ingredienteSave.getUnidadeMedida());
						dtoResponse.setIdGrupo(ingredienteSave.getGrupo().getId());
						dtoResponse.setNomeGrupo(ingredienteSave.getGrupo().getNome());
						dtoResponse.setStatus(ingredienteSave.getStatus());

						return dtoResponse;

				}


		/**
		 * Lista Ingredientes
		 *
		 * @return List
		 */
		public List<IngredienteDTOResponse> listar() {
				List<Ingrediente> ingredientes = ingredienteRepository.listar();

				if (ingredientes.isEmpty()) {
						return null;
				}
				List<projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse> dtoResponse = new ArrayList<>();
				for (Ingrediente ingrediente : ingredientes) {
						projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse temp = new projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse();
						temp.setId(ingrediente.getId());
						temp.setNome(ingrediente.getNome());
						temp.setDescricao(ingrediente.getDescricao());
						temp.setIdGrupo(ingrediente.getGrupo().getId());
						temp.setNomeGrupo(ingrediente.getGrupo().getNome());
            temp.setUnidadeMedida(ingrediente.getUnidadeMedida());
						temp.setStatus(ingrediente.getStatus());
						dtoResponse.add(temp);
				}
				return dtoResponse;
		}

		/**
		 * Lista ingredientes pertencentes a um grupo
		 *
		 * @param grupoId
		 * @return
		 */
		public ListaIngredienteDeGrupoDTO listarPorGrupo(Integer grupoId) {
				Optional<Grupo> grupo = this.grupoRepository.buscarPorId(grupoId);

				if (grupo.isPresent()) {
						List<Ingrediente> ingredientes = this.ingredienteRepository.listarPorGrupo(grupo.get().getId());
						if (!ingredientes.isEmpty()) {
								// transforma as ingredientes em dto especifico
								List<IngredienteSemGrupoDTOResponse> ingredientesDTO = new ArrayList<>();
								for (Ingrediente ingrediente : ingredientes) {
										IngredienteSemGrupoDTOResponse temp = new IngredienteSemGrupoDTOResponse();
										temp.setId(ingrediente.getId());
										temp.setNome(ingrediente.getNome());
                    temp.setUnidadeMedida(ingrediente.getUnidadeMedida());
										temp.setDescricao(ingrediente.getDescricao());
										ingredientesDTO.add(temp);
								}

								ListaIngredienteDeGrupoDTO dtoResponse = new ListaIngredienteDeGrupoDTO();
								dtoResponse.setIdGrupo(grupo.get().getId());
								dtoResponse.setNomeGrupo(grupo.get().getNome());
								dtoResponse.setCorGrupo(grupo.get().getCor());
								dtoResponse.setIngredientes(ingredientesDTO);
								return dtoResponse;
						}
				} return null;
		}

		/**
		 * Buscar Ingrediente por Id
		 *
		 * @param ingredienteId
		 * @return
		 */
		public IngredienteDTOResponse buscarPorId(Integer ingredienteId) {
				Optional<Ingrediente> ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId);

				if (ingrediente.isEmpty()) { return null; } // caso não haja ingrediente

				IngredienteDTOResponse dtoResponse = new IngredienteDTOResponse();
				dtoResponse.setId(ingrediente.get().getId());
				dtoResponse.setNome(ingrediente.get().getNome());
				dtoResponse.setDescricao(ingrediente.get().getDescricao());
        dtoResponse.setUnidadeMedida(ingrediente.get().getUnidadeMedida());
				dtoResponse.setIdGrupo(ingrediente.get().getGrupo().getId());
				dtoResponse.setNomeGrupo(ingrediente.get().getGrupo().getNome());
				dtoResponse.setStatus(ingrediente.get().getStatus());
				return dtoResponse;
		}

		/**
		 * Altera Status de IIngredientes
		 *
		 * @param ingredienteId
		 * @param dtoRequest
		 * @return
		 */

		@Transactional
		public AlterarStatusDTOResponse alterarStatus(Integer ingredienteId, AlterarStatusDTORequest dtoRequest) {
				Optional<Ingrediente> ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId);

				if (ingrediente.isEmpty()) { return null; } // caso não haja ingrediente com o ‘id’

						ingrediente.get().setStatus(dtoRequest.status());
						Ingrediente save = this.ingredienteRepository.save(ingrediente.get());

						AlterarStatusDTOResponse dtoResponse = new AlterarStatusDTOResponse();
						dtoResponse.setId(save.getId());
						dtoResponse.setStatus(save.getStatus());
						return dtoResponse;
		}

		/**
		 * Altera Grupo de Ingrediente
		 *
		 * @param ingredienteId
		 * @param dtoRequest
		 * @return
		 */
		@Transactional
		public MudarDeGrupoDTOResponse alterarGrupo(Integer ingredienteId, MudarDeGrupoDTORequest dtoRequest) {
				Integer grupoId = dtoRequest.grupo();
				Optional<Grupo> grupo = this.grupoRepository.buscarPorId(grupoId); // busca pelo grupo

				Optional<Ingrediente> ingrediente	= this.ingredienteRepository.buscarPorId(ingredienteId);

				if (ingrediente.isEmpty() || grupo.isEmpty()) { return null; }

				ingrediente.get().setGrupo(grupo.get());
				Ingrediente salvo = this.ingredienteRepository.save(ingrediente.get());

				MudarDeGrupoDTOResponse dtoResponse = new MudarDeGrupoDTOResponse();
				dtoResponse.setId(salvo.getId());
				dtoResponse.setIdGrupo(salvo.getGrupo().getId());
				dtoResponse.setNomeGrupo(salvo.getGrupo().getNome());

				return dtoResponse;

		}

		/**
		 * Alterar várias ingredientes tecnicas de um grupo(A) para outro grupo(B)
		 *
		 * @param grupoId
		 * @param dtoRequest
		 * @return
		 */
		@jakarta.transaction.Transactional
		public MudarDeGrupoEmLoteDTOResponse mudarDeGrupoEmLote(Integer grupoId, MudarDeGrupoDTORequest dtoRequest) {
				Optional<Grupo> grupoA = this.grupoRepository.buscarPorId(grupoId);

				Optional<Grupo> grupoB = this.grupoRepository.buscarPorId(dtoRequest.grupo());

				List<Ingrediente> ingredientes = this.ingredienteRepository.listarPorGrupo(grupoId);

					if (!ingredientes.isEmpty() && grupoA.isPresent() && grupoB.isPresent()) {
							for (Ingrediente ingrediente : ingredientes) {
									ingrediente.setGrupo(grupoB.get());
									Ingrediente salvo = ingredienteRepository.save(ingrediente);
							}

							MudarDeGrupoEmLoteDTOResponse dtoResponse = new MudarDeGrupoEmLoteDTOResponse();
							dtoResponse.setIdGrupo(grupoA.get().getId());
							dtoResponse.setNomeDosItens(
									ingredientes.stream()
											.map(Ingrediente::getNome)
											.collect(Collectors.toList())); // captura o nome dos itens presentes na lista
							return dtoResponse;
					} return null;
		}

		/**
		 * Inativa o ingrediente
		 * ou ter sido descontinuado
		 *
		 * @param ingredienteId
		 * @return
		 */
		@Transactional
		public Boolean desativar(Integer ingredienteId) {
				Optional<Ingrediente> ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId);
				ingrediente.ifPresent(desativado -> {
						this.ingredienteRepository.updateStatus(ingrediente.get().getId(), inativo);
				});
				return ingrediente.isPresent();
		}

		/**
		 * Apaga de forma lógica
		 *
		 * @param ingredienteId
		 * @return
		 */
		@jakarta.transaction.Transactional
		public Boolean apagar(Integer ingredienteId) {
				Optional<Ingrediente> ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId);
				ingrediente.ifPresent( apaga -> {
						this.ingredienteRepository.updateStatus(ingrediente.get().getId(), apagado);
				});
				return ingrediente.isPresent();
		}

		/**
		 * Destroi objeto que tenha sido apagado
		 *
		 * @param ingredienteId
		 * @return
		 */
		@Transactional
		public Boolean destroy(Integer ingredienteId) {
				Optional<Ingrediente> buscaPorIngrediente = this.ingredienteRepository.findById(ingredienteId);

				if (buscaPorIngrediente.isPresent()) {
						Integer status = buscaPorIngrediente.get().getStatus();
						if (status.equals(apagado)) {
								Ingrediente ingrediente = buscaPorIngrediente.get();
								ingredienteRepository.delete(ingrediente);
								return true;
						}
				}
				return false;
		}
}