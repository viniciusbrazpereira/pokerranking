package sfingsolutions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import sfingsolutions.datamodel.GenericLazyDataModel;
import sfingsolutions.dominio.defaultobject.IDefaultObject;
import sfingsolutions.utils.DatetimeUtils;
import sfingsolutions.utils.ObjectUtils;

/**
 * Classe abstrata, contendo os métodos padrões de gerenciamento de tela.
 * 
 * @param <T> Entidade que a tela manipula.
 * @author viniciusbrazpereira.
 */
public abstract class AbstratoCrudMBean<T extends IDefaultObject<?>> extends AbstratoMBean {

    private GenericLazyDataModel<T> listagem;
    private T item;

    private Boolean isCadastro;
    private Boolean isValid;

    protected static final String CONFIRM_DIALOG_SALVAR_SHOW = "dlgConfirmDialogSalvar.show()";

    /**
     * Armazena um item.
     * 
     * @return T Entidade.
     */
    public abstract T salvar();

    /**
     * Exclui um item.
     * 
     * @return T Entidade.
     */
    public abstract T excluir();

    /**
     * @return the listagem
     */
    public GenericLazyDataModel<T> getListagem() {
        if (ObjectUtils.isNull(listagem)) {
            this.listagem = new GenericLazyDataModel<T>(this);
        }
        return listagem;
    }

    /**
     * @param listagem the listagem to set
     */
    public void setListagem(GenericLazyDataModel<T> listagem) {
        this.listagem = listagem;
    }

    /**
     * @return the item
     */
    public T getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(T item) {
        this.item = item;
    }

    /**
     * Retorna a lista para ser exibida no DataModel.
     * @param first Primeiro index.
     * @param pageSize Tamanho de Paginação
     * @param sortField SortFiel.
     * @param sortOrder Sortorder.
     * @param filters Filters.
     * @return List<T>
     */
    public List<T> carregarListagem(int first, int pageSize, String sortField, SortOrder sortOrder,
        Map<String, String> filters) {
        return null;
    }

    /**
     * Retorna a quantidade da lista para ser exibida no DataModel.
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @param filters
     * @return List<T>
     */
    public int getRowCountForDataModel() {
        return 0;
    }

    /**
     * Método que faz a pesquisa.
     */
    public void pesquisar() {
        if (validarCamposPesquisa()) {
            populaBeanPesquisa();
        }
    }

    /**
     * Valida os campos para fazer a pesquisa.
     * @return Boolean
     */
    public Boolean validarCamposPesquisa() {
        return true;
    }

    /**
     * Valida campos para o cadastro
     * @return Boolean
     */
    public Boolean validarCamposCadastro() {
        confirmDialogSalvar(true);
        return true;
    }

    /**
     * Verifica e exibe o dialog confirm salvar.
     * @param isConfirm Boolean.
     */
    public void confirmDialogSalvar(Boolean isConfirm) {
        if (isConfirm) {
            getRequestContext().execute(CONFIRM_DIALOG_SALVAR_SHOW);
        }
    }

    /**
     * Método para popular o objeto bean para a pesquisa.
     */
    public void populaBeanPesquisa() {

    }

    /**
     * Cancelar
     */
    public void cancelar() {

    }

    /**
     * Define o parametro como objeto da classe(item).
     * @param t Entity.
     * @return T
     */
    public T preDialogConfirm(T t) {
        setItem(t);
        return getItem();
    }

    /**
     * @return the isCadastro
     */
    public Boolean getIsCadastro() {
        return isCadastro;
    }

    /**
     * @param isCadastro the isCadastro to set
     */
    public void setIsCadastro(Boolean isCadastro) {
        this.isCadastro = isCadastro;
    }

    /**
     * Método que valida select combo.
     * @param value Integer.
     * @param formNameComponent String.
     * @param excecao String.
     */
    protected void isInValidSelect(Integer value, String excecao, String formNameComponent) {
        if (value == null || value == 0) {
            lancarExcecao(excecao);
            isValidComponentRed(formNameComponent);
            isValid = Boolean.FALSE;
        }
    }
    
