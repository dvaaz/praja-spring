package projt4.praja.service;

import org.springframework.dao.DataIntegrityViolationException;
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
import java.util.stream.Collectors;

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
		 * @param dtoRequest
		 * @return ingredienteDTOResponse
		 */
		@Transactional
		public projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse criarIngrediente(IngredienteDTORequest dtoRequest) {
				// Verifica a existencia do um grupo
				Grupo grupo = grupoRepository.buscarPorIdETipo(dtoRequest.getGrupo(), grupoIngrediente)
						.orElseGet(() -> this.grupoService.buscarOuCriarGrupoIngrediente());

				// Mapeia os dados obtidos para  a criação de ingrediente
				Ingrediente novoIngrediente = new Ingrediente();
				novoIngrediente.setNome(dtoRequest.getNome());
				novoIngrediente.setDescricao(dtoRequest.getDescricao());
				novoIngrediente.setGrupo(grupo);
				novoIngrediente.setStatus(ativo);
				// Salva no banco de dados (persistence)
				try {
				Ingrediente ingredienteSave = ingredienteRepository.save(novoIngrediente);

				projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse dtoResponse= new projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse();
				dtoResponse.setId(ingredienteSave.getId());
				dtoResponse.setNome(ingredienteSave.getNome());
				dtoResponse.setDescricao(ingredienteSave.getDescricao());
				dtoResponse.setIdGrupo(ingredienteSave.getGrupo().getId());
				dtoResponse.setNomeGrupo(ingredienteSave.getGrupo().getNome());
				dtoResponse.setStatus(ingredienteSave.getStatus());

				return dtoResponse;
				} catch (DataIntegrityViolationException ex) {
						throw new RuntimeException("Erro ao gravar Ingrediente", ex);
				}
		}

		/**
		 * Lista Ingredientes
		 * @return List
		 */
		public List<projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse> listarIngredientes(){
			List<Ingrediente> ingredientes = ingredienteRepository.listarIngredientes();

			if(ingredientes.isEmpty()){
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
		 * @param grupoId
		 * @return
		 */
		public ListaIngredienteDeGrupoDTO listarFichasDeGrupo(Integer grupoId){
				Grupo grupo = this.grupoRepository.buscarPorId(grupoId)
						.orElseThrow(() -> new GrupoException("Grupo com o ID: "+grupoId +" não encontrado"));
				List<Ingrediente> ingredientes = this.ingredienteRepository.listarIngredientesPorGrupo(grupo.getId());
				if (ingredientes.isEmpty()){
						throw new IngredienteException("Não há fichas no grupo : ( "+grupo.getId()+" ) -> "+grupo.getNome());
				}
				// transforma as fichas em dto especifico
				List<projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse> ingredientesDTO = new ArrayList<>();
				for (Ingrediente ingrediente: ingredientes){
						projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse temp = new projt4.praja.entity.dto.response.ingrediente.IngredienteDTOResponse();
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
		 * @param ingredienteId
		 * @return
		 */
		public IngredienteDTOResponse buscarPorId(Integer ingredienteId){
				Ingrediente ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId)
						.orElseThrow(() -> new IngredienteException("Ingrediente com o ID: " + ingredienteId + " não encontrada"));

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
		 * @param ingredienteId
		 * @param dtoRequest
		 * @return
		 */

		@Transactional
		public AlterarStatusDTOResponse alterarStatus(Integer ingredienteId, AlterarStatusDTORequest dtoRequest){
				Ingrediente ingrediente = this.ingredienteRepository.buscarPorId(ingredienteId)
						.orElseThrow(() -> new IngredienteException("Ingrediente com o ID: " + ingredienteId + " não encontrada"));
				ingrediente.setStatus(dtoRequest.getStatus());

				try{
						ingredienteRepository.save(ingrediente);
						AlterarStatusDTOResponse dtoResponse = new AlterarStatusDTOResponse();
						dtoResponse.setId(ingrediente.getId());
						dtoResponse.setStatus(ingrediente.getStatus());
						return dtoResponse;
				} catch (DataIntegrityViolationException ex){
						throw new  RuntimeException("Erro ao salvar alteracões.", ex);
				}
		}

		/**
		 * Altera Grupo de Ingrediente
		 * @param ingredienteId
		 * @param dtoRequest
		 * @return
		 */
		@jakarta.transaction.Transactional
		public MudarDeGrupoDTOResponse alterarGrupo(Integer ingredienteId, MudarDeGrupoDTORequest dtoRequest){
				Integer grupoId = dtoRequest.getGrupo();
				Grupo grupo = this.grupoRepository.buscarPorId(grupoId) // busca pelo grupo
						.orElseThrow(() -> new GrupoException("Grupo com o ID: "+grupoId +" não encontrado"));

				Ingrediente ingrediente
						= this.ingredienteRepository.buscarPorId(ingredienteId)
						.orElseThrow(() -> new IngredienteException("Ingrediente com o ID: " + ingredienteId + " não encontrada"));

				ingrediente.setGrupo(grupo);

				try{
						Ingrediente salvo = this.ingredienteRepository.save(ingrediente);

						MudarDeGrupoDTOResponse dtoResponse = new MudarDeGrupoDTOResponse();
						dtoResponse.setId(salvo.getId());
						dtoResponse.setIdGrupo(salvo.getGrupo().getId());
						dtoResponse.setNomeGrupo(salvo.getGrupo().getNome());

						return dtoResponse;
				} catch (DataIntegrityViolationException ex){
						throw new RuntimeException("Erro ao salvar alterações", ex);
				}
		}

		/**
		 * Alterar várias fichas tecnicas de um grupo para outro
		 * @param grupoId
		 * @param dtoRequest
		 * @return
		 */
		@jakarta.transaction.Transactional
		public MudarDeGrupoEmLoteDTOResponse mudarDeGrupoEmLote(Integer grupoId, MudarDeGrupoDTORequest dtoRequest){
				Grupo grupo = this.grupoRepository.buscarPorId(grupoId)
						.orElseThrow(() -> new GrupoException("Grupo com o id: "+grupoId+" não encontrado"));
				List<Ingrediente> fichas = this.ingredienteRepository.listarIngredientesPorGrupo(grupoId);

				if (fichas.isEmpty()){
						throw new IngredienteException("Não há fichas tecnicas no grupo: ( "+grupoId+" )-> "+grupo.getNome());
				}
				try {
						for (Ingrediente ingrediente : fichas) {
								ingrediente.setGrupo(grupo);
								Ingrediente salvo = ingredienteRepository.save(ingrediente);
						}

						MudarDeGrupoEmLoteDTOResponse dtoResponse = new MudarDeGrupoEmLoteDTOResponse();
						dtoResponse.setIdGrupo(grupo.getId());
						dtoResponse.setNomeDosItens(
								fichas.stream()
										.map(Ingrediente::getNome)
										.collect(Collectors.toList()));
						return dtoResponse;

				} catch (DataIntegrityViolationException ex){
						throw new  RuntimeException("Erro ao salvar alteracões.", ex);
				}
		}
}
