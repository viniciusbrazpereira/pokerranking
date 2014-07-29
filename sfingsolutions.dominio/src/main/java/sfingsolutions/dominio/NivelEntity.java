package sfingsolutions.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import sfingsolutions.dominio.defaultobject.DefaultObject;


/**
 * Entidade que representa um nivel.
 * 
 * @author Vinicius Braz.
 */
@Entity
@Table(name = "nivel")
public class NivelEntity extends DefaultObject<Integer> {

    /**
     * 
     */
    private static final long serialVersionUID = -2052618975536895139L;
    
    @Id
    @Column(name = "id_nivel", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number_nivel")
    private Integer numberNivel;
    
    @Column(name = "small_blind")
    private Double smallBlind;
    
    @Column(name = "big_blind")
    private Double bigBlind;
    
    @Column(name = "ante")
    private Double ante;

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
     * @return the numberNivel
     */
    public Integer getNumberNivel() {
        return numberNivel;
    }

    /**
     * @param numberNivel the numberNivel to set
     */
    public void setNumberNivel(Integer numberNivel) {
        this.numberNivel = numberNivel;
    }

    /**
     * @return the smallBlind
     */
    public Double getSmallBlind() {
        return smallBlind;
    }

    /**
     * @param smallBlind the smallBlind to set
     */
    public void setSmallBlind(Double smallBlind) {
        this.smallBlind = smallBlind;
    }

    /**
     * @return the bigBlind
     */
    public Double getBigBlind() {
        return bigBlind;
    }

    /**
     * @param bigBlind the bigBlind to set
     */
    public void setBigBlind(Double bigBlind) {
        this.bigBlind = bigBlind;
    }

    /**
     * @return the ante
     */
    public Double getAnte() {
        return ante;
    }

    /**
     * @param ante the ante to set
     */
    public void setAnte(Double ante) {
        this.ante = ante;
    }
}