    /**
     * Método que valida select combo.
     * @param value String.
     * @param formNameComponent String.
     * @param excecao String.
     */
    protected void isInValidSelect(String value, String excecao, String formNameComponent) {
        if (value == null || value.length() == 0) {
            lancarExcecao(excecao);
            isValidComponentRed(formNameComponent);
            isValid = Boolean.FALSE;
        }
    }

    /**
     * Método que valida multi select combo.
     * @param value Integer [].
     * @param formNameComponent String.
     * @param excecao String.
     */
    protected void isInValidMultiSelect(Integer[] value, String excecao, String formNameComponent) {
        if (value == null || value.length == 0) {
            lancarExcecao(excecao);
            isValidComponentRed(formNameComponent);
            isValid = Boolean.FALSE;
        }
    }

    /**
     * Método que valida campo texto.
     * @param value String.
     * @param formNameComponent String.
     * @param excecao String.
     */
    protected void isInValidText(String value, String excecao, String formNameComponent) {
        if (value == null || value.length() == 0) {
            lancarExcecao(excecao);
            isValidComponentRed(formNameComponent);
            isValid = Boolean.FALSE;
        }
    }

    /**
     * Verifica se primeira data é menor ou iqual à segunda.
     * @param dataInicial Date.
     * @param dataFinal Date.
     * @param formNameComponent String.
     * @param excecao String.
     */
    protected void isInValidFirstDateLess(Date dataInicial, Date dataFinal, String excecao, String formNameComponent) {
        if (!DatetimeUtils.isFirstDataMoreOrEqual(dataFinal, dataInicial)) {
            lancarExcecao(excecao);
            isValidComponentRed(formNameComponent);
            isValid = Boolean.FALSE;
        }
    }

    /**
     * Método que valida campo integer.
     * @param value Integer.
     * @param formNameComponent String.
     * @param excecao String.
     */
    protected void isInValidInteger(Integer value, String excecao, String formNameComponent) {
        if (value == null || value == 0) {
            lancarExcecao(excecao);
            isValidComponentRed(formNameComponent);
            isValid = Boolean.FALSE;
        }
    }
    
    /**
     * Método que valida campo Double.
     * @param value Integer.
     * @param formNameComponent String.
     * @param excecao String.
     */
    protected void isInValidDouble(Double value, String excecao, String formNameComponent) {
        if (value == null || value == 0) {
            lancarExcecao(excecao);
            isValidComponentRed(formNameComponent);
            isValid = Boolean.FALSE;
        }
    }

    /**
     * Verifica se a data é nula.
     * @param data Date.
     * @param formNameComponent String.
     * @param excecao Mensagem.
     */
    protected void isInValidDate(Date data, String excecao, String formNameComponent) {
        if (data == null) {
            lancarExcecao(excecao);
            isValidComponentRed(formNameComponent);
            isValid = Boolean.FALSE;
        }
    }

    /**
     * Lança aviso de erro e sinaliza componentes.
     * @param excecao Mensagem de erro.
     * @param componentes Lista de componentes para sinalização.
     */
    protected void lancarErroComponentes(String excecao, String... componentes) {
        lancarExcecao(excecao);
        isValid = Boolean.FALSE;
        for (String componente : componentes) {
            isValidComponentRed(componente);
        }
    }

    /**
     * @return the isValid
     */
    public Boolean getIsValid() {
        return isValid;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * setDialogMensagemMBean.
     * @param abstratoMBean AbstratoMBean.
     * @param mensagemDialogSalvar String.
     * @param mensagemDialogExcluir String.
     */
    protected void setDialogMensagemMBean(AbstratoMBean abstratoMBean, String mensagemDialogSalvar,
        String mensagemDialogExcluir) {
        DialogMensagemMBean dialogMensagemMBean = getReferenciaDialogMensagemMBean();
        dialogMensagemMBean.setAbstratoMBean(this);
        dialogMensagemMBean.setMensagemDialogExcluir(mensagemDialogExcluir);
        dialogMensagemMBean.setMensagemDialogSalvar(mensagemDialogSalvar);
    }

}
