package projt4.praja.service;

import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
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
import projt4.praja.entity.dto.response.ingrediente.ListaIngredienteDeGrupoDTO;
import projt4.praja.entity.dto.response.shared.AlterarStatusDTOResponse;
import projt4.praja.entity.dto.response.shared.MudarDeGrupoDTOResponse;
import projt4.praja.entity.dto.response.shared.MudarDeGrupoEmLoteDTOResponse;
import projt4.praja.exception.IngredienteException;
import projt4.praja.exception.GrupoException;
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

		// Enums a serem utilizados
		private final Integer ativo = StatusEnum.ATIVO.getStatus(),
				inativo = StatusEnum.INATIVO.getStatus(),
				apagado = StatusEnum.APAGADO.getStatus();
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
				Grupo grupo;
				Integer grupoId = dtoRequest.getGrupo();

				if (dtoRequest.getGrupo() != null) { // Buscar por grupo
						grupo = grupoRepository.buscarPorIdETipo(grupoId, grupoIngrediente)
								.orElseThrow(() -> new GrupoException("Grupo com o ID: " + grupoId + " não encontrado."));
				} else { // caso se receba um valor nulo pelo dto
						grupo = this.grupoService.buscarOuCriarGrupoIngrediente();
				}

				if (grupo == null) { // Caso ainda assim não seja possivel obter um grupo
						throw new GrupoException("Não foi possível determinar ou criar o Grupo necessário para Ingrediente.");
				}

				// Mapeia os dados obtidos para  a criação de ingrediente
				Ingrediente novoIngrediente = new Ingrediente();
				novoIngrediente.setNome(dtoRequest.getNome());
				novoIngrediente.setDescricao(dtoRequest.getDescricao());
				novoIngrediente.setGrupo(grupo);
				novoIngrediente.setStatus(ativo);
				// Salva no banco de dados (persistence)
				try {
						Ingrediente ingredienteSave = ingredienteRepository.save(novoIngrediente);

						IngredienteDTOResponse dtoResponse = new IngredienteDTOResponse();
						dtoResponse.setId(ingredienteSave.getId());
						dtoResponse.setNome(ingredienteSave.getNome());
						dtoResponse.setDescricao(ingredienteSave.getDescricao());
						dtoResponse.setIdGrupo(ingredienteSave.getGrupo().getId());
						dtoResponse.setNomeGrupo(ingredienteSave.getGrupo().getNome());
						dtoResponse.setStatus(ingredienteSave.getStatus());

						return dtoResponse;
				} catch (DataIntegrityViolationException ex) {
						throw new RuntimeException("Erro ao gravar Ingrediente", ex);
				} catch (PropertyValueException ex) {
						throw new RuntimeException("Campo obrigatório não preenchido na Entidade.", ex);
				}
		}

		/**
		 * Lista Ingredientes
		 *
		 * @return List
		 */
		public List<IngredienteDTOResponse> listar() {
				List<Ingrediente> ingredientes = ingredienteRepository.listar();

				if (ingredientes.isEmpty()) {
						throw new IngredienteException("Não há nenum ingrediente a ser listado.");
				}
				List<projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse> dtoResponse = new ArrayList<>();
				for (Ingrediente ingrediente : ingredientes) {
						projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse temp = new projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse();
						temp.setId(ingrediente.getId());
						temp.setNome(ingrediente.getNome());
						temp.setDescricao(ingrediente.getDescricao());
						temp.setIdGrupo(ingrediente.getGrupo().getId());
						temp.setNomeGrupo(ingrediente.getGrupo().getNome());
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
				Grupo grupo = this.grupoRepository.buscarPorId(grupoId)
						.orElseThrow(() -> new GrupoException("Grupo com o ID: " + grupoId + " não encontrado"));
				List<Ingrediente> ingredientes = this.ingredienteRepository.listarPorGrupo(grupo.getId());
				if (ingredientes.isEmpty()) {
						throw new IngredienteException("Não há ingredientes no grupo : ( " + grupo.getId() + " ) -> " + grupo.getNome());
				}
				// transforma as ingredientes em dto especifico
				List<IngredienteDTOResponse> ingredientesDTO = new ArrayList<>();
				for (Ingrediente ingrediente : ingredientes) {
						IngredienteDTOResponse temp = new IngredienteDTOResponse();
						temp.setId(ingrediente.getId());
						temp.setNome(ingrediente.getNome());
						temp.setDescricao(ingrediente.getDescricao());
						ingredientesDTO.add(temp);
				}

				ListaIngredienteDeGrupoDTO dtoResponse = new ListaIngredienteDeGrupoDTO();
				dtoResponse.setIdGrupo(grupo.getId());
				dtoResponse.setNomeGrupo(grupo.getNome());
				dtoResponse.setCorGrupo(grupo.getCor());
				dtoResponse.setIngredientes(ingredientesDTO);
				return dtoResponse;
		}

		/**
		 * Buscar Ingrediene por Id
		 *
		 * @param ingredienteId
		 * @return
		 */
		public IngredienteDTOResponse buscarPorId(Integer ingredienteId) {
				Ingrediente ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId)
						.orElseThrow(() -> new IngredienteException("Ingrediente com o ID: " + ingredienteId + " não encontrado"));

				IngredienteDTOResponse dtoResponse = new IngredienteDTOResponse();
				dtoResponse.setId(ingrediente.getId());
				dtoResponse.setNome(ingrediente.getNome());
				dtoResponse.setDescricao(ingrediente.getDescricao());
				dtoResponse.setIdGrupo(ingrediente.getGrupo().getId());
				dtoResponse.setNomeGrupo(ingrediente.getGrupo().getNome());
				dtoResponse.setStatus(ingrediente.getStatus());
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
				Ingrediente ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId)
						.orElseThrow(() -> new IngredienteException("Ingrediente com o ID: " + ingredienteId + " não encontrado"));
				ingrediente.setStatus(dtoRequest.getStatus());

