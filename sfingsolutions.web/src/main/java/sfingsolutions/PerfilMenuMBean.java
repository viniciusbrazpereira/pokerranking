package sfingsolutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.SortOrder;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.MenuEntity;
import sfingsolutions.dominio.PerfilEntity;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.negocio.UsuarioBC;
import sfingsolutions.negocio.exception.BCException;
import sfingsolutions.utils.ObjectUtils;

/**
 * Manager Bean responsável pelo Perfil Menu.
 * 
 * @author Vinicius Braz.
 */
@ManagedBean(name = "perfilMenuMBean")
@ViewScoped
public class PerfilMenuMBean extends AbstratoCrudMBean<PerfilEntity> {

    private static final Logger logger = LoggerFactory.getLogger(PerfilMenuMBean.class);

    @EJB
    private UsuarioBC usuarioBC;

    private TreeNode root;

    private TreeNode[] selectedNodes;

    private Map<Integer, TreeNode> menuPerfilTree;

    private PerfilEntity perfilEntityPresquisa = null;

    private Integer idPerfil;
    private List<SelectItem> selectPerfil;

    private List<MenuEntity> listMenu;

    /**
     * Construtor padrão da classe PerfilMenuMBean.
     */
    public PerfilMenuMBean() {
        super();
        logger.debug("... PerfilMenuMBean()");
        setItem(new PerfilEntity());

        DialogMensagemMBean dialogMensagemMBean = getReferenciaDialogMensagemMBean();
        dialogMensagemMBean.setAbstratoMBean(this);
        dialogMensagemMBean.setMensagemDialogExcluir("perfil.mensagemDialogExcluir");
        dialogMensagemMBean.setMensagemDialogSalvar("perfil.mensagemDialogSalvar");
    }

    /**
     * Método que cria uma arvore com os menus, para selecionar a permissão.
     */
    public void createSelectedTree() {
        this.menuPerfilTree = new HashMap<Integer, TreeNode>();
        List<MenuEntity> listMenuPerfil = getListMenuEntity();
        this.root = new DefaultTreeNode("Root", null);

        for (MenuEntity menuPerfil : listMenuPerfil) {
            TreeNode node = null;

            // Adiciona menu com actionListener
            if (menuPerfil.getIdPai() == 0) {
                node = new DefaultTreeNode(menuPerfil.getNome(), root);
                menuPerfilTree.put(menuPerfil.getId(), node);

            } else {
                TreeNode nodePai = menuPerfilTree.get(menuPerfil.getIdPai());
                node = new DefaultTreeNode(menuPerfil.getNome(), nodePai);
                menuPerfilTree.put(menuPerfil.getId(), node);
            }
        }
    }

    /**
     * Método que cria uma arvore com os menus, para selecionar a permissão. Já
     * cria com itens selecionados a partir da lista passada.
     * 
     * @param listMenuPerfil to listMenuPerfil to set
     */
    public void createSelectedTree(List<MenuEntity> listMenuPerfil) {
        createSelectedTree();
        for (MenuEntity menuPerfil : listMenuPerfil) {
            TreeNode node = this.menuPerfilTree.get(menuPerfil.getId());
            node.setSelected(true);
        }
    }

