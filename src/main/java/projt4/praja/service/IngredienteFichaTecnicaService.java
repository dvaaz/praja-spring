package projt4.praja.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projt4.praja.Enum.StatusEnum;
import projt4.praja.entity.FichaTecnica;
import projt4.praja.entity.Ingrediente;
import projt4.praja.entity.IngredienteFichaTecnica;
import projt4.praja.entity.dto.request.shared.AlterarUnidadeMedidaDTORequest;
import projt4.praja.entity.dto.request.ingredienteFichaTecnica.IngredienteFichaTecnicaDTORequest;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.AlterarUnidadeMedidaIngredienteFichaDTOResponse;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.IngredienteEMFichaTecnicaDTOResponse;
import projt4.praja.entity.dto.response.ingredienteFichaTecnica.IngredienteFichaTecnicaDTOResponse;
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
    Integer ingredienteId = dtoRequestRequest.ingrediente(), // armazena os Ids
		    fichaId = dtoRequestRequest.fichaTecnica();

		Optional<Ingrediente> ingrediente = this.ingredienteRepository.buscarPorId((ingredienteId)); // Verifica existencia de ingredient
		Optional<FichaTecnica> ficha = this.fichaTecnicaRepository.buscarPorId(fichaId); // Verifica existencia de ficha tecnica

		if (ingrediente.isPresent() && ficha.isPresent()) {
				Optional<IngredienteFichaTecnica> buscaDeEntrada = this.ingredienteFichaTecRepository.buscarIngredienteEmFichaTecnica(ingredienteId, fichaId);

				if (buscaDeEntrada.isPresent()) {
						IngredienteFichaTecnica ingredienteFicha = new IngredienteFichaTecnica();
						ingredienteFicha.setQtd(dtoRequestRequest.qtd());
						ingredienteFicha.setUnidadeMedida(dtoRequestRequest.unidadeMedida());
						ingredienteFicha.setIngrediente(ingrediente.get());
            ingredienteFicha.setDetalhe(ingredienteFicha.getDetalhe());
						ingredienteFicha.setFichaTecnica(ficha.get());
						ingredienteFicha.setStatus(ativo);
            ingredienteFicha.setDetalhe(dtoRequestRequest.detalhe());

						IngredienteFichaTecnica ingredienteFichaSave = ingredienteFichaTecRepository.save(ingredienteFicha);

            IngredienteFichaTecnicaDTOResponse dtoResponse = new IngredienteFichaTecnicaDTOResponse();
						dtoResponse.setId(ingredienteFichaSave.getId());
						dtoResponse.setQtd(ingredienteFichaSave.getQtd());
						dtoResponse.setUnidadeMedida(ingredienteFichaSave.getUnidadeMedida());
            dtoResponse.setDetalhe(ingredienteFichaSave.getDetalhe());
						dtoResponse.setIngrediente(ingredienteFichaSave.getIngrediente().getId());
						dtoResponse.setFichaTecnica(ingredienteFichaSave.getFichaTecnica().getId());
						dtoResponse.setStatus(ingredienteFichaSave.getStatus());
						return dtoResponse;
				}
		} return null;
  }

    /** Recebe uma lista de dados para criação de ficha tecnicas
     *
     * @param dtoRequest
     * @return
     */
    public List<IngredienteFichaTecnicaDTOResponse> criarVarias(List<IngredienteFichaTecnicaDTORequest> dtoRequest){
        List<IngredienteFichaTecnicaDTOResponse> dtoResponse = new ArrayList<>();
        for (IngredienteFichaTecnicaDTORequest dto : dtoRequest) {
            IngredienteFichaTecnicaDTOResponse ingredienteFicha = this.criar(dto);
            dtoResponse.add(ingredienteFicha);
        }
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
              dto.setDetalhe(ingrediente.getDetalhe());
              dto.setQtd(ingrediente.getQtd());
              responseListaIngredientesEmFicha.add(dto);
          }
          return responseListaIngredientesEmFicha;
      }
      return null;
  }

    /**
     * Alteração do atributo de Unidade de Medida NA FICHA TECNICA
     * @param ingredienteFichaId
     * @param dtoRequest
     * @return
     */
  @Transactional
  public AlterarUnidadeMedidaIngredienteFichaDTOResponse alterarUnidadeMedida(Integer ingredienteFichaId, AlterarUnidadeMedidaDTORequest dtoRequest) {

      Optional<IngredienteFichaTecnica> busca = this.ingredienteFichaTecRepository.buscarPorId(ingredienteFichaId);
		  if(busca.isPresent()){
					busca.get().setUnidadeMedida(dtoRequest.getUnidadeMedida());
      IngredienteFichaTecnica save = ingredienteFichaTecRepository.save(busca.get());

      AlterarUnidadeMedidaIngredienteFichaDTOResponse dtoResponse= new AlterarUnidadeMedidaIngredienteFichaDTOResponse();
      dtoResponse.setId(save.getId());
      dtoResponse.setUnidadeMedida(save.getUnidadeMedida());
//      dtoResponse.setObservacao(ingredienteFichaSave.getObservacao());

      return dtoResponse;

    } return null;
  }

  public Boolean apagar(Integer ingredienteFichaId) {
			Optional<IngredienteFichaTecnica> ingredienteFicha = this.ingredienteFichaTecRepository.buscarPorId(ingredienteFichaId);
		  ingredienteFicha.ifPresent(
					apaga ->{
					this.ingredienteFichaTecRepository.updateStatus(ingredienteFichaId, apagado);
			});

        return ingredienteFicha.isPresent();
	}
}