package projt4.praja.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projt4.praja.Enum.StatusEnum;
import projt4.praja.Enum.UnidadeMedidaEnum;
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

  // Variaveis para melhor leitura de código
  private final int ativo = 1,
            inativo = 0,
            impulsionar = 2,
            apagado = -1;
  
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
     * Cria relação de UM ingrediente a ficha tecnica
     * @param dtoRequestRequest
     * @return
     */
  @Transactional
  public IngredienteEMFichaTecnicaDTOResponse adicionarItem (IngredienteFichaTecnicaDTORequest dtoRequestRequest){
    Integer ingredienteId = dtoRequestRequest.ingrediente(), // armazena os Ids
		    fichaId = dtoRequestRequest.fichaTecnica();

		Optional<Ingrediente> ingrediente = this.ingredienteRepository.buscarPorId((ingredienteId)); // Verifica existencia de ingredient
		Optional<FichaTecnica> ficha = this.fichaTecnicaRepository.buscarPorId(fichaId); // Verifica existencia de ficha tecnica

		  if (ingrediente.isEmpty() || ficha.isEmpty()) {
				  return null; // ou lance uma exceção customizada BadRequestNotFound
		  }
				Optional<IngredienteFichaTecnica> buscaDeEntrada = this.ingredienteFichaTecRepository.buscarIngredienteEmFichaTecnica(ingredienteId, fichaId);

			// sobreescreve/ atualiza a entrada existente
		  if (buscaDeEntrada.isPresent()) {
				  IngredienteFichaTecnica existente = buscaDeEntrada.get();
				  IngredienteEMFichaTecnicaDTOResponse dtoResponse = new IngredienteEMFichaTecnicaDTOResponse();
				  dtoResponse.setId(existente.getId());
				  dtoResponse.setQtd(existente.getQtd());
				  dtoResponse.setUnidadeMedida(existente.getUnidadeMedida());
				  dtoResponse.setDetalhe(existente.getDetalhe());
				  dtoResponse.setIdIngrediente(existente.getIngrediente().getId());
				  dtoResponse.setNomeIngrediente(existente.getIngrediente().getNome());
				  return dtoResponse;
		  }
		  // Se não existe, cria nova entrada
				IngredienteFichaTecnica ingredienteFicha = new IngredienteFichaTecnica();
				ingredienteFicha.setQtd(dtoRequestRequest.qtd());
				ingredienteFicha.setUnidadeMedida(dtoRequestRequest.unidadeMedida());
				ingredienteFicha.setIngrediente(ingrediente.get());
		    ingredienteFicha.setDetalhe(ingredienteFicha.getDetalhe());
				ingredienteFicha.setFichaTecnica(ficha.get());
				ingredienteFicha.setStatus(ativo);
		    ingredienteFicha.setDetalhe(dtoRequestRequest.detalhe());

				IngredienteFichaTecnica ingredienteFichaSave = ingredienteFichaTecRepository.save(ingredienteFicha);

				IngredienteEMFichaTecnicaDTOResponse dtoResponse = new IngredienteEMFichaTecnicaDTOResponse();
				dtoResponse.setId(ingredienteFichaSave.getId());
				dtoResponse.setQtd(ingredienteFichaSave.getQtd());
				dtoResponse.setUnidadeMedida(ingredienteFichaSave.getUnidadeMedida());
		    dtoResponse.setDetalhe(ingredienteFichaSave.getDetalhe());
				dtoResponse.setIdIngrediente(ingredienteFichaSave.getIngrediente().getId());
				dtoResponse.setNomeIngrediente(ingredienteFichaSave.getIngrediente().getNome());
				return dtoResponse;


  }


    /**
     * Recebe uma lista de dados para criação de ficha tecnicas
     * @param dtoRequest
     * @return
     */
    @Transactional
    public List<IngredienteEMFichaTecnicaDTOResponse> criar(List<IngredienteFichaTecnicaDTORequest> dtoRequest){
        List<IngredienteEMFichaTecnicaDTOResponse> dtoResponse = new ArrayList<>();
        for (IngredienteFichaTecnicaDTORequest dto : dtoRequest) {
		        IngredienteEMFichaTecnicaDTOResponse ingredienteFicha = this.adicionarItem(dto);
            dtoResponse.add(ingredienteFicha);
        }
        return dtoResponse;
    }

  public List<IngredienteEMFichaTecnicaDTOResponse> listarIngredientesEmFichaTecnica(Integer fichaTecnicaId){
      List<IngredienteFichaTecnica> obterLista = this.ingredienteFichaTecRepository.listarIngredienteEmFichas(fichaTecnicaId);

			if(!obterLista.isEmpty()) {
					UnidadeMedidaEnum unidadeMedidaEnum;
          List<IngredienteEMFichaTecnicaDTOResponse> responseListaIngredientesEmFicha = new ArrayList<IngredienteEMFichaTecnicaDTOResponse>();

          for (IngredienteFichaTecnica ingrediente : obterLista) {
              IngredienteEMFichaTecnicaDTOResponse dto = new IngredienteEMFichaTecnicaDTOResponse();
              dto.setId(ingrediente.getId());
              dto.setIdIngrediente(ingrediente.getIngrediente().getId());
              dto.setNomeIngrediente(ingrediente.getIngrediente().getNome());
              dto.setUnidadeMedida(ingrediente.getUnidadeMedida());
							dto.setUnidadeMedidaString(
									UnidadeMedidaEnum.getString(ingrediente.getUnidadeMedida())
											.orElse("N/A"));
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
					busca.get().setUnidadeMedida(dtoRequest.unidadeMedida());
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