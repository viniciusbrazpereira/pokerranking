package sfingsolutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.MenuEntity;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.negocio.UsuarioBC;
import sfingsolutions.negocio.exception.BCException;
import sfingsolutions.utils.AcessoSistemaUtils;

/**
 * Manager Bean responsável pelo entidade cadastro de Menu.
 * 
 * @author Vinicius Braz.
 */
@ViewScoped
@ManagedBean(name = "cadastroMenuMBean")
public class CadastroMenuMBean extends AbstratoCrudMBean<MenuEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CadastroMenuMBean.class);

    private Integer idCadastro;
    private Integer idMenuPai;
    private String idSistema;
    private List<SelectItem> menuPai;
    private List<SelectItem> sistema;
    private List<SelectItem> sequencia;

    private List<MenuEntity> listagemFilho;
    private List<MenuEntity> listagemPai;

    private UsuarioEntity usuarioEntity;

    @EJB
    private UsuarioBC usuarioBC;

    /**
     * Construtor padrão da classe VisualMBean.
     */
    public CadastroMenuMBean() {
        super();
        LOGGER.debug("... CadastroMenuMBean()");
        setItem(new MenuEntity());

        usuarioEntity = getUsuarioEntitySession();

        DialogMensagemMBean dialogMensagemMBean = getReferenciaDialogMensagemMBean();
        dialogMensagemMBean.setAbstratoMBean(this);
        dialogMensagemMBean.setMensagemDialogExcluir("cadastroMenu.mensagemDialogExcluir");
        dialogMensagemMBean.setMensagemDialogSalvar("cadastroMenu.mensagemDialogSalvar");

        setIsCadastro(Boolean.TRUE);

        sistema = new ArrayList<SelectItem>();

        for (AcessoSistemaUtils acessoSistema : AcessoSistemaUtils.values()) {
            createSelectItem(sistema, acessoSistema.getDescricao(), acessoSistema.getSistema());
        }
    }

    private List<MenuEntity> getListaMenuPai() {
        try {
            return this.usuarioBC.buscarListaMenuPai();
        } catch (BCException e) {
            LOGGER.error("Erro ao obter a lista de menu pai da base de dados {}: ", e);
            return null;
        }
    }

    private MenuEntity buscarMenuPorNome(MenuEntity menuEntity) {
        try {
            return this.usuarioBC.buscarMenuPorNome(menuEntity);
        } catch (BCException e) {
            LOGGER.error("Erro ao obter o menu da base de dados {}: ", e);
            return null;
        }
    }

    @Override
    public List<MenuEntity> carregarListagem(int first, int pageSize, String sortField, SortOrder sortOrder,
        Map<String, String> filters) {

        try {
            listagemPai = this.usuarioBC.buscarListaMenuPaiPorUsuario(first, pageSize);
            return listagemPai;
        } catch (BCException e) {
            LOGGER.error("Erro ao obter a lista de menu pai da base de dados {}: ", e);
            return null;
        }
    }

    @Override
    public int getRowCountForDataModel() {
        try {
            return this.usuarioBC.rowListaMenuPaiPorUsuario().intValue();
        } catch (BCException e) {
            LOGGER.error("Erro ao obter a lista de menu pai da base de dados {}: ", e);
            return 0;
        }
    }

    /**
     * Zera perfil para um novo cadastro.
     */
    public void preNovo() {
        this.setItem(new MenuEntity());
        setIsCadastro(Boolean.TRUE);

        int ultimaSequencia = listagemPai.size() + 1;

        createSelectSequencia(ultimaSequencia);
        getItem().setSequencia(ultimaSequencia);
    }

    private void createSelectMenu() {
        menuPai = new ArrayList<SelectItem>();
        List<MenuEntity> listaItens = getListaMenuPai();

        for (MenuEntity menu : listaItens) {
            createSelectItem(menuPai, menu.getNome(), menu.getId());
        }
    }

    private void createSelectSequencia(Integer tamanho) {
        sequencia = new ArrayList<SelectItem>();

        for (int i = 1; i <= tamanho; i++) {
            createSelectItem(sequencia, String.valueOf(i), i);
        }
    }

    private void preEditar(MenuEntity menuEntity) {
        try {
            setItem(usuarioBC.buscarMenuPorId(menuEntity));
            setIsCadastro(Boolean.FALSE);

        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o preEditar{}: ", e);
        }
    }

    /**
     * Configuração para a edição.
     * @param menuEntity Entity.
     */
    public void preEditarPai(MenuEntity menuEntity) {
        preEditar(menuEntity);
        createSelectSequencia(listagemPai.size());
    }

    /**
     * Configuração para a edição.
     * @param menuEntity Entity.
     */
    public void preEditarFilho(MenuEntity menuEntity) {
        preEditar(menuEntity);
        createSelectSequencia(listagemFilho.size());
    }

    /**
     * handleMenuPaiChange.
     */
    public void handleMenuPaiChange() {

        try {
            if (idMenuPai != null && idMenuPai.intValue() > 0) {
                listagemFilho = usuarioBC.buscarMenuFilhoPorUsuarioEIdPai(idMenuPai);
            } else {
                listagemFilho = null;
            }
        } catch (BCException e) {
            LOGGER.error("Erro ao obter a lista de menu filho da base de dados {}: ", e);
        }
    }

    /**
     * handleSequenciaChange.
     */
    public void handleSequenciaChange() {
        try {
            int idMenuPaiTemp = getItem().getIdPai();
            if (idMenuPaiTemp > 0) {
                int quantidade = usuarioBC.rowMenuFilhoPorUsuarioEIdPai(usuarioEntity, idMenuPaiTemp).intValue();

                int ultimaSequencia = quantidade + 1;
                createSelectSequencia(ultimaSequencia);
                getItem().setSequencia(ultimaSequencia);
            } else {
                createSelectSequencia(listagemPai.size() + 1);
                getItem().setSequencia(listagemPai.size() + 1);
            }
        } catch (BCException e) {
            LOGGER.error("Erro ao obter a lista de menu filho da base de dados {}: ", e);
        }
    }

    /**
     * Valida campos para o cadastro
     * @return Boolean
     */
    public Boolean validarCamposCadastro() {
        setIsValid(Boolean.TRUE);

        MenuEntity entity = getItem();

        if (idCadastro != null) {
            entity.setId(idCadastro);
        }

        isInValidText(entity.getNome(), "cadastroMenu.error.message.obrigatorio.nome",
            ":formDialogNovoCadastro:inputNome");
        isInValidText(entity.getIcon(), "cadastroMenu.error.message.obrigatorio.icon",
            ":formDialogNovoCadastro:inputIcon");
        isInValidSelect(entity.getSistema(), "cadastroMenu.error.message.obrigatorio.sistema",
            ":formDialogNovoCadastro:selectSistema");

        if (getIsCadastro() && buscarMenuPorNome(entity) != null) {
            lancarExcecao("cadastroMenu.error.message.existente.nome");
            setIsValid(Boolean.FALSE);
        }

        confirmDialogSalvar(getIsValid());

        return getIsValid();
    }

    @Override
    public MenuEntity salvar() {
        MenuEntity menuEntity = getItem();

        if (idCadastro != null) {
            menuEntity.setId(idCadastro);
        }

        if (validarCamposCadastro()) {

            try {

                List<MenuEntity> menusTemp = usuarioBC.buscarMenuFilhoPorUsuarioEIdPai(usuarioEntity,
                    menuEntity.getIdPai());

                executar(menuEntity, menusTemp);

            } catch (BCException e) {
                LOGGER.error("Erro ao cadastrar ou atualizar o menu{}: ", e);
            }
        }

        return menuEntity;
    }

    private void executar(MenuEntity menuEntity, List<MenuEntity> menusTemp) throws BCException {
        if (getIsCadastro()) {

            for (MenuEntity entity : menusTemp) {
                if (entity.getSequencia().intValue() == menuEntity.getSequencia().intValue()) {
                    entity.setSequencia(menusTemp.size() + 1);

                    usuarioBC.atualizarMenu(entity);
                }
            }

            usuarioBC.salvarMenu(menuEntity);

        } else {
            int sequenciaTrade = 0;
            for (MenuEntity entity : menusTemp) {
                if (entity.getId().intValue() == menuEntity.getId().intValue()) {
                    sequenciaTrade = entity.getSequencia();
                }
            }

            for (MenuEntity entity : menusTemp) {
                if (entity.getSequencia().intValue() == menuEntity.getSequencia().intValue()) {
                    entity.setSequencia(sequenciaTrade);

                    usuarioBC.atualizarMenu(entity);
                }
            }

            usuarioBC.atualizarMenu(menuEntity);

            handleMenuPaiChange();
        }

        createSelectMenu();
    }

    @Override
    public MenuEntity excluir() {
        MenuEntity menuEntity = getItem();

        try {
            if (usuarioBC.listarPerfilPorMenu(menuEntity).size() > 0) {
                lancarExcecao(getMensagemDoRotulo("cadastroMenu.error.message.perfil"));
            } else {
                usuarioBC.deleteMenu(menuEntity);
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao excluir o menu{}: ", e);
            lancarExcecao(e);
        }

        return menuEntity;
    }

    /**
     * getNomeAcessoSistema.
     * @param sistema String
     * @return String.
     */
    public String getNomeAcessoSistema(String sistema) {
        return AcessoSistemaUtils.getAcessoSistema(sistema).getDescricao();
    }

    /**
     * @return the menuPai
     */
    public List<SelectItem> getMenuPai() {
        if (menuPai == null) {
            createSelectMenu();
        }
        return menuPai;
    }

    /**
     * @param menuPai the menuPai to set
     */
    public void setMenuPai(List<SelectItem> menuPai) {
        this.menuPai = menuPai;
    }

    /**
     * @return the idMenuPai
     */
    public Integer getIdMenuPai() {
        return idMenuPai;
    }

    /**
     * @param idMenuPai the idMenuPai to set
     */
    public void setIdMenuPai(Integer idMenuPai) {
        this.idMenuPai = idMenuPai;
    }

    /**
     * @return the listagemFilho
     */
    public List<MenuEntity> getListagemFilho() {
        return listagemFilho;
    }

    /**
     * @param listagemFilho the listagemFilho to set
     */
    public void setListagemFilho(List<MenuEntity> listagemFilho) {
        this.listagemFilho = listagemFilho;
    }

    /**
     * @return the sequencia
     */
    public List<SelectItem> getSequencia() {
        return sequencia;
    }

    /**
     * @param sequencia the sequencia to set
     */
    public void setSequencia(List<SelectItem> sequencia) {
        this.sequencia = sequencia;
    }

    /**
     * @return the idCadastro
     */
    public Integer getIdCadastro() {
        return idCadastro;
    }

    /**
     * @param idCadastro the idCadastro to set
     */
    public void setIdCadastro(Integer idCadastro) {
        this.idCadastro = idCadastro;
    }

    /**
     * @return the idSistema
     */
    public String getIdSistema() {
        return idSistema;
    }

    /**
     * @param idSistema the idSistema to set
     */
    public void setIdSistema(String idSistema) {
        this.idSistema = idSistema;
    }

    /**
     * @return the sistema
     */
    public List<SelectItem> getSistema() {
        return sistema;
    }

    /**
     * @param sistema the sistema to set
     */
    public void setSistema(List<SelectItem> sistema) {
        this.sistema = sistema;
    }

}