    /**
     * @return the selectedNodes
     */
    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    /**
     * @param selectedNodes the selectedNodes to set
     */
    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        if (this.root == null) {
            createSelectedTree();
        }
        return root;
    }

    /**
     * Retorna a lista de menu da base de dados.
     * 
     * @return List<MenuEntity>
     */
    @SuppressWarnings("unchecked")
    private List<MenuEntity> getListMenuEntity() {
        try {
            if (listMenu == null || listMenu.size() == 0) {
                listMenu = this.usuarioBC.listarMenu();
            }

        } catch (BCException e) {
            logger.error("Erro ao obter o menu da base de dados {}: ", e);
        }

        return listMenu;
    }

    @Override
    public PerfilEntity salvar() {
        List<MenuEntity> listMenuSelecionado = getListMenuSelecionado();
        PerfilEntity perfilEntity = getItem();
        perfilEntity.setListaMenu(listMenuSelecionado);
        perfilEntity.setNome(perfilEntity.getNome().toUpperCase());

        try {
            if (getIsCadastro()) {
                usuarioBC.salvarPerfil(perfilEntity);

                PerfilEntity perfilEntityNovo = new PerfilEntity();
                perfilEntityNovo.setId(idPerfil);

                for (UsuarioEntity usuario : usuarioBC.listarUsuarioPorPerfil(perfilEntityNovo)) {
                    UsuarioEntity user = usuarioBC.buscarUsuarioPorUsuario(usuario);
                    user.setPerfis(usuarioBC.listarPerfilPorUsuario(user));
                    user.getPerfis().add(perfilEntity);
                    usuarioBC.atualizarUsuario(user);
                }

            } else {
                usuarioBC.atualizarPerfil(perfilEntity);
            }
        } catch (BCException e) {
            logger.error("Erro ao cadastrar ou atualizar o perfil{}: ", e);
        }

        return perfilEntity;
    }

    /**
     * Retorna a lista de menu selecionado.
     * @return List<MenuEntity>
     */
    private List<MenuEntity> getListMenuSelecionado() {
        List<MenuEntity> listMenuSelecionado = new ArrayList<MenuEntity>();
        if (selectedNodes != null && selectedNodes.length > 0) {

            @SuppressWarnings("unchecked")
            List<MenuEntity> listMenuSession = getListMenuEntity();

            for (TreeNode node : selectedNodes) {
                String nomeMenuSelecionado = node.getData().toString();
                for (MenuEntity menu : listMenuSession) {
                    if (menu.getNome().equals(nomeMenuSelecionado)) {
                        if (!listMenuSelecionado.contains(menu)) {
                            listMenuSelecionado.add(menu);

                            MenuEntity meuPai = encontreMeuPai(menu.getIdPai(), listMenuSession);
                            if (ObjectUtils.isReferencia(meuPai) && !listMenuSelecionado.contains(meuPai)) {
                                listMenuSelecionado.add(meuPai);
                            }
                        }
                    }
                }
            }
        }
        return listMenuSelecionado;
    }

    private MenuEntity encontreMeuPai(Integer id, List<MenuEntity> listMenuSession) {
        for (MenuEntity menu : listMenuSession) {
            if (menu.getId().intValue() == id.intValue()) {
                return menu;
            }
        }
        return null;
    }

    @Override
    public PerfilEntity excluir() {
        PerfilEntity perfilEntity = getItem();
        try {
            if (usuarioBC.listarUsuarioPorPerfil(perfilEntity).size() == 1) {
                usuarioBC.deletePerfil(perfilEntity);
            } else {
                lancarExcecao("perfil.excluir.nosucesso");
            }
        } catch (BCException e) {
            logger.error("Erro ao excluir o perfil{}: ", e);
        }
        return perfilEntity;
    }

    /**
     * Define o perfil do parametro como o perfil da classe(item).
     * @param perfilEntity Perfil.
     * @return PerfilEntity
     */
    public PerfilEntity preDialogConfirm(PerfilEntity perfilEntity) {
        setItem(perfilEntity);
        return getItem();
    }

    @Override
    public List<PerfilEntity> carregarListagem(int first, int pageSize, String sortField, SortOrder sortOrder,
        Map<String, String> filters) {
        try {
            return usuarioBC.listarTodosPerfil(first, pageSize);
        } catch (BCException e) {
            logger.error("Erro ao criar a lista de perfil{}: ", e);
        }
        return null;
    }

    @Override
    public int getRowCountForDataModel() {
        try {
            if (ObjectUtils.isNull(perfilEntityPresquisa)) {
                return usuarioBC.rowlistarTodosPerfil().intValue();
            } else {
                return usuarioBC.rowCountPerfilPorUsuario(perfilEntityPresquisa).intValue();
            }
        } catch (BCException e) {
            logger.error("Erro ao retorna a quantidade de perfil{}: ", e);
        }
        return 0;
    }

    @Override
    public void populaBeanPesquisa() {
        perfilEntityPresquisa = new PerfilEntity();
        perfilEntityPresquisa.setNome(getItem().getNome());
    }

    /**
     * Zera perfil para um novo cadastro.
     */
    public void preNovo() {
        this.setItem(new PerfilEntity());
        createSelectedTree();
        setIsCadastro(Boolean.TRUE);
    }

    /**
     * Configuração do Perfil para a edição.
     * @param perfilEntity Perfil
     */
    public void preEditar(PerfilEntity perfilEntity) {
        try {
            PerfilEntity perfilEntityBase = usuarioBC.buscarPerfilPorPerfilId(perfilEntity);
            List<MenuEntity> listMenu = usuarioBC.listarMenuPorPerfil(perfilEntity);
            if (ObjectUtils.isNull(listMenu)) {
                createSelectedTree();
            } else {
                createSelectedTree(listMenu);
            }
            setIsCadastro(Boolean.FALSE);
            perfilEntityBase.setListaMenu(listMenu);
            setItem(perfilEntityBase);
        } catch (BCException e) {
            logger.error("Erro ao carregar o preEditar{}: ", e);
        }
    }

    /**
     * Cancelar.
     */
    public void cancelar() {

    }

    /**
     * Valida campos para o cadastro
     * @return Boolean
     */
    public Boolean validarCamposCadastro() {
        setIsValid(Boolean.TRUE);

        PerfilEntity perfilEntity = getItem();

        isInValidText(perfilEntity.getNome(), "perfil.error.message.obrigatorio.perfil",
            ":formDialogNovoCadastro:inputNome");

        if (getIsCadastro()) {
            isInValidInteger(idPerfil, "perfil.error.message.obrigatorio.perfil.usuario",
                ":formDialogNovoCadastro:selectPerfils");
        }

        confirmDialogSalvar(getIsValid());
        return getIsValid();
    }

    private List<PerfilEntity> getPerfils() {
        List<PerfilEntity> perfils = null;

        try {
            UsuarioEntity usuarioEntity = getUsuarioEntitySession();
            perfils = usuarioBC.listarPerfilPorUsuario(usuarioEntity);
        } catch (BCException e) {
            logger.error("Erro ao criar a lista de perfil{}: ", e);

        }
        return perfils;
    }

    /**
     * @return the selectPerfil
     */
    public List<SelectItem> getSelectPerfil() {
        if (ObjectUtils.isNull(selectPerfil)) {
            selectPerfil = new ArrayList<SelectItem>();
            for (PerfilEntity perfil : getPerfils()) {
                createSelectItem(selectPerfil, perfil.getNome(), perfil.getId());
            }
        }
        return selectPerfil;
    }

    /**
     * @param selectPerfil the selectPerfil to set
     */
    public void setSelectPerfil(List<SelectItem> selectPerfil) {
        this.selectPerfil = selectPerfil;
    }

    /**
     * @return the idPerfil
     */
    public Integer getIdPerfil() {
        return idPerfil;
    }

    /**
     * @param idPerfil the idPerfil to set
     */
    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

}
