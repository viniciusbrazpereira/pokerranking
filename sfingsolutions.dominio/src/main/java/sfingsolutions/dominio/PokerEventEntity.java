package sfingsolutions.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import sfingsolutions.dominio.defaultobject.DefaultObject;


/**
 * Entidade que representa um Usuário.
 * 
 * @author Vinicius Braz.
 */
@Entity
@Table(name = "pokerEvent")
public class PokerEventEntity extends DefaultObject<Integer> {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3867688965882178832L;

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    
    @Column(name = "buy")
    private Double buy;
    
    @Column(name = "rebuy")
    private Double rebuy;
    
    @Column(name = "addon")
    private Double addon;
    
    @Column(name = "dataInclusao")
    private Date dataInclusao;
    
    @Column(name = "dataEvent")
    private Date dataEvent;
    
    @Column(name = "prizeWinner")
    private Double prizeWinner = 0.d;
    
    @Column(name = "dataInicio")
    private Date dataInicio;
    
    @Column(name = "dataFinal")
    private Date dataFinal;
    
    @Column(name = "prizeHome")
    private Double prizeHome = 0.d;
    
    @Column(name = "prizeTotal")
    private Double prizeTotal = 0.d;
    
    @Column(name = "percentPrizeHome")
    private Double percentPrizeHome = 0.d;
    
    @Column(name = "isPercentRebuyin", columnDefinition = "bit")
    private Boolean isPercentRebuyin = Boolean.FALSE;
    
    @Column(name = "isPercentAddon", columnDefinition = "bit")
    private Boolean isPercentAddon = Boolean.FALSE;

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
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the buy
     */
    public Double getBuy() {
        return buy;
    }

    /**
     * @param buy the buy to set
     */
    public void setBuy(Double buy) {
        this.buy = buy;
    }

    /**
     * @return the rebuy
     */
    public Double getRebuy() {
        return rebuy;
    }

    /**
     * @param rebuy the rebuy to set
     */
    public void setRebuy(Double rebuy) {
        this.rebuy = rebuy;
    }

    /**
     * @return the addon
     */
    public Double getAddon() {
        return addon;
    }

    /**
     * @param addon the addon to set
     */
    public void setAddon(Double addon) {
        this.addon = addon;
    }

    /**
     * @return the dataInclusao
     */
    public Date getDataInclusao() {
        return dataInclusao;
    }

    /**
     * @param dataInclusao the dataInclusao to set
     */
    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    /**
     * @return the dataEvent
     */
    public Date getDataEvent() {
        return dataEvent;
    }

    /**
     * @param dataEvent the dataEvent to set
     */
    public void setDataEvent(Date dataEvent) {
        this.dataEvent = dataEvent;
    }

    /**
     * @return the prizeWinner
     */
    public Double getPrizeWinner() {
        return prizeWinner;
    }

    /**
     * @param prizeWinner the prizeWinner to set
     */
    public void setPrizeWinner(Double prizeWinner) {
        this.prizeWinner = prizeWinner;
    }

    /**
     * @return the dataInicio
     */
    public Date getDataInicio() {
        return dataInicio;
    }

    /**
     * @param dataInicio the dataInicio to set
     */
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * @return the dataFinal
     */
    public Date getDataFinal() {
        return dataFinal;
    }

    /**
     * @param dataFinal the dataFinal to set
     */
    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    /**
     * @return the prizeHome
     */
    public Double getPrizeHome() {
        return prizeHome;
    }

    /**
     * @param prizeHome the prizeHome to set
     */
    
    public void setPrizeHome(Double prizeHome) {
        this.prizeHome = prizeHome;
    }

    /**
     * @return the prizeTotal
     */
    public Double getPrizeTotal() {
        return prizeTotal;
    }

    /**
     * @param prizeTotal the prizeTotal to set
     */
    public void setPrizeTotal(Double prizeTotal) {
        this.prizeTotal = prizeTotal;
    }

    /**
     * @return the percentPrizeHome
     */
    public Double getPercentPrizeHome() {
        return percentPrizeHome;
    }

    /**
     * @param percentPrizeHome the percentPrizeHome to set
     */
    public void setPercentPrizeHome(Double percentPrizeHome) {
        this.percentPrizeHome = percentPrizeHome;
    }

    /**
     * @return the isPercentRebuyin
     */
    public Boolean getIsPercentRebuyin() {
        return isPercentRebuyin;
    }

    /**
     * @param isPercentRebuyin the isPercentRebuyin to set
     */
    public void setIsPercentRebuyin(Boolean isPercentRebuyin) {
        this.isPercentRebuyin = isPercentRebuyin;
    }

    /**
     * @return the isPercentAddon
     */
    public Boolean getIsPercentAddon() {
        return isPercentAddon;
    }

    /**
     * @param isPercentAddon the isPercentAddon to set
     */
    public void setIsPercentAddon(Boolean isPercentAddon) {
        this.isPercentAddon = isPercentAddon;
    }
}
