package projt4.praja.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projt4.praja.Enum.StatusEnum;
import projt4.praja.entity.FichaTecnica;
import projt4.praja.entity.Ingrediente;
import projt4.praja.entity.IngredienteFichaTecnica;
import projt4.praja.entity.dto.request.ingredienteFichaTecnica.AlterarQtdIngredienteFichaDTORequest;
import projt4.praja.entity.dto.request.ingredienteFichaTecnica.IngredienteFichaTecnicaDTORequest;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.AlterarMedidasIngredienteFichaDTOResponse;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.IngredienteEMFichaTecnicaDTOResponse;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.IngredienteFichaTecnicaDTOResponse;
import projt4.praja.exception.FichaTecnicaException;
import projt4.praja.exception.IngredienteException;
import projt4.praja.exception.IngredienteFichaTecnicaException;
import projt4.praja.repository.FichaTecnicaRepository;
import projt4.praja.repository.IngredienteFichaTecnicaRepository;
import projt4.praja.repository.IngredienteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredienteFichaTecnicaService {

  private final IngredienteFichaTecnicaRepository ingredienteFichaTecRepository;
  private final IngredienteRepository ingredienteRepository;
  private final FichaTecnicaRepository fichaTecnicaRepository;
  

  private IngredienteService ingredienteService;
  private FichaTecnicaService fichaTecnicaService;

  private final Integer ativo = StatusEnum.ATIVO.getStatus(),
        inativo = StatusEnum.INATIVO.getStatus(),
        apagado = StatusEnum.APAGADO.getStatus();
  
  public IngredienteFichaTecnicaService(
      IngredienteFichaTecnicaRepository ingredienteFichaTecRepository,
      IngredienteRepository ingredienteRepository,
      FichaTecnicaRepository fichaTecnicaRepository
  ) {
    this.ingredienteFichaTecRepository = ingredienteFichaTecRepository;
    this.ingredienteRepository = ingredienteRepository;
    this.fichaTecnicaRepository = fichaTecnicaRepository;
  }

    /**
     * Cria relação de ingrediente ficha tecnica
     * @param dtoRequestRequest
     * @return
     */
  @Transactional
  public IngredienteFichaTecnicaDTOResponse criar (IngredienteFichaTecnicaDTORequest dtoRequestRequest){
    Integer ingredienteId = dtoRequestRequest.getIngrediente(),
		    fichaId = dtoRequestRequest.getFichaTecnica();

		Ingrediente ingrediente = this.ingredienteRepository.buscarPorId((ingredienteId)) // Verifica existencia de ingrediente
		    .orElseThrow(() -> new IngredienteException("Ingrediente com o ID: " + ingredienteId + " não encontrado"));
		FichaTecnica ficha = this.fichaTecnicaRepository.buscarPorId(fichaId) // Verifica existencia de ficha tecnica
				.orElseThrow(() -> new FichaTecnicaException("Ficha tecnica com o ID: " + fichaId + " não encontrada"));
    this.ingredienteFichaTecRepository.buscarIngredienteEmFichaTecnica(ingredienteId, fichaId)
        .ifPresent(existente -> {
                throw new IngredienteFichaTecnicaException("Já existe relação Ingrediente Ficha Tecnica");
        });
    IngredienteFichaTecnica ingredienteFicha = new IngredienteFichaTecnica();
    ingredienteFicha.setQtd(dtoRequestRequest.getQtd());
    ingredienteFicha.setUnidadeMedida(dtoRequestRequest.getUnidadeMedida());
    ingredienteFicha.setIngrediente(ingrediente);
    ingredienteFicha.setFichaTecnica(ficha);
    ingredienteFicha.setStatus(ativo);
//    ingredienteFicha.setObservacao(dtoRequestRequest.getObservacao());

    IngredienteFichaTecnica ingredienteFichaSave = ingredienteFichaTecRepository.save(ingredienteFicha);

    IngredienteFichaTecnicaDTOResponse dtoResponse= new IngredienteFichaTecnicaDTOResponse();
    dtoResponse.setId(ingredienteFichaSave.getId());
    dtoResponse.setQtd(ingredienteFichaSave.getQtd());
    dtoResponse.setUnidadeMedida(ingredienteFichaSave.getUnidadeMedida());
    dtoResponse.setIngrediente(ingredienteFichaSave.getIngrediente().getId());
    dtoResponse.setFichaTecnica(ingredienteFichaSave.getFichaTecnica().getId());
    dtoResponse.setStatus(ingredienteFichaSave.getStatus());
//    dtoResponse.setObservacao(ingredienteFichaSave.getObservacao());
    return dtoResponse;

  }

  public List<IngredienteEMFichaTecnicaDTOResponse> listarIngredientesEmFichaTecnica(Integer fichaTecnicaId){
      List<IngredienteFichaTecnica> obterLista = this.ingredienteFichaTecRepository.listarIngredienteEmFichas(fichaTecnicaId);

			if(!obterLista.isEmpty()) {
          List<IngredienteEMFichaTecnicaDTOResponse> responseListaIngredientesEmFicha = new ArrayList<IngredienteEMFichaTecnicaDTOResponse>();
          for (IngredienteFichaTecnica ingrediente : obterLista) {
              IngredienteEMFichaTecnicaDTOResponse dto = new IngredienteEMFichaTecnicaDTOResponse();
              dto.setId(ingrediente.getId());
              dto.setIdIngrediente(ingrediente.getIngrediente().getId());
              dto.setNomeIngrediente(ingrediente.getIngrediente().getNome());
              dto.setUnidadeMedida(ingrediente.getUnidadeMedida());
              dto.setQtd(ingrediente.getQtd());
              //              dto.setPrepato(ingrediente.getObservacao());
              responseListaIngredientesEmFicha.add(dto);
          }
          return responseListaIngredientesEmFicha;
      }
      return null;
  }

  @Transactional
  public AlterarMedidasIngredienteFichaDTOResponse alterarMedidasIngredienteFicha(Integer ingredienteFichaId, AlterarQtdIngredienteFichaDTORequest dtoRequest) {

      Optional<IngredienteFichaTecnica> busca = this.ingredienteFichaTecRepository.buscarPorId(ingredienteFichaId);
		  if(busca.isPresent()){
					busca.get().set
      IngredienteFichaTecnica ingredienteFichaSave = ingredienteFichaTecRepository.save(ingredienteFicha);

      AlterarMedidasIngredienteFichaDTOResponse dtoResponse= new AlterarMedidasIngredienteFichaDTOResponse();
      dtoResponse.setId(ingredienteFichaSave.getId());
      dtoResponse.setQtd(ingredienteFichaSave.getQtd());
      dtoResponse.setUnidadeMedida(ingredienteFichaSave.getUnidadeMedida());
//      dtoResponse.setObservacao(ingredienteFichaSave.getObservacao());

      return dtoResponse.;

    } return null;
  }

  public Boolean apagar(Integer ingredienteFichaId) {
			Optional<IngredienteFichaTecnica> ingredienteFichaReturn = this.ingredienteFichaTecRepository.buscarPorId(ingredienteFichaId);
		  ingredienteFichaReturn.ifPresent(
					ingredienteFicha ->{
					this.ingredienteFichaTecRepository.updateStatus(ingredienteFichaId, apagado);
			});

        return ingredienteFichaReturn.isPresent();
	}
}