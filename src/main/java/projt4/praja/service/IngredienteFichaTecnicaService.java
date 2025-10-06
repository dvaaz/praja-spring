package projt4.praja.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projt4.praja.entity.FichaTecnica;
import projt4.praja.entity.Ingrediente;
import projt4.praja.entity.IngredienteFichaTecnica;
import projt4.praja.entity.dto.request.ingredienteFichaTecnica.IngredienteFichaTecnicaDTORequest;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.IngredienteEMFichaTecnicaDTOResponse;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.IngredienteFichaTecnicaDTOResponse;
import projt4.praja.exception.FichaTecnicaException;
import projt4.praja.exception.IngredienteException;
import projt4.praja.repository.FichaTecnicaRepository;
import projt4.praja.repository.IngredienteFichaTecnicaRepository;
import projt4.praja.repository.IngredienteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredienteFichaTecnicaService {

  private final IngredienteFichaTecnicaRepository ingredienteFichaTecRepository;
  private final IngredienteRepository ingredienteRepository;
  private final FichaTecnicaRepository fichaTecnicaRepository;
  

  private IngredienteService ingredienteService;
  private FichaTecnicaService fichaTecnicaService;
  
  public IngredienteFichaTecnicaService(
      IngredienteFichaTecnicaRepository ingredienteFichaTecRepository,
      IngredienteRepository ingredienteRepository,
      FichaTecnicaRepository fichaTecnicaRepository
  ) {
    this.ingredienteFichaTecRepository = ingredienteFichaTecRepository;
    this.ingredienteRepository = ingredienteRepository;
    this.fichaTecnicaRepository = fichaTecnicaRepository;
  }

  @Transactional
  public IngredienteFichaTecnicaDTOResponse criarRelacaoIngredienteFichaTecnica (IngredienteFichaTecnicaDTORequest dtoRequestRequest){
    Integer ingredienteId = dtoRequestRequest.getIngrediente(),
		    fichaId = dtoRequestRequest.getFichaTecnica();

		Ingrediente ingrediente = this.ingredienteRepository.buscarPorId(
				(ingredienteId))
		    .orElseThrow(() -> new IngredienteException("Ingrediente com o ID: " + ingredienteId + " não encontrado")););
		FichaTecnica ficha = this.fichaTecnicaRepository.buscarPorId(fichaId)
				.orElseThrow(() -> new FichaTecnicaException("Ficha tecnica com o ID: " + fichaId + " não encontrada"));

		
		
		if(duplicata != null){ return null; }
    IngredienteFichaTecnica ingredienteFicha =  new IngredienteFichaTecnica();
    ingredienteFicha.setQtd(dtoRequestRequest.getQtd());
    ingredienteFicha.setUnidadeMedida(dtoRequestRequest.getUnidadeMedida());
    ingredienteFicha.setIngredienteId(ingrediente);
    ingredienteFicha.setFichaTecnicaId(fichaTecnica);
    ingredienteFicha.setStatus(dtoRequestRequest.getStatus());
//    ingredienteFicha.setObservacao(dtoRequestRequest.getObservacao());

    IngredienteFichaTecnica ingredienteFichaSave = ingredienteFichaTecRepository.save(ingredienteFicha);

    IngredienteFichaTecnicaDTOResponse dtoResponse= new IngredienteFichaTecnicaDTOResponse();
    dtoResponse.setId(ingredienteFichaSave.getId());
    dtoResponse.setQtd(ingredienteFichaSave.getQtd());
    dtoResponse.setUnidadeMedida(ingredienteFichaSave.getUnidadeMedida());
    dtoResponse.setIngrediente(ingredienteFichaSave.getIngredienteId().getId());
    dtoResponse.setFichaTecnica(ingredienteFichaSave.getFichaTecnicaId().getId());
    dtoResponse.setStatus(ingredienteFichaSave.getStatus());
//    dtoResponse.setObservacao(ingredienteFichaSave.getObservacao());
    return dtoResponse;

  }

  public List<IngredienteEMFichaTecnicaDTOResponse> listarIngredientesDeFichaTecnica(Integer fichaTecnicaId){
      List<IngredienteFichaTecnica> obterLista = this.ingredienteFichaTecRepository.listarIngredientesEmFichaTecnica(fichaTecnicaId);
      if(obterLista != null) {
          List<IngredienteEMFichaTecnicaDTOResponse> responseListaIngredientesEmFicha = new ArrayList<IngredienteEMFichaTecnicaDTOResponse>();
          for (IngredienteFichaTecnica ingrediente : obterLista) {
              IngredienteEMFichaTecnicaDTOResponse dto = new IngredienteEMFichaTecnicaDTOResponse();
              dto.setId(ingrediente.getId());
              dto.setIdIngrediente(ingrediente.getIngredienteId().getId());
              dto.setNomeIngrediente(ingrediente.getIngredienteId().getNome());
              dto.setUnidadeMedida(ingrediente.getUnidadeMedida());
              dto.setQtd(ingrediente.getQtd());
              //              dto.setPrepato(ingrediente.getObservacao());
              responseListaIngredientesEmFicha.add(dto);
          }
          return responseListaIngredientesEmFicha;
      }
      throw new RuntimeException("A ficha tecnica não foi encontrada");
  }

  @Transactional
  public AlterarMedidasIngredienteFichaDTOResponse alterarMedidasIngredienteFicha(Integer ingredienteFichaId, AlterarMedidasIngredienteFichaDTORequest dtoRequest) {
    IngredienteFichaTecnica ingredienteFichaTecnica = this.ingredienteFichaTecRepository.buscarIngredienteEmFichaTecnica(ingredienteFichaId);
    if (ingredienteFichaTecnica != null) {
      ingredienteFichaTecnica.setUnidadeMedida(dtoRequest.getUnidadeMedida());
      ingredienteFichaTecnica.setQtd(dtoRequest.getQtd());
//      ingredienteFichaTecnica.setObservacao(dtoRequest.getObservacao());

      IngredienteFichaTecnica ingredienteFichaSave = ingredienteFichaTecRepository.save(ingredienteFichaTecnica);

      AlterarMedidasIngredienteFichaDTOResponse dtoResponse= new AlterarMedidasIngredienteFichaDTOResponse();
      dtoResponse.setId(ingredienteFichaSave.getId());
      dtoResponse.setQtd(ingredienteFichaSave.getQtd());
      dtoResponse.setUnidadeMedida(ingredienteFichaSave.getUnidadeMedida());
//      dtoResponse.setObservacao(ingredienteFichaSave.getObservacao());

      return dtoResponse;

    } throw new RuntimeException("Nenhuma ficha tecnica com esse ID foi encontrada");
  }

  public void pagarLogicoIngredienteFichaTecnica(Integer ingredienteFichaId){
    this.ingredienteFichaTecRepository.apagarLogicoIngredienteFichaTecnica(ingredienteFichaId);