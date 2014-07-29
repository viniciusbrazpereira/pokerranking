package sfingsolutions;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import sfingsolutions.constantes.SessionConstants;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.dominio.defaultobject.IDefaultObject;
import sfingsolutions.utils.ObjectUtils;

/**
 * Classe abstrata, contendo os métodos padrões de gerenciamento de tela.
 * @author viniciusbrazpereira.
 */
public abstract class AbstratoMBean implements Serializable{

    private ResourceBundle rotulo;

    /**
     * Método responsável por lançar exceção ao usuário.
     * 
     * @param e Exceção.
     */
    public void lancarExcecao(Exception e) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
    }

    /**
     * Método responsável por lançar exceção ao usuário.
     * @param mensagem Texto a ser informado.
     */
    public void lancarExcecao(String mensagem) {
        String messageRotolo = getMensagemDoRotulo(mensagem);
        if (messageRotolo == null) {
            messageRotolo = mensagem;
        }
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageRotolo, ""));
    }

    /**
     * Método responsável por lançar uma mensagem de aviso ao usuário.
     * @param mensagem Texto a ser informado.
     */
    public void lancarAviso(String mensagem) {
        String messageRotolo = getMensagemDoRotulo(mensagem);
        if (messageRotolo == null) {
            messageRotolo = mensagem;
        }
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageRotolo, ""));
    }

    /**
     * Método responsável por lançar exceção ao usuário.
     * @param mensagem Texto a ser informado.
     */
    public void lancarMessageValidator(String mensagem) {
        String messageRotolo = getMensagemDoRotulo(mensagem);
        if (messageRotolo == null) {
            messageRotolo = mensagem;
        }
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageRotolo, ""));
    }

    /**
     * Método que retorna a session.
     * @return HttpSession
     */
    public HttpSession getSession() {
        return (HttpSession) getFacesContext().getExternalContext().getSession(false);
    }

    /**
     * Método que retorna o FacesContext
     * @return FacesContext
     */
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Método que retorna o RequestContext
     * @return RequestContext
     */
    public RequestContext getRequestContext() {
        return RequestContext.getCurrentInstance();
    }

    /**
     * Método que retorna o ServletContext.
     * @return ServletContext.
     */
    public ServletContext getServletContext() {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    /**
     * redirectRequest.
     * @param requestPath String.
     * @throws IOException erro.
     */
    public void redirectRequest(String requestPath) throws IOException {
        String path = getFacesContext().getExternalContext().getRequestContextPath() + requestPath;
        getFacesContext().getExternalContext().redirect(path);
    }

    /**
     * Obtém uma mensagem do rótulo a partir da chave.
     * @param chave Chave.
     * @return Valor no rótulo.
     */
    protected String getMensagemDoRotulo(String chave) {
        try {
            if (ObjectUtils.isNull(rotulo)) {
                FacesContext context = FacesContext.getCurrentInstance();
                rotulo = context.getApplication().getResourceBundle(context, SessionConstants.ROTULO);
            }
            return rotulo.getString(chave);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que cria um SelectItem e adiciona a lista.
     * @param listSelectItem
     * @param label
     * @param value
     */
    protected void createSelectItem(List<SelectItem> listSelectItem, String label, Integer value) {
        SelectItem selectItem = new SelectItem();
        selectItem.setLabel(label);
        selectItem.setValue(value);
        listSelectItem.add(selectItem);
    }

    /**
     * Método que cria um SelectItem e adiciona a lista.
     * @param listSelectItem
     * @param label
     * @param value
     */
    protected void createSelectItem(List<SelectItem> listSelectItem, String label, String value) {
        SelectItem selectItem = new SelectItem();
        selectItem.setLabel(label);
        selectItem.setValue(value);
        listSelectItem.add(selectItem);
    }

    /**
     * Retorna o Usuario logado da session.
     * 
     * @return UsuarioEntity
     */
    protected UsuarioEntity getUsuarioEntitySession() {
        return (UsuarioEntity) getSession().getAttribute(SessionConstants.USUARIO);
    }

    /**
     * setUsuarioEntitySession.
     * @param usuarioEntity Entity.
     */
    protected void setUsuarioEntitySession(UsuarioEntity usuarioEntity) {
        getSession().setAttribute(SessionConstants.USUARIO, usuarioEntity);
    }

    /**
     * Retorna as mensagens de erros lançadas pela aplicação.
     * @return Lista de mensagens de erros.
     */
    public List<FacesMessage> getMessageList() {
        return new ArrayList<FacesMessage>(getFacesContext().getMessageList());
    }

    /**
     * @return dialogMensagemMBean .
     */
    public DialogMensagemMBean getReferenciaDialogMensagemMBean() {
        return getFacesContext().getApplication().evaluateExpressionGet(getFacesContext(), "#{dialogMensagemMBean}",
            DialogMensagemMBean.class);
    }

    /**
     * Cria a lista Dual para o PickList.
     * @param sourceBase Objectos disponíveis para atribuição.
     * @param targetBase Objetos já atribuídos.
     * @param <Q> tipo genérico.
     * @return DualListModel<T>
     */
    public <Q extends IDefaultObject<Integer>> DualListModel<Q> createListModel(List<Q> sourceBase, List<Q> targetBase) {
        List<Q> source = new ArrayList<Q>();
        List<Q> target = new ArrayList<Q>();
        for (Q entity : sourceBase) {
            if (obterById(targetBase, entity.getId())) {
                target.add(entity);
            } else {
                source.add(entity);
            }
        }
        return new DualListModel<Q>(source, target);
    }

    private <Q extends IDefaultObject<Integer>> Boolean obterById(List<Q> list, Integer id) {
        if (ObjectUtils.isNull(list)) {
            return false;
        }

        for (Q entity : list) {
            if (entity.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obter o objeto atraves do id informado.
     * @param list EntityList.
     * @param id integer.
     * @param <Q> tipo parametrizado.
     * @return IDefaultObject.
     */
    public <Q extends IDefaultObject<Integer>> Q obterEntityById(List<Q> list, Integer id) {
        if (ObjectUtils.isNull(list)) {
            return null;
        }

        for (Q entity : list) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Retorna a lista adicionada para um evento do pickList.
     * @param unidadesTransf List<Q>.
     * @param unidadesTarget List<Q>.
     * @param unidadesSource List<Q>.
     * @param <Q> Tipo parametrizado.
     * @return List<Q>.
     */
    public <Q extends IDefaultObject<Integer>> List<Q> obterListaAdicionada(List<Q> unidadesTransf,
        List<Q> unidadesTarget, List<Q> unidadesSource) {

        List<Q> unidadeConsulta = new ArrayList<Q>();
        List<Q> unidadeRemove = new ArrayList<Q>();

        for (Q unidade : unidadesTransf) {
            if (unidadesTarget.contains(unidade)) {
                unidadeRemove.add(unidade);
            }

            if (unidadesSource.contains(unidade)) {
                unidadeConsulta.add(unidade);
            }
        }

        for (Q unidade : unidadesTarget) {
            if (!unidadeConsulta.contains(unidade)) {
                unidadeConsulta.add(unidade);
            }
        }

        for (Q unidade : unidadeRemove) {
            if (unidadeConsulta.contains(unidade)) {
                unidadeConsulta.remove(unidade);
            }
        }

        return unidadeConsulta;
    }

    /**
     * Verifica se a lista é valida e está populada com 1 ou mais valor.
     * @param list DualListModel<?>.
     * @return Boolean.
     */
    public boolean isInValidDualList(DualListModel<?> list) {
        return list == null || (list.getSource().size() == 0 && list.getTarget().size() == 0);
    }

    /**
     * Verifica se a lista é valida e está populada com 1 ou mais valor.
     * @param list DualListModel<?>.
     * @return Boolean.
     */
    public boolean isInValidList(List<?> list) {
        return list == null || list.size() == 0;
    }

    protected void isValidComponentRed(String formNameComponent) {
        UIInput input = (UIInput) findComponent(formNameComponent, getFacesContext().getViewRoot());
        if (input == null) {
            input = (UIInput) getFacesContext().getViewRoot().findComponent(formNameComponent);
        }
        if (input != null) {
            input.setValid(false);
        }
    }

    private UIComponent findComponent(String id, UIComponent where) {
        if (where == null) {
            return null;
        } else if (where.getId().equals(id)) {
            return where;
        } else {
            List<UIComponent> childrenList = where.getChildren();
            if (childrenList == null || childrenList.isEmpty()) {
                return null;
            }
            for (UIComponent child : childrenList) {
                UIComponent result = null;
                result = findComponent(id, child);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }
    }

    /**
     * Obtém o escopo Flash.
     * @return Escopo flash.
     */
    public Flash getFlashScope() {
        return (FacesContext.getCurrentInstance().getExternalContext().getFlash());
    }
    
    /**
     * Obtém os parâmetros do contexto.
     * @return Parâmetros do contexto.
     */
    public Map<String, String> getParameterMap(){
        return getFacesContext().getExternalContext().getRequestParameterMap();
    }
    
}
