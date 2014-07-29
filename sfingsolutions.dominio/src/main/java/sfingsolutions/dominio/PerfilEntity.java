package sfingsolutions.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import sfingsolutions.dominio.defaultobject.DefaultObject;

/**
 * Classe que representa a entidade Perfil novo.
 * 
 * @author Vinicius Braz.
 */

@Entity
@Table(name = "perfil")
public class PerfilEntity extends DefaultObject<Integer> {

    private static final long serialVersionUID = -7200455421613816422L;

    @Id
    @Column(name = "codperfil")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_perfil", nullable = false, length = 50)
    private String nome;

    @Column(name = "restricao", columnDefinition = "varchar")
    private Boolean restricao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "perfilmenu", joinColumns = { 
            @JoinColumn(name = "codperfil", nullable = false, updatable = false) }, 
            inverseJoinColumns = { @JoinColumn(name = "codmenu", nullable = false, updatable = false) })
    private List<MenuEntity> listaMenu;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuarioperfil", 
        joinColumns = {@JoinColumn(name = "codperfil", nullable = false, updatable = false)},
        inverseJoinColumns = {@JoinColumn(name = "id", nullable = false, updatable = false)})
    private List<UsuarioEntity> usuarios;

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
     * @return the restricao
     */
    public Boolean getRestricao() {
        return restricao;
    }

    /**
     * @return the listaMenu
     */
    public List<MenuEntity> getListaMenu() {
        return listaMenu;
    }

    /**
     * @param listaMenu the listaMenu to set
     */
    public void setListaMenu(List<MenuEntity> listaMenu) {
        this.listaMenu = listaMenu;
    }

    /**
     * @param restricao the restricao to set
     */
    public void setRestricao(Boolean restricao) {
        this.restricao = restricao;
    }

    /**
     * @return the usuarios
     */
    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
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
        PerfilEntity other = (PerfilEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
