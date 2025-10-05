package projt4.praja.service;

import projt4.praja.Enum.GrupoEnum;
import projt4.praja.Enum.StatusEnum;
import projt4.praja.entity.FichaTecnica;
import projt4.praja.entity.Grupo;
import projt4.praja.entity.Ingrediente;
import projt4.praja.entity.dto.request.grupo.GrupoDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarGrupoDTORequest;
import projt4.praja.entity.dto.request.shared.AlterarStatusDTORequest;
import projt4.praja.entity.dto.response.grupo.GrupoAtualizarDTOResponse;
import projt4.praja.entity.dto.response.grupo.GrupoDTOResponse;
import projt4.praja.entity.dto.response.shared.AlterarStatusDTOResponse;
import projt4.praja.exception.GrupoException;
import projt4.praja.repository.FichaTecnicaRepository;
import projt4.praja.repository.GrupoRepository;
import projt4.praja.repository.IngredienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {
    private final GrupoRepository grupoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final ModelMapper modelMapper;

    // Enums

    public GrupoService(GrupoRepository grupoRepository,
                        IngredienteRepository ingredienteRepository,
                        FichaTecnicaRepository fichaTecnicaRepository,
                        ModelMapper modelMapper) {
        this.grupoRepository = grupoRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.modelMapper = modelMapper;
    }

    // Enuns a serem utilizados na classe
    private final String grupoIngrNome = GrupoEnum.ingrediente.getText(),
            grupoFichNome = GrupoEnum.fichaTecnica.getText();
    private final Integer grupoIngrNum = GrupoEnum.ingrediente.getNumber(),
            grupoFichaNum = GrupoEnum.fichaTecnica.getNumber();
    private final Integer ativo = StatusEnum.ATIVO.getStatus(),
            inativo = StatusEnum.INATIVO.getStatus(),
            apagado = StatusEnum.APAGADO.getStatus();

    // Inicio dos métodos

    /**
     * Cria um novo grupo de acordo com a regra unique name
     * @param dtoRequest
     * @return
     */
    @Transactional
    public GrupoDTOResponse criar(GrupoDTORequest dtoRequest) {
        Grupo grupo = modelMapper.map(dtoRequest, Grupo.class);
        grupo.setStatus(ativo);

        if (grupo.getTipo() != grupoIngrNum || grupo.getTipo() != grupoFichaNum) {
            return null;
        }
            try {
                Grupo salvo = grupoRepository.save(grupo);
                return modelMapper.map(salvo, GrupoDTOResponse.class);
            } catch (DataIntegrityViolationException ex) {
                throw new RuntimeException("Erro ao gravar grupo ", ex);
            }
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
				try {
        Grupo save = grupoRepository.save(grupoDefault);

        return save;
				} catch (DataIntegrityViolationException ex) {
						throw new RuntimeException("Erro ao gravar grupo padrão de ingredientes", ex);
				}
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
        try {
		        Grupo save = grupoRepository.save(grupoDefault);

		        return save;
        } catch (DataIntegrityViolationException ex) {
		        throw new RuntimeException("Erro ao gravar grupo padrão de fichas tecnicas", ex);
        }
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
        Grupo grupo = this.grupoRepository.buscarPorId(id)
            .orElseThrow(() -> new GrupoException("Grupo com o ID: " + id + " não encontrado"));

        GrupoDTOResponse dtoResponse = this.modelMapper.map(grupo, GrupoDTOResponse.class);

        return dtoResponse;
    }

    /**
     * Lista grupos com status >=0
     *
     * @return listDTOResponse
     */
    public List<GrupoDTOResponse> listar() {
        List<Grupo> grupos = this.grupoRepository.listar();

        if (grupos.isEmpty()) {
            throw new GrupoException("Não há nenhum grupo criado.");
        }

        return grupos.stream()
            .map(grupo -> modelMapper.map(grupo, GrupoDTOResponse.class))
            .toList();
    }

    /**
     * Lista grupos de fichas tecnicas ou ingredientes com status >=0
     *
     * @return listDTOResponse
     */
    public List<GrupoDTOResponse> listarGruposDoTipo(Integer tipo) {
        List<Grupo> grupos = this.grupoRepository.listarPorTipo(tipo);

        if (grupos.isEmpty()) {
            GrupoEnum grupoEnum = GrupoEnum.values()[tipo];
            throw new GrupoException("Não há nenhum grupo de " + grupoEnum + " criado.");
        }

        return grupos.stream()
            .map(grupo -> modelMapper.map(grupo, GrupoDTOResponse.class))
            .toList();
    }

    @Transactional
    public GrupoAtualizarDTOResponse alterarDetalhes(Integer grupoId, AlterarGrupoDTORequest dtoRequest) {
        Grupo grupo = grupoRepository.buscarPorId(grupoId)
            .orElseThrow(() -> new GrupoException("Grupo com o ID: "+grupoId +" não encontrado"));;

        if (dtoRequest.getCor() != null) {
            grupo.setCor(dtoRequest.getCor());
        }
        if (dtoRequest.getNome()!= null) {
            grupo.setNome(dtoRequest.getNome());
        }
        try {
            Grupo save = grupoRepository.save(grupo);
            return modelMapper.map(save, GrupoAtualizarDTOResponse.class);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Um objeto com este nome já existe: " + dtoRequest.getNome(), ex);
        }
    }

    /**
     * Apaga o grupo. Caso haja elementos no grupo eles serão alocados para o grupo padrao devido.
     *
     * @param grupoId
     */
    @Transactional
    public void apagarGrupo(Integer grupoId) {
        Grupo grupo = this.grupoRepository.buscarPorId(grupoId)
            .orElseThrow(() -> new GrupoException("Grupo com o Id:" + grupoId + " não encontrado"));

        // Remaneja os itens presentes nos grupos para seus devidos grupos padrões.
        if (grupo.getTipo() == grupoIngrNum) {
            List<Ingrediente> listaIngredientes = this.ingredienteRepository.listarPorGrupo(grupoId);
            if (!listaIngredientes.isEmpty()) {
                Grupo grupoPadrao = this.grupoRepository.buscarGrupoPadrao(grupoIngrNum, grupoIngrNome)
                    .orElseGet(this::criarGrupoPadraoIngrediente);

                for (Ingrediente ingrediente : listaIngredientes) {
                    ingrediente.setGrupo(grupoPadrao);
                }
            }
        } else if (grupo.getTipo() == grupoFichaNum) {
            List<FichaTecnica> listaFichasTecnicas = this.fichaTecnicaRepository.listarPorGrupo(grupoId);
            if (!listaFichasTecnicas.isEmpty()) {
                Grupo grupoPadrao = this.grupoRepository.buscarGrupoPadrao(grupoFichaNum, grupoIngrNome)
                    .orElseGet(this::criarGrupoPadraoFichaTecnica);

                for (FichaTecnica fichaTecnica : listaFichasTecnicas) {
                    fichaTecnica.setGrupo(grupoPadrao);
                }
            }
        }
    }

    @Transactional
    public AlterarStatusDTOResponse atualizarStatus(Integer id, AlterarStatusDTORequest dtoRequest) {
        Grupo  grupo = this.grupoRepository.buscarPorId(id)
            .orElseThrow(() -> new GrupoException("Grupo com o id: "+id+" não encontrado"));
        Integer novoStatus = dtoRequest.getStatus();

        grupo.setStatus(dtoRequest.getStatus());
        Grupo tempResponse = grupoRepository.save(grupo);
        return modelMapper.map(tempResponse, AlterarStatusDTOResponse.class);
    }

    // Incluir lógica para remanejar os itens que estejam dentro do grupo
    @Transactional
    public boolean apagar(Integer id){
        Grupo grupo = this.grupoRepository.buscarPorId(id)
                .orElseThrow(()->new GrupoException("Grupo com o id: "+id+" não encontrado"));
        this.grupoRepository.updateStatus(id, apagado);
        return true;
    }

    /**
     * Destroi objeto que tenha sido apagado
     * @param id
     * @return
     */
    @Transactional
    public boolean destroy(Integer id) {
        Grupo grupo = this.grupoRepository.findById(id)
            .orElseThrow(() -> new GrupoException("Grupo com o ID: " + id + " não encontrado"));

        if (grupo.getStatus() == apagado){
            grupoRepository.delete(grupo);
            return true;
        }
        return false;

    }
}