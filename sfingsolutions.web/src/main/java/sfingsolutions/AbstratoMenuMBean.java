package sfingsolutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponentBase;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;

import sfingsolutions.dominio.MenuEntity;

/**
 * Manager Abstato Bean responsável por criar a lista de Menu.
 * @param <T>
 * 
 * @author viniciusbrazpereira.
 */
public abstract class AbstratoMenuMBean<T extends MenuEntity> extends AbstratoCrudMBean<MenuEntity> {

    private List<MenuEntity> listaMenuFilho;

    /**
     * Método que cria o menu.
     * @param listagemMenu Lista de menu pai.
     * @param listaMenuFilhoTemp Lista de todos os menu filho.
     * @return Map<Integer, UIComponentBase>.
     */
    public Map<Integer, UIComponentBase> createMenuUIComponent(List<MenuEntity> listagemMenu,
        List<MenuEntity> listaMenuFilhoTemp) {

        this.listaMenuFilho = listaMenuFilhoTemp;

        Map<Integer, UIComponentBase> subMenuEntity = new HashMap<Integer, UIComponentBase>();

        for (MenuEntity menu : listagemMenu) {

            Integer sequencia = menu.getSequencia();
            UIComponentBase uiComponentBase = null;

            if (isMenuWithActionListener(menu)) {
                // Cria menu com actionListener
                uiComponentBase = createMenuActionListener(menu);

            } else if (isMenuWithUrl(menu)) {
                // Cria menu com URL
                uiComponentBase = createMenuUrl(menu);

            } else if (menu.getIsSubMenu()) {

                // Cria sub menu.
                Submenu submenu = createSubmenu(menu);
                addListaMenuFilho(menu, submenu);

                uiComponentBase = submenu;
            }

            subMenuEntity.put(sequencia, uiComponentBase);
        }

        return subMenuEntity;
    }

    private List<MenuEntity> getListagemMenuFilho(MenuEntity menuPai) {
        List<MenuEntity> novaLista = new ArrayList<MenuEntity>();

        for (MenuEntity menuFilhoTemp : listaMenuFilho) {
            if (menuFilhoTemp.getIdPai().intValue() == menuPai.getId().intValue()) {
                novaLista.add(menuFilhoTemp);
            }
        }

        return novaLista;
    }

    private void addListaMenuFilho(MenuEntity menu, Submenu submenu) {
        List<MenuEntity> listaMenuFilhoTemp = getListagemMenuFilho(menu);

        for (MenuEntity menuFilhoTemp : listaMenuFilhoTemp) {
            if (menuFilhoTemp.getIsSubMenu()) {
                // Cria sub menu filho.
                Submenu submenuFilho = createSubmenu(menuFilhoTemp);

                addListaMenuFilho(menuFilhoTemp, submenuFilho);
                submenu.getChildren().add(submenuFilho);

            } else {
                MenuItem item = createMenuItem(menuFilhoTemp);
                submenu.getChildren().add(item);
            }
        }
    }

    /**
     * Verifica se é menu com actionListener.
     * @param menu MenuEntity.
     * @return Boolean.
     */
    private Boolean isMenuWithActionListener(MenuEntity menu) {
        return menu.getIsSubMenu() && menu.getActionListener() != null && menu.getActionListener().length() > 0;
    }

    /**
     * Verifica se é menu com url.
     * @param menu MenuEntity.
     * @return Boolean.
     */
    private Boolean isMenuWithUrl(MenuEntity menu) {
        return menu.getIsSubMenu() && menu.getUrl() != null && menu.getUrl().length() > 0;
    }

    /**
     * Retorna o Model de menu com a sequencia de posiçoes.
     * @param subMenuEntity Map<Integer, UIComponentBase>.
     * @return DefaultMenuModel.
     */
    public DefaultMenuModel getDefaultMenuModel(Map<Integer, UIComponentBase> subMenuEntity) {
        DefaultMenuModel modelNew = new DefaultMenuModel();
        List<Integer> orderMenu = new ArrayList(subMenuEntity.keySet());

        for (int i = 0; i < orderMenu.size(); i++) {
            Object objectMenu = subMenuEntity.get(orderMenu.get(i));

            if (objectMenu instanceof Submenu) {
                modelNew.addSubmenu((Submenu) objectMenu);
            }

            if (objectMenu instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) objectMenu;
                menuItem.setAjax(false);
                modelNew.addMenuItem(menuItem);
            }
        }
        return modelNew;
    }

    private Submenu createSubmenu(MenuEntity menu) {
        Submenu submenu = new Submenu();
        submenu.setLabel(menu.getNome());

        if (menu.getIcon() != null && menu.getIcon().length() > 0) {
            submenu.setIcon(menu.getIcon());
        }

        return submenu;
    }

    private void addAction(MenuItem menuItem, String action) {
        menuItem.setAction(getFacesContext().getApplication().createMethodBinding("#{" + action + "}", null));
    }

    private MenuItem createMenuActionListener(MenuEntity menu) {
        MenuItem menuItem = new MenuItem();
        menuItem.setValue(menu.getNome());

        if (menu.getIcon().length() > 0) {
            menuItem.setIcon(menu.getIcon());
        }

        this.addAction(menuItem, menu.getActionListener());

        return menuItem;
    }

    private MenuItem createMenuUrl(MenuEntity menu) {
        MenuItem item = new MenuItem();
        item.setValue(menu.getNome());
        item.setUrl(menu.getUrl());

        if (menu.getIcon().length() > 0) {
            item.setIcon(menu.getIcon());
        }

        return item;
    }

    private MenuItem createMenuItem(MenuEntity menu) {
        MenuItem item = new MenuItem();

        item.setValue(menu.getNome());
        if (menu.getUrl() != null && menu.getUrl().length() > 0) {
            item.setUrl(menu.getUrl());
        }

        if (menu.getIcon() != null && menu.getIcon().length() > 0) {
            item.setIcon(menu.getIcon());
        }

        return item;
    }

}
