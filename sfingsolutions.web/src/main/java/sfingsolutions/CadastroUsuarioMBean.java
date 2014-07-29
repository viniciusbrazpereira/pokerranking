package sfingsolutions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.DualListModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.EstadoUsuarioEntity;
import sfingsolutions.dominio.PerfilEntity;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.negocio.UsuarioBC;
import sfingsolutions.negocio.exception.BCException;
import sfingsolutions.utils.ObjectUtils;

/**
 * Manager Bean responsável pelo entidade cadastro de Usuário.
 * 
 * @author Vinicius Braz.
 */
@ViewScoped
@ManagedBean(name = "cadastroUsuarioMBean")
public class CadastroUsuarioMBean extends AbstratoCrudMBean<UsuarioEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CadastroUsuarioMBean.class);

    private Integer idSelectItemEstados;
    private List<SelectItem> selectItemEstados;

    private DualListModel<PerfilEntity> listModelPerfilCadastro;

    private UsuarioEntity usuarioEntitySession;
    private UsuarioEntity usuarioEntityPesquisa;

    private static final String DEFAULT_PASS = "pokerpoker";

    private static final int MINIMO_LOGIN = 5;

    private Boolean gerarSenha = Boolean.FALSE;

    @EJB
    private UsuarioBC usuarioBC;

    /**
     * Construtor padrão da classe VisualMBean.
     */
    public CadastroUsuarioMBean() {
        super();
        LOGGER.debug("... CadastroUsuarioMBean()");

        usuarioEntitySession = getUsuarioEntitySession();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setEstadoUsuario(new EstadoUsuarioEntity());
        // usuarioEntity.setAcessoSistemaEntity(new AcessoSistemaEntity());
        usuarioEntity.setUserPassword(DEFAULT_PASS);
        setItem(usuarioEntity);

        setIsCadastro(Boolean.TRUE);

        DialogMensagemMBean dialogMensagemMBean = getReferenciaDialogMensagemMBean();

        dialogMensagemMBean.setAbstratoMBean(this);
        dialogMensagemMBean.setMensagemDialogExcluir("cadastroUsuario.mensagemDialogExcluir");
        dialogMensagemMBean.setMensagemDialogSalvar("cadastroUsuario.mensagemDialogSalvar");
    }

    private Boolean isLoginCadastrado(String login) {
        try {
            return this.usuarioBC.buscarUsuarioPorLogin(login) != null;
        } catch (BCException e) {
            LOGGER.error("Erro ao obter o login da base de dados {}: ", e);
            return false;
        }
    }

    private Boolean isCpfCadastrado(String cpf) {
        try {
            return usuarioBC.buscarUsuarioPorCPF(cpf) != null;
        } catch (BCException e) {
            LOGGER.error("Erro ao obter o cpf da base de dados {}: ", e);
            return false;
        }
    }

    private List<PerfilEntity> getListaPerfil(UsuarioEntity usuarioEntity) {
        try {
            return usuarioBC.listarPerfilPorUsuario(usuarioEntity);
        } catch (BCException e) {
            LOGGER.error("Erro ao obter a lista de perfil da base de dados {}: ", e);
            return null;
        }
    }

    /**
     * createEstadoUsuario.
     */
    private void createEstadoUsuario(List<EstadoUsuarioEntity> listaEstadadoUsuario) {
        selectItemEstados = new ArrayList<SelectItem>();

        for (EstadoUsuarioEntity entity : listaEstadadoUsuario) {
            createSelectItem(selectItemEstados, entity.getDescricao(), entity.getId());
        }
    }

    @Override
    public List<UsuarioEntity> carregarListagem(int first, int pageSize, String sortField, SortOrder sortOrder,
        Map<String, String> filters) {

        try {
            return usuarioBC.listarAllUsuario(first, pageSize);
        } catch (BCException e) {
            LOGGER.error("Erro ao obter a lista da base de dados {}: ", e);
            return null;
        }
    }

    @Override
    public void populaBeanPesquisa() {
        this.usuarioEntityPesquisa = getItem();

        EstadoUsuarioEntity estadoUsuarioEntity = new EstadoUsuarioEntity();
        estadoUsuarioEntity.setId(idSelectItemEstados);

        usuarioEntityPesquisa.setEstadoUsuario(estadoUsuarioEntity);
    }

    @Override
    public int getRowCountForDataModel() {
        try {
            if (usuarioEntityPesquisa == null) {
                return usuarioBC.listarUsuario().size();
            } else {
                return usuarioBC.rowCountUsuarioPorNomeLoginEmailEstado(usuarioEntityPesquisa).intValue();
            }
        } catch (BCException e) {
            LOGGER.error("Erro ao obter a lista de menu pai da base de dados {}: ", e);
            return 0;
        }
    }

    /**
     * preEditar.
     * @param entity UsuarioEntity.
     */
    public void preEditar(UsuarioEntity entity) {
        try {
            setItem(usuarioBC.buscarUsuarioPorUsuario(entity));
            idSelectItemEstados = getItem().getEstadoUsuario().getId();

            // if(getItem().getAcessoSistemaEntity() == null) {
            // getItem().setAcessoSistemaEntity(new AcessoSistemaEntity());
            // }

            List<PerfilEntity> listaPerfil = getListaPerfil(getItem());
            List<PerfilEntity> listaPerfilAdm = getListaPerfil(usuarioEntitySession);
            listModelPerfilCadastro = createListModel(listaPerfilAdm, listaPerfil);

            setIsCadastro(Boolean.FALSE);
            setGerarSenha(Boolean.FALSE);

        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o preEditar{}: ", e);
            lancarExcecao(e.getMessage());
        }
    }

    /**
     * preNovo.
     */
    public void preNovo() {
        listModelPerfilCadastro = null;

        idSelectItemEstados = 0;

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        // usuarioEntity.setAcessoSistemaEntity(new AcessoSistemaEntity());
        usuarioEntity.setEstadoUsuario(new EstadoUsuarioEntity());
        usuarioEntity.setUserPassword(DEFAULT_PASS);

        setItem(usuarioEntity);

        setIsCadastro(Boolean.TRUE);
        setGerarSenha(Boolean.FALSE);
    }

    @Override
    public Boolean validarCamposCadastro() {
        UsuarioEntity usuarioEntityCadastro = populaBean();

        setIsValid(Boolean.TRUE);

        isInValidText(usuarioEntityCadastro.getNome(), "cadastroUsuario.error.message.obrigatorio.nome",
            ":formDialogNovoCadastro:inputNome");

        isInValidText(usuarioEntityCadastro.getUserLogin(), "cadastroUsuario.error.message.obrigatorio.login",
            ":formDialogNovoCadastro:inputUserLogin");

        isCadastradoLogin(usuarioEntityCadastro);

        //isValidCPF(usuarioEntityCadastro);

        isValidEmail(usuarioEntityCadastro);

        isInValidSelect(usuarioEntityCadastro.getEstadoUsuario().getId(),
            "cadastroUsuario.error.message.obrigatorio.estado", ":formDialogNovoCadastro:selectEstado");

        isInValidMultiSelect(usuarioEntityCadastro.getPerfis(), "cadastroUsuario.error.message.obrigatorio.perfil");

        confirmDialogSalvar(getIsValid());
        return getIsValid();
    }

    private void isValidEmail(UsuarioEntity usuarioEntityCadastro) {
        String regex = "[a-zA-Z0-9]+[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]*\\.+[a-z]{2,4}";
        if (getIsValid() && !usuarioEntityCadastro.getEmail().matches(regex)) {
            lancarExcecao("cadastroUsuario.error.message.obrigatorio.emailInvalid");
            isValidComponentRed(":formDialogNovoCadastro:inputUserEmail");
            setIsValid(Boolean.FALSE);
        }
    }

    private void isCadastradoLogin(UsuarioEntity usuarioEntityCadastro) {
        if (getIsValid() && usuarioEntityCadastro.getUserLogin().length() < MINIMO_LOGIN) {
            lancarExcecao("cadastroUsuario.error.message.obrigatorio.loginMinimo");
            isValidComponentRed(":formDialogNovoCadastro:inputUserLogin");
            setIsValid(Boolean.FALSE);
        }

        if (getIsValid() && getIsCadastro() && isLoginCadastrado(usuarioEntityCadastro.getUserLogin())) {
            lancarExcecao("cadastroUsuario.error.message.obrigatorio.loginCadastrado");
            isValidComponentRed(":formDialogNovoCadastro:inputUserLogin");
            setIsValid(Boolean.FALSE);
        }
    }

    /*private void isValidCPF(UsuarioEntity usuarioEntityCadastro) {
        isInValidText(usuarioEntityCadastro.getCpf(), "cadastroUsuario.error.message.obrigatorio.cpf",
            ":formDialogNovoCadastro:inputCpf");

        String cpf = usuarioEntityCadastro.getCpf().replace(".", "").replace("-", "");

        if (getIsValid() && !StringUtils.validarCPF(cpf)) {
            lancarExcecao("cadastroUsuario.error.message.obrigatorio.cpfInvalid");
            isValidComponentRed(":formDialogNovoCadastro:inputCpf");
            setIsValid(Boolean.FALSE);
        }

        if (getIsCadastro() && isCpfCadastrado(usuarioEntityCadastro.getCpf())) {
            lancarExcecao("cadastroUsuario.error.message.obrigatorio.cpfCadastrado");
            isValidComponentRed(":formDialogNovoCadastro:inputCpf");
            setIsValid(Boolean.FALSE);
        }
    }*/

    private void isInValidMultiSelect(List<PerfilEntity> value, String excecao) {
        if (value == null || value.size() == 0) {
            lancarExcecao(excecao);
            setIsValid(Boolean.FALSE);
        }
    }

    protected UsuarioEntity populaBean() {
        UsuarioEntity usuarioEntity = getItem();
        usuarioEntity.setNome(getItem().getNome().toUpperCase());
        usuarioEntity.setUserLogin(getItem().getUserLogin().toLowerCase());

        usuarioEntity.setPerfis(listModelPerfilCadastro.getTarget());
        usuarioEntity.setEstadoUsuario(new EstadoUsuarioEntity(idSelectItemEstados));
        
        usuarioEntity.setDataInclusao(new Date());

        return usuarioEntity;
    }

    @Override
    public UsuarioEntity salvar() {
        UsuarioEntity usuarioEntity = populaBean();

        if (getGerarSenha()) {
            usuarioEntity.setUserPassword(DEFAULT_PASS);
        }

        try {
            usuarioBC.salvarUsuario(usuarioEntity);
        } catch (BCException e) {
            LOGGER.error("Erro ao salvar o usuário na base de dados {}: ", e);
            lancarExcecao(e.getMessage());
        }

        return usuarioEntity;
    }

    @Override
    public UsuarioEntity excluir() {
        return null;
    }

    public void reset() {
        getItem().setId(0);
    }

    /**
     * @return the selectItemEstados
     */
    public List<SelectItem> getSelectItemEstados() {
        if (ObjectUtils.isNull(selectItemEstados)) {
            try {
                createEstadoUsuario(usuarioBC.listarEstado());
            } catch (BCException e) {
                LOGGER.error("Erro ao criar a lista de estados para o combo{}: ", e);
            }
        }
        return selectItemEstados;
    }

    /**
     * @param selectItemEstados the selectItemEstados to set
     */
    public void setSelectItemEstados(List<SelectItem> selectItemEstados) {
        this.selectItemEstados = selectItemEstados;
    }

    /**
     * @return the idSelectItemEstados
     */
    public Integer getIdSelectItemEstados() {
        return idSelectItemEstados;
    }

    /**
     * @param idSelectItemEstados the idSelectItemEstados to set
     */
    public void setIdSelectItemEstados(Integer idSelectItemEstados) {
        this.idSelectItemEstados = idSelectItemEstados;
    }

    /**
     * @return the listModelPerfilCadastro
     */
    public DualListModel<PerfilEntity> getListModelPerfilCadastro() {
        if (isInValidDualList(listModelPerfilCadastro)) {

            List<PerfilEntity> sourceBase = new ArrayList<PerfilEntity>();

            List<PerfilEntity> perfils = getListaPerfil(getUsuarioEntitySession());

            for (PerfilEntity perfil : perfils) {
                sourceBase.add(perfil);
            }

            listModelPerfilCadastro = createListModel(sourceBase, new ArrayList<PerfilEntity>());
        }

        return listModelPerfilCadastro;
    }

    /**
     * @param listModelPerfilCadastro the listModelPerfilCadastro to set
     */
    public void setListModelPerfilCadastro(DualListModel<PerfilEntity> listModelPerfilCadastro) {
        this.listModelPerfilCadastro = listModelPerfilCadastro;
    }

    /**
     * @return the gerarSenha
     */
    public Boolean getGerarSenha() {
        return gerarSenha;
    }

    /**
     * @param gerarSenha the gerarSenha to set
     */
    public void setGerarSenha(Boolean gerarSenha) {
        this.gerarSenha = gerarSenha;
    }

}