//				try{
				ingredienteRepository.save(ingrediente);
				AlterarStatusDTOResponse dtoResponse = new AlterarStatusDTOResponse();
				dtoResponse.setId(ingrediente.getId());
				dtoResponse.setStatus(ingrediente.getStatus());
				return dtoResponse;
//				} catch (DataIntegrityViolationException ex){
//						throw new  RuntimeException("Erro ao salvar alteracões.", ex);
//				}
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
				Integer grupoId = dtoRequest.getGrupo();
				Grupo grupo = this.grupoRepository.buscarPorId(grupoId) // busca pelo grupo
						.orElseThrow(() -> new GrupoException("Grupo com o ID: " + grupoId + " não encontrado"));

				Ingrediente ingrediente
						= this.ingredienteRepository.buscarPorId(ingredienteId)
						.orElseThrow(() -> new IngredienteException("Ingrediente com o ID: " + ingredienteId + " não encontrado"));

				ingrediente.setGrupo(grupo);

				try {
						Ingrediente salvo = this.ingredienteRepository.save(ingrediente);

						MudarDeGrupoDTOResponse dtoResponse = new MudarDeGrupoDTOResponse();
						dtoResponse.setId(salvo.getId());
						dtoResponse.setIdGrupo(salvo.getGrupo().getId());
						dtoResponse.setNomeGrupo(salvo.getGrupo().getNome());

						return dtoResponse;
				} catch (DataIntegrityViolationException ex) {
						throw new RuntimeException("Erro ao salvar alterações", ex);
				}
		}

		/**
		 * Alterar várias ingredientes tecnicas de um grupo para outro
		 *
		 * @param grupoId
		 * @param dtoRequest
		 * @return
		 */
		@jakarta.transaction.Transactional
		public MudarDeGrupoEmLoteDTOResponse mudarDeGrupoEmLote(Integer grupoId, MudarDeGrupoDTORequest dtoRequest) {
				Grupo grupo = this.grupoRepository.buscarPorId(grupoId)
						.orElseThrow(() -> new GrupoException("Grupo com o id: " + grupoId + " não encontrado"));
				List<Ingrediente> ingredientes = this.ingredienteRepository.listarPorGrupo(grupoId);

				if (ingredientes.isEmpty()) {
						throw new IngredienteException("Não há ingredientes tecnicas no grupo: ( " + grupoId + " )-> " + grupo.getNome());
				}
				try {
						for (Ingrediente ingrediente : ingredientes) {
								ingrediente.setGrupo(grupo);
								Ingrediente salvo = ingredienteRepository.save(ingrediente);
						}

						MudarDeGrupoEmLoteDTOResponse dtoResponse = new MudarDeGrupoEmLoteDTOResponse();
						dtoResponse.setIdGrupo(grupo.getId());
						dtoResponse.setNomeDosItens(
								ingredientes.stream()
										.map(Ingrediente::getNome)
										.collect(Collectors.toList()));
						return dtoResponse;

				} catch (DataIntegrityViolationException ex) {
						throw new RuntimeException("Erro ao salvar alteracões.", ex);
				}
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
				Optional<Ingrediente> ingredienteReturn = this.ingredienteRepository.buscarPorId(ingredienteId);
				ingredienteReturn.ifPresent(ingrediente -> {
						this.ingredienteRepository.updateStatus(ingrediente.getId(), inativo);
				});
				return ingredienteReturn.isPresent();
		}

		/**
		 * Apaga de forma lógica
		 *
		 * @param ingredienteId
		 * @return
		 */
		@jakarta.transaction.Transactional
		public Boolean apagar(Integer ingredienteId) {
				Optional<Ingrediente> ingredienteReturn = this.ingredienteRepository.buscarPorId(ingredienteId);
				ingredienteReturn.ifPresent(ingrediente -> {
						this.ingredienteRepository.updateStatus(ingrediente.getId(), apagado);
				});
				return ingredienteReturn.isPresent();
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