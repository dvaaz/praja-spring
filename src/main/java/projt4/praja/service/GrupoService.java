package projt4.praja.service;

import projt4.praja.Enum.GrupoEnum;
import projt4.praja.Enum.StatusEnum;
import projt4.praja.entity.FichaTecnica;
import projt4.praja.entity.Grupo;
import projt4.praja.entity.Ingrediente;
import projt4.praja.entity.dto.request.grupo.AlterarCorDTORequest;
import projt4.praja.entity.dto.request.grupo.GrupoDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarNomeDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarStatusDTORequest;
import projt4.praja.entity.dto.response.grupo.GrupoAtualizarDTOResponse;
import projt4.praja.entity.dto.response.grupo.GrupoDTOResponse;
import projt4.praja.entity.dto.response.shared.AlterarStatusDTOResponse;
import projt4.praja.repository.FichaTecnicaRepository;
import projt4.praja.repository.GrupoRepository;
import projt4.praja.repository.IngredienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {
    private final GrupoRepository grupoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;

    // Enums

    public GrupoService(GrupoRepository grupoRepository,
                        IngredienteRepository ingredienteRepository,
                        FichaTecnicaRepository fichaTecnicaRepository) {
        this.grupoRepository = grupoRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
    }

    // // Variaveis para melhor leitura de código
    private final String grupoIngrNome = GrupoEnum.ingrediente.getText(),
            grupoFichNome = GrupoEnum.fichaTecnica.getText();
    private final Integer grupoIngrNum = GrupoEnum.ingrediente.getNumber(),
            grupoFichaNum = GrupoEnum.fichaTecnica.getNumber();
    private final int ativo = 1,
            inativo = 0,
            apagado = -1;

    // Inicio dos métodos
		/**
		 * Conversao manual sem uso de model mapper
		 * @param grupo
		 * @return dtoResponse
		 */
	public GrupoDTOResponse conversaoDtoResponse (Grupo grupo){
			if (grupo == null) {
					return null;
			}
			GrupoDTOResponse dtoResponse = new GrupoDTOResponse();

			// Atribuição Manual de Propriedades:
			dtoResponse.setId(grupo.getId());
			dtoResponse.setNome(grupo.getNome());
			dtoResponse.setCor(grupo.getCor());
			dtoResponse.setTipo(grupo.getTipo());
			dtoResponse.setStatus(grupo.getStatus());

			return dtoResponse;
	}

    /**
     * Cria um novo grupo de acordo com a regra unique name
     * @param dtoRequest
     * @return
     */
    @Transactional
    public GrupoDTOResponse criar(GrupoDTORequest dtoRequest) {
		    if (!(dtoRequest.tipo().equals(grupoIngrNum) || dtoRequest.tipo().equals(grupoFichaNum))) {
				    return null;
		    }

				Grupo grupo = new Grupo();
				grupo.setNome(dtoRequest.nome());
				grupo.setCor(dtoRequest.cor());
				grupo.setTipo(dtoRequest.tipo());
        grupo.setStatus(ativo);

        Grupo salvo = grupoRepository.save(grupo);

				return this.conversaoDtoResponse(salvo); // conversao manual no proprio método, evitando model mapper

        }

    /**
     * Cria o grupo padrao de ingrediente
     *
     * @return Grupo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected Grupo criarGrupoPadraoIngrediente() {
        Grupo grupoDefault = new Grupo();

        grupoDefault.setNome(grupoIngrNome);
        grupoDefault.setStatus(ativo);
        grupoDefault.setCor("90EE90");
        grupoDefault.setTipo(grupoIngrNum);

        Grupo save = grupoRepository.save(grupoDefault);

        return save;

    }

    /**
     * Cria o grupo padrao de ficha tecnica
     *
     * @return Grupo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected Grupo criarGrupoPadraoFichaTecnica() {
        Grupo grupoDefault = new Grupo();
        grupoDefault.setNome(grupoFichNome);
        grupoDefault.setStatus(ativo);
        grupoDefault.setCor("FA8907");
        grupoDefault.setTipo(grupoFichaNum);

        Grupo save = grupoRepository.save(grupoDefault);

        return save;
    }

    /**
     * Busca grupo padrao ou, caso não haja, cria o grupo padrao de ingredientes
     *
     * @return Grupo
     */
    @Transactional
    public Grupo buscarOuCriarGrupoIngrediente() {
        return grupoRepository.buscarGrupoPadrao(
                grupoIngrNum, grupoIngrNome)
            .orElseGet(() -> criarGrupoPadraoIngrediente());
    }

    @Transactional
    public Grupo buscarOuCriarGrupoFichaTecnica() {
        return grupoRepository.buscarGrupoPadrao(
                        grupoIngrNum, grupoIngrNome)
                .orElseGet(() -> criarGrupoPadraoFichaTecnica());
    }

    /**
     * Busca produto por id
     * @param id
     * @return
     */
    public GrupoDTOResponse buscarPorID(Integer id) {
        Optional<Grupo> grupo = this.grupoRepository.buscarPorId(id);

				if (grupo.isEmpty()) { return null; }

		    return this.conversaoDtoResponse(grupo.get()); // conversao manual no proprio método, evitando model mapper
    }

    /**
     * Lista grupos com status >=0
     *
     * @return listDTOResponse
     */
    public List<GrupoDTOResponse> listar() {
        List<Grupo> grupos = this.grupoRepository.listar();

        if (grupos.isEmpty()) {
            return null;
        }

        return grupos.stream()
            .map(this::conversaoDtoResponse )
		        .toList();
    }

    /**
     * Lista grupos de fichas tecnicas ou ingredientes com status >=0
     *
     * @return listDTOResponse
     */
    public List<GrupoDTOResponse> listarGruposDoTipo(Integer tipo) {
        List<Grupo> grupos = this.grupoRepository.listarPorTipo(tipo);

        if (grupos.isEmpty()) { return null; }

		    return grupos.stream()
				    .map(this::conversaoDtoResponse )
				    .toList();
    }

    @Transactional
    public GrupoAtualizarDTOResponse alterarNome(Integer id, AlterarNomeDTORequest dtoRequest) {
        Optional<Grupo> grupo = grupoRepository.buscarPorId(id);

				if (grupo.isEmpty()) { return null; }

				grupo.get().setNome(dtoRequest.nome());
        Grupo save = grupoRepository.save(grupo.get());

				GrupoAtualizarDTOResponse dtoResponse = new GrupoAtualizarDTOResponse();
				dtoResponse.setId(save.getId());
				dtoResponse.setNome(save.getNome());
				dtoResponse.setCor(save.getCor());

				return dtoResponse;

    }

		public GrupoAtualizarDTOResponse alterarCor(Integer id, AlterarCorDTORequest dtoRequest) {
				Optional<Grupo> grupo = grupoRepository.buscarPorId(id);

				if (grupo.isEmpty()) { return null; }

				grupo.get().setCor(dtoRequest.cor());
				Grupo save = grupoRepository.save(grupo.get());

				GrupoAtualizarDTOResponse dtoResponse = new GrupoAtualizarDTOResponse();
				dtoResponse.setId(save.getId());
				dtoResponse.setNome(save.getNome());
				dtoResponse.setCor(save.getCor());

				return dtoResponse;
		}

    /**
     * Apaga o grupo. Caso haja elementos no grupo eles serão alocados para o grupo padrao devido.
     *
     * @param id
     */
    @Transactional
    public void apagarGrupo(Integer id) {
        Optional<Grupo> grupo = this.grupoRepository.buscarPorId(id);

				if (grupo.isEmpty()) { return; }

        // Remaneja os itens presentes nos grupos para seus devidos grupos padrões.
        if (grupo.get().getTipo().equals(grupoIngrNum) ) { // busca ingredientes de um grupo
            List<Ingrediente> listaIngredientes = this.ingredienteRepository.listarPorGrupo(id);
            if (!listaIngredientes.isEmpty()) {
                Grupo grupoPadrao = this.buscarOuCriarGrupoIngrediente();

                for (Ingrediente ingrediente : listaIngredientes) {
                    ingrediente.setGrupo(grupoPadrao);
                }
            }
        } else if (grupo.get().getTipo().equals(grupoFichaNum) ) {
            List<FichaTecnica> listaFichasTecnicas = this.fichaTecnicaRepository.listarPorGrupo(id);
            if (!listaFichasTecnicas.isEmpty()) {
                Grupo grupoPadrao = this.buscarOuCriarGrupoFichaTecnica();

                for (FichaTecnica fichaTecnica : listaFichasTecnicas) {
                    fichaTecnica.setGrupo(grupoPadrao);
                }
            }
        }
    }

    @Transactional
    public AlterarStatusDTOResponse atualizarStatus(Integer id, AlterarStatusDTORequest dtoRequest) {
        Integer novoStatus = dtoRequest.status();

        Optional<Grupo>  grupo = this.grupoRepository.buscarPorId(id);

				if (grupo.isEmpty()) { return null; }

        grupo.get().setStatus(dtoRequest.status());
        Grupo save = grupoRepository.save(grupo.get());

				AlterarStatusDTOResponse dtoResponse = new AlterarStatusDTOResponse();
				dtoResponse.setId(save.getId());
				dtoResponse.setStatus(save.getStatus());
				return dtoResponse;
    }

    // Incluir lógica para remanejar os itens que estejam dentro do grupo
    @Transactional
    public boolean apagar(Integer id){
        Optional<Grupo> grupo = this.grupoRepository.buscarPorId(id);
        grupo.ifPresent(apaga -> {
		        this.grupoRepository.updateStatus(id, apagado);
        });
        return grupo.isPresent();
    }

    /**
     * Destroi objeto que tenha sido apagado
     * @param id
     * @return
     */
    @Transactional
    public boolean destroy(Integer id) {
        Optional<Grupo> grupo = this.grupoRepository.findById(id);

        if (grupo.isPresent()) {
            if (grupo.get().getStatus().equals(apagado)) {
                grupoRepository.delete(grupo.get());
                return true;
            }
        }
        return false;

    }


}