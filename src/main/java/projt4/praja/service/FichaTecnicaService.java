package projt4.praja.service;


import projt4.praja.Enum.GrupoEnum;
import projt4.praja.Enum.StatusEnum;
import projt4.praja.entity.FichaTecnica;
import projt4.praja.entity.Grupo;
import projt4.praja.entity.dto.request.fichaTecnica.FichaTecnicaDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarStatusDTORequest;
import projt4.praja.entity.dto.request.shared.MudarDeGrupoDTORequest;
import projt4.praja.entity.dto.response.fichaTecnica.FichaTecnicaDTOResponse;
import projt4.praja.entity.dto.response.fichaTecnica.ListaFichasDeGrupoDTO;
import projt4.praja.entity.dto.response.shared.AlterarStatusDTOResponse;
import projt4.praja.entity.dto.response.shared.MudarDeGrupoDTOResponse;
import projt4.praja.entity.dto.response.shared.MudarDeGrupoEmLoteDTOResponse;
import projt4.praja.repository.FichaTecnicaRepository;
import projt4.praja.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FichaTecnicaService {
    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final GrupoRepository grupoRepository;
    private final GrupoService grupoService;

    public FichaTecnicaService(FichaTecnicaRepository fichaTecnicaRepository,
                               GrupoRepository grupoRepository, GrupoService grupoService) {
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.grupoRepository = grupoRepository;
        this.grupoService = grupoService;
    }

    // Enuns a serem utilizados na classe
    private final Integer ativo = StatusEnum.ATIVO.getStatus(),
            inativo = StatusEnum.INATIVO.getStatus(),
            apagado = StatusEnum.APAGADO.getStatus();
		private final int grupoFicha = GrupoEnum.fichaTecnica.getNumber();

    // Métodos

    /**
     * Cria Ficha Tecnica, quando criada a ficha tecnica NÃO será
     * considerada ativa, essa ativação se dará manualmente por endpoint proprio
     *
     * @param dtoRequest
     * @return
     */
    @Transactional
    public FichaTecnicaDTOResponse criar(FichaTecnicaDTORequest dtoRequest) {
				Integer grupoId = dtoRequest.getGrupo();
			// Busca pelo grupo
		    Grupo grupo = this.grupoRepository.buscarPorIdETipo(grupoId, grupoFicha)
				    .orElseGet(() -> this.grupoService.buscarOuCriarGrupoFichaTecnica()); // caso não obtenha um grupo

		    if (grupo == null) { return null; } // Força a saida caso não seja possivel obter um grupo

				FichaTecnica fichaTecnica = new FichaTecnica();
        fichaTecnica.setNome(dtoRequest.getNome());
        fichaTecnica.setDescricao(dtoRequest.getDescricao());
        fichaTecnica.setGrupo(grupo);
        fichaTecnica.setStatus(inativo);

        FichaTecnica salvo = this.fichaTecnicaRepository.save(fichaTecnica);

        FichaTecnicaDTOResponse dtoResponse = new FichaTecnicaDTOResponse();
        dtoResponse.setId(salvo.getId());
        dtoResponse.setNome(salvo.getNome());
        dtoResponse.setDescricao(salvo.getDescricao());
        dtoResponse.setIdGrupo(salvo.getGrupo().getId());
        dtoResponse.setNomeGrupo(salvo.getGrupo().getNome());
        dtoResponse.setStatus(salvo.getStatus());

        return dtoResponse;

    }

  /**
   * Lista Fichas Tecnicas
   * @return List<FichaTecnicaDTOResponse>
   */
  public List<FichaTecnicaDTOResponse> listar(){
    List<FichaTecnica> fichasTecnicas = this.fichaTecnicaRepository.listar();

    if (fichasTecnicas.isEmpty()){ return null; }

    List<FichaTecnicaDTOResponse> dtoResponse = new ArrayList<>();
    for (FichaTecnica ficha: fichasTecnicas){
      FichaTecnicaDTOResponse temp = new FichaTecnicaDTOResponse();
      temp.setId(ficha.getId());
      temp.setNome(ficha.getNome());
      temp.setDescricao(ficha.getDescricao());
      temp.setIdGrupo(ficha.getGrupo().getId());
      temp.setNomeGrupo(ficha.getGrupo().getNome());
      temp.setStatus(ficha.getStatus());

      dtoResponse.add(temp);
    }
    return dtoResponse;
  }

		/**
		 * Lista Fichas Tecnicas que estejam ativas
		 * @return List<FichaTecnicaDTOResponse>
		 */
		public List<FichaTecnicaDTOResponse> listarDia(){
				List<FichaTecnica> fichasTecnicas = this.fichaTecnicaRepository.listarAtivas();

				if (fichasTecnicas.isEmpty()){ return null; }

				List<FichaTecnicaDTOResponse> dtoResponse = new ArrayList<>();
				for (FichaTecnica ficha: fichasTecnicas){
						FichaTecnicaDTOResponse temp = new FichaTecnicaDTOResponse();
						temp.setId(ficha.getId());
						temp.setNome(ficha.getNome());
						temp.setDescricao(ficha.getDescricao());
						temp.setIdGrupo(ficha.getGrupo().getId());
						temp.setNomeGrupo(ficha.getGrupo().getNome());
						temp.setStatus(ficha.getStatus());

						dtoResponse.add(temp);
				}
				return dtoResponse;
		}


    /**
     * Lista fichas tecnicas pertencentes a um grupo
     * @param grupoId
     * @return
     */
    public ListaFichasDeGrupoDTO listarPorGrupo(Integer grupoId){
        Optional<Grupo> grupo = this.grupoRepository.buscarPorId(grupoId);

				if(grupo.isPresent()) {
						List<FichaTecnica> fichas = this.fichaTecnicaRepository.listarPorGrupo(grupo.get().getId());
						if (!fichas.isEmpty()) {
								// transforma as fichas em dto especifico
								List<FichaTecnicaDTOResponse> fichasDto = new ArrayList<>();
								for (FichaTecnica ficha : fichas) {
										FichaTecnicaDTOResponse temp = new FichaTecnicaDTOResponse();
										temp.setId(ficha.getId());
										temp.setNome(ficha.getNome());
										temp.setDescricao(ficha.getDescricao());
										fichasDto.add(temp);
								}

								ListaFichasDeGrupoDTO dtoResponse = new ListaFichasDeGrupoDTO();
								dtoResponse.setIdGrupo(grupo.get().getId());
								dtoResponse.setNomeGrupo(grupo.get().getNome());
								dtoResponse.setCorGrupo(grupo.get().getCor());
								dtoResponse.setFichasTecnicas(fichasDto);
								return dtoResponse;
						}
				} return null;
    }

    /**
     * Buscar Ficha Tecnica por Id
     * @param fichaId
     * @return
     */
  public FichaTecnicaDTOResponse buscarPorId(Integer fichaId){
      Optional<FichaTecnica> fichaTecnica = this.fichaTecnicaRepository.buscarPorId(fichaId);

			if (fichaTecnica.isEmpty()){ return null;}

      FichaTecnicaDTOResponse dtoResponse = new FichaTecnicaDTOResponse();
      dtoResponse.setId(fichaTecnica.get().getId());
      dtoResponse.setNome(fichaTecnica.get().getNome());
      dtoResponse.setDescricao(fichaTecnica.get().getDescricao());
      dtoResponse.setIdGrupo(fichaTecnica.get().getGrupo().getId());
      dtoResponse.setNomeGrupo(fichaTecnica.get().getGrupo().getNome());
      dtoResponse.setStatus(fichaTecnica.get().getStatus());
      return dtoResponse;
  }

    /**
     * Altera Status de Ficha Tecnica
     * @param fichaId
     * @param dtoRequest
     * @return
     */
  @Transactional
  public AlterarStatusDTOResponse alterarStatus(Integer fichaId, AlterarStatusDTORequest dtoRequest){
      Optional<FichaTecnica> ficha = this.fichaTecnicaRepository.buscarPorId(fichaId); // busca por ficha tecnica

		  if(ficha.isPresent()) {

          ficha.get().setStatus(dtoRequest.getStatus());

          FichaTecnica salvo = this.fichaTecnicaRepository.save(ficha.get());
          AlterarStatusDTOResponse dtoResponse = new AlterarStatusDTOResponse();
          dtoResponse.setId(ficha.get().getId());
          dtoResponse.setStatus(ficha.get().getStatus());
          return dtoResponse;
      } return null;
  }

    /**
     * Altera Ficha Tecnica de Grupo
     * @param fichaId
     * @param dtoRequest
     * @return
     */
    @Transactional
    public MudarDeGrupoDTOResponse alterarGrupo(Integer fichaId, MudarDeGrupoDTORequest dtoRequest){
        Integer grupoId = dtoRequest.getGrupo();

				Optional<Grupo> grupo = this.grupoRepository.buscarPorId(grupoId); // busca pelo grupo

		    if(grupo.isPresent()) {
				    Optional<FichaTecnica> ficha = this.fichaTecnicaRepository.buscarPorId(fichaId); // busca pela ficha tecnica

				    if (ficha.isPresent()) {
						    ficha.get().setGrupo(grupo.get());
						    FichaTecnica salvo = fichaTecnicaRepository.save(ficha.get());

						    MudarDeGrupoDTOResponse dtoResponse = new MudarDeGrupoDTOResponse();
						    dtoResponse.setId(salvo.getId());
						    dtoResponse.setIdGrupo(salvo.getGrupo().getId());
						    dtoResponse.setNomeGrupo(salvo.getGrupo().getNome());

						    return dtoResponse;
				    }
		    } return null;
    }

    /**
     * Alterar várias fichas tecnicas de um grupo(A) para um grupo(B)
     * @param grupoId
     * @param dtoRequest
     * @return
     */
  @Transactional
  public MudarDeGrupoEmLoteDTOResponse mudarDeGrupoEmLote(Integer grupoId, MudarDeGrupoDTORequest dtoRequest){
      Optional<Grupo> grupoA = this.grupoRepository.buscarPorId(grupoId);
			Optional<Grupo> grupoB = this.grupoRepository.buscarPorId(dtoRequest.getGrupo());

			if(grupoA.isPresent() && grupoB.isPresent()) {
      List<FichaTecnica> fichas = this.fichaTecnicaRepository.listarPorGrupo(grupoId);
				if(!fichas.isEmpty()) {
						for (FichaTecnica ficha : fichas) {
								ficha.setGrupo(grupoB.get());
								FichaTecnica salvo = fichaTecnicaRepository.save(ficha);
						}

						MudarDeGrupoEmLoteDTOResponse dtoResponse = new MudarDeGrupoEmLoteDTOResponse();
						dtoResponse.setIdGrupo(grupoB.get().getId());
						dtoResponse.setNomeDosItens(
								fichas.stream()
										.map(FichaTecnica::getNome)
										.collect(Collectors.toList()));

						return dtoResponse;
				}
				} return null;
  }

		/**
		 * Ativa a ficha tecnica para apresentação em cardápio e outros fins
		 * @param fichaId
		 * @return
		 */
	@Transactional
	public Boolean ativar(Integer fichaId){
			Optional<FichaTecnica> ficha = this.fichaTecnicaRepository.buscarPorId(fichaId);
				ficha.ifPresent( ativa -> {
						this.fichaTecnicaRepository.updateStatus(ficha.get().getId(), ativo);
				});

			return ficha.isPresent();
	}

		/**
		 * Apaga de forma lógica
		 * @param fichaId
		 * @return
		 */
    @Transactional
    public Boolean apagar(Integer fichaId){
      Optional<FichaTecnica> ficha = this.fichaTecnicaRepository.buscarPorId(fichaId);

			ficha.ifPresent( apaga -> {
					this.fichaTecnicaRepository.updateStatus(ficha.get().getId(), apagado);
			});

      return ficha.isPresent();
    }

    /**
     * Destroi objeto que tenha sido apagado
     * @param fichaId
     * @return
     */
    @Transactional
    public Boolean destroy(Integer fichaId) {
        Optional<FichaTecnica> ficha = this.fichaTecnicaRepository.findById(fichaId);
				if(ficha.isPresent()) {
						if (ficha.get().getStatus().equals(apagado)) {
								fichaTecnicaRepository.delete(ficha.get());
								return true;
						}
				} return false;
    }
}

