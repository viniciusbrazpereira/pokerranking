package sfingsolutions.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import sfingsolutions.dominio.defaultobject.DefaultObject;

/**
 * Classe que representa a entidade Menu.
 * 
 * @author Vinicius Braz.
 */

@Entity
@Table(name = "menu")
public class MenuEntity extends DefaultObject<Integer> {

    private static final long serialVersionUID = -9144567844772726843L;

    @Id
    @Column(name = "codmenu")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_menu", nullable = false, length = 50)
    private String nome;

    @Column(name = "actionlistener", length = 50)
    private String actionListener;

    @Column(name = "icon", length = 30)
    private String icon;

    @Column(name = "codmenupai")
    private Integer idPai;

    @Column(name = "url", nullable = true, length = 50)
    private String url;

    @Column(name = "sequencia")
    private Integer sequencia;

    @Column(name = "submenu", columnDefinition = "bit")
    private Boolean isSubMenu;

    @Column(name = "sistema", length = 1)
    private String sistema;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the idPai
     */
    public Integer getIdPai() {
        return idPai;
    }

    /**
     * @param idPai the idPai to set
     */
    public void setIdPai(Integer idPai) {
        this.idPai = idPai;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the actionListener
     */
    public String getActionListener() {
        return actionListener;
    }

    /**
     * @param actionListener the actionListener to set
     */
    public void setActionListener(String actionListener) {
        this.actionListener = actionListener;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @return the sequencia
     */
    public Integer getSequencia() {
        return sequencia;
    }

    /**
     * @param sequencia the sequencia to set
     */
    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the isSubMenu
     */
    public Boolean getIsSubMenu() {
        return isSubMenu;
    }

    /**
     * @param isSubMenu the isSubMenu to set
     */
    public void setIsSubMenu(Boolean isSubMenu) {
        this.isSubMenu = isSubMenu;
    }

    /**
     * @return the sistema
     */
    public String getSistema() {
        return sistema;
    }

    /**
     * @param sistema the sistema to set
     */
    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MenuEntity other = (MenuEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

}
