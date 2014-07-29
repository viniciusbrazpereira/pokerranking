package sfingsolutions.datamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import sfingsolutions.AbstratoCrudMBean;
import sfingsolutions.dominio.defaultobject.IDefaultObject;
import sfingsolutions.utils.ObjectUtils;

/**
 * Classe responsável por fazer a paginação de forma preguiçosa dos dados na
 * listagem.
 * @param <T> Entidade que a tela manipula.
 * @author Eduardo Galego.
 */
public class GenericLazyDataModel<T extends IDefaultObject<?>> extends LazyDataModel<T> implements 
    SelectableDataModel<T> {

    private static final long serialVersionUID = -802457118673976470L;

    private AbstratoCrudMBean<T> mbean;
    private int rows;
    private String rowsPerPageTemplate;
    private String paginatorPosition;
    private String paginatorTemplate;
    private T selectedItem;

    /**
     * Construtor padrão da classe GenericLazyDataModel (privado).
     */
    private GenericLazyDataModel() {
        super();
        this.rowsPerPageTemplate = "10,20,50";
    }

    /**
     * Construtor alternativo, passando o manager bean..
     * @param managerBean manager bean.
     */
    public GenericLazyDataModel(AbstratoCrudMBean<T> managerBean) {
        this();
        this.mbean = managerBean;
        this.rows = Integer.valueOf("10");
    }

    @Override
    public Object getRowKey(T item) {
        return item.getId();
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<T> list = mbean.carregarListagem(first, pageSize, sortField, sortOrder, filters);
        if (ObjectUtils.isNull(list)) {
            setRowCount(0);
        } else {
            setRowCount(mbean.getRowCountForDataModel());           
        }
        return list;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return the rowsPerPageTemplate
     */
    public String getRowsPerPageTemplate() {
        return this.rowsPerPageTemplate;
    }

    /**
     * @param rowsPerPageTemplate the rowsPerPageTemplate to set
     */
    public void setRowsPerPageTemplate(String rowsPerPageTemplate) {
        this.rowsPerPageTemplate = rowsPerPageTemplate;
    }

    /**
     * @return the paginatorPosition
     */
    public String getPaginatorPosition() {
        return "bottom";
    }

    /**
     * @param paginatorPosition the paginatorPosition to set
     */
    public void setPaginatorPosition(String paginatorPosition) {
        this.paginatorPosition = paginatorPosition;
    }

    /**
     * @return the paginatorTemplate
     */
    public String getPaginatorTemplate() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{CurrentPageReport} {FirstPageLink} {PreviousPageLink} ");
        stringBuffer.append("{PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}");
        return stringBuffer.toString();
    }

    /**
     * @param paginatorTemplate the paginatorTemplate to set
     */
    public void setPaginatorTemplate(String paginatorTemplate) {
        this.paginatorTemplate = paginatorTemplate;
    }

    /**
     * Index de colunas.
     * @param rowIndex Index de colunas.
     */
    public void setRowIndex(int rowIndex) {
        /*
         * The following is in ancestor (LazyDataModel): this.rowIndex =
         * rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
         */
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    /**
     * getRowData.
     * @param rowKey String.
     * @return T entity.
     */
    public T getRowData(String rowKey) {
    	IDefaultObject domainObject = (IDefaultObject) mbean.getItem();
        domainObject.setId(Integer.valueOf(rowKey));
        return (T) domainObject;
    }

    /**
     * @return the selectedItem
     */
    public T getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
    }
}
