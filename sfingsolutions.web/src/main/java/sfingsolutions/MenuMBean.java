package sfingsolutions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponentBase;

import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.constantes.SessionConstants;
import sfingsolutions.dominio.MenuEntity;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.negocio.UsuarioBC;
import sfingsolutions.negocio.exception.BCException;

/**
 * Manager Bean responsável pelo entidade MenuEntity.
 * 
 * @author Viniciusbrazpereira.
 */
@ViewScoped
@ManagedBean(name = "menuMBean")
public class MenuMBean extends AbstratoMenuMBean<MenuEntity> {

    private static final Logger logger = LoggerFactory.getLogger(MenuMBean.class);

    @EJB
    private UsuarioBC usuarioBC;

    private MenuModel model;
    private Map<String, MenuModel> modelMenu;

    private int sizeList;

    private UsuarioEntity usuarioEntity;
    
    private Boolean isMenu;

    /**
     * Construtor padrão da classe VisualMBean.
     */
    public MenuMBean() {
        super();
        logger.debug("... MenuMBean()");
        usuarioEntity = getUsuarioEntitySession();
    }

    private List<MenuEntity> getListagemMenuPai() throws BCException {
        return usuarioBC.buscarListaMenuPaiPorUsuario(usuarioEntity);
    }

    private List<MenuEntity> getListagemMenuFilho() throws BCException {
        return usuarioBC.listarMenuFilhoPorUsuario(usuarioEntity);
    }

    /**
     * Método que cria o menu.
     * @return DefaultMenuModel.
     */
    public DefaultMenuModel createAllMenu() {

        try {
            List<MenuEntity> listagemMenu = getListagemMenuPai();
            List<MenuEntity> listagemMenuFilho = getListagemMenuFilho();
            
            Map<Integer, UIComponentBase> subMenuEntity = createMenuUIComponent(listagemMenu, listagemMenuFilho);

            return getDefaultMenuModel(subMenuEntity);
        } catch (BCException e) {
            logger.error("Erro ao obter o menu da base de dados {}: ", e);
            return null;
        }
    }

    /**
     * @return the model
     * 
     *         public MenuModel getModel() { HttpSession session = getSession();
     *         if (this.model == null) { this.model = (MenuModel)
     *         session.getAttribute(SessionConstants.MODEL_MENU);
     * 
     *         if (this.model == null) { this.model = createAllMenu();
     *         session.setAttribute(SessionConstants.MODEL_MENU, this.model); }
     * 
     *         } return model; }
     */

    /**
     * @return the model
     */
    public MenuModel getModel() {
        String chave = "A";
        this.modelMenu = (Map<String, MenuModel>) getSession().getAttribute(SessionConstants.MODEL_MENU);

        if (this.modelMenu == null) {
            modelMenu = new HashMap<String, MenuModel>();
            modelMenu.put(chave, createAllMenu());
            getSession().setAttribute(SessionConstants.MODEL_MENU, this.modelMenu);
        }

        model = modelMenu.get(chave);
        if (model == null) {
            model = createAllMenu();
            modelMenu.put(chave, model);
            getSession().setAttribute(SessionConstants.MODEL_MENU, this.modelMenu);
        }
        
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MenuModel model) {
        this.model = model;
    }

    @Override
    public MenuEntity salvar() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuEntity excluir() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MenuEntity> carregarListagem(int first, int pageSize, String sortField, SortOrder sortOrder,
        Map<String, String> filters) {
        try {

            List<MenuEntity> listaMenu = usuarioBC.listarMenu();
            sizeList = listaMenu.size();
            return listaMenu;
        } catch (BCException e) {
            logger.error("Erro ao criar a lista de menu{}: ", e);
        }
        return null;
    }

    @Override
    public int getRowCountForDataModel() {
        try {
            return usuarioBC.rowCountMenu().intValue();
        } catch (BCException e) {
            logger.error("Erro ao criar a lista de menu{}: ", e);
        }
        return 0;
    }

    /**
     * @return the sizeList
     */
    public int getSizeList() {
        return sizeList;
    }

    /**
     * @param sizeList the sizeList to set
     */
    public void setSizeList(int sizeList) {
        this.sizeList = sizeList;
    }

    /**
     * @return the isMenu
     */
    public Boolean getIsMenu() {
        if(isMenu == null) {
            try {
                isMenu = getListagemMenuPai().size() > 0;
            } catch (BCException e) {
                logger.error("Erro ao obter o menu da base de dados {}: ", e);
            }
        }
        
        return isMenu;
    }

    /**
     * @param isMenu the isMenu to set
     */
    public void setIsMenu(Boolean isMenu) {
        this.isMenu = isMenu;
    }
}
