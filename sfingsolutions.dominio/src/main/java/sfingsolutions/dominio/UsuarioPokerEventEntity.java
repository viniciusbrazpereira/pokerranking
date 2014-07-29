package sfingsolutions.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import sfingsolutions.dominio.defaultobject.DefaultObject;

/**
 * Entidade que representa um Usuário e Evento.
 * 
 * @author Vinicius Braz.
 */
@Entity
@Table(name = "punter_pokerEvent")
@IdClass(sfingsolutions.dominio.UsuarioPokerEventEntityId.class)
public class UsuarioPokerEventEntity extends DefaultObject<Integer> {

    /**
     * 
     */
    private static final long serialVersionUID = 3457649337939053830L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPunter", nullable = false)
    private UsuarioEntity usuarioEntity;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPokerEvent", nullable = false)
    private PokerEventEntity pokerEventEntity;

    @Column(name = "countBuy", length = 11)
    private Integer countBuy = 1;

    @Column(name = "countRebuy", length = 11)
    private Integer countRebuy = 0;

    @Column(name = "countAddon", length = 11)
    private Integer countAddon = 0;

    @Column(name = "prizeGain")
    private Double prizeGain = 0.d;

    @Column(name = "position", length = 11)
    private Integer position = 0;

    @Transient
    private Integer id;

    @Transient
    private Double rankingPoint;

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the countBuy
     */
    public Integer getCountBuy() {
        return countBuy;
    }

    /**
     * @param countBuy the countBuy to set
     */
    public void setCountBuy(Integer countBuy) {
        this.countBuy = countBuy;
    }

    /**
     * @return the countRebuy
     */
    public Integer getCountRebuy() {
        return countRebuy;
    }

    /**
     * @param countRebuy the countRebuy to set
     */
    public void setCountRebuy(Integer countRebuy) {
        this.countRebuy = countRebuy;
    }

    /**
     * @return the countAddon
     */
    public Integer getCountAddon() {
        return countAddon;
    }

    /**
     * @param countAddon the countAddon to set
     */
    public void setCountAddon(Integer countAddon) {
        this.countAddon = countAddon;
    }

    /**
     * @return the prizeGain
     */
    public Double getPrizeGain() {
        return prizeGain;
    }

    /**
     * @param prizeGain the prizeGain to set
     */
    public void setPrizeGain(Double prizeGain) {
        this.prizeGain = prizeGain;
    }

    /**
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return the UsuarioPokerEventEntityId
     */
    public UsuarioPokerEventEntityId getUsuarioPokerEventEntityId() {
        return new UsuarioPokerEventEntityId(this.usuarioEntity, this.pokerEventEntity);
    }

    /**
     * @param UsuarioPokerEventEntityId the UsuarioPokerEventEntityId to set
     */
    public void setUsuarioPokerEventEntityId(UsuarioPokerEventEntityId usuarioPokerEventEntityId) {
        this.usuarioEntity = usuarioPokerEventEntityId.getUsuarioEntity();
        this.pokerEventEntity = usuarioPokerEventEntityId.getPokerEventEntity();
    }

    /**
     * @return the rankingPoint
     */
    public Double getRankingPoint() {
        return rankingPoint;
    }

    /**
     * @param rankingPoint the rankingPoint to set
     */
    public void setRankingPoint(Double rankingPoint) {
        this.rankingPoint = rankingPoint;
    }

}
