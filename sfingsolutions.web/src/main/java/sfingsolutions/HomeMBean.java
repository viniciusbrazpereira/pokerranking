package sfingsolutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.PokerEventEntity;
import sfingsolutions.dominio.UsuarioPokerEventEntity;
import sfingsolutions.dominio.UsuarioPokerEventEntityId;
import sfingsolutions.helper.CalculoRankingHelper;
import sfingsolutions.negocio.EventBC;
import sfingsolutions.negocio.exception.BCException;

/**
 * Manager Bean responsável por home.
 * 
 * @author Vinicius Braz.
 */
@ViewScoped
@ManagedBean(name = "homeMBean")
public class HomeMBean extends AbstratoCrudMBean<UsuarioPokerEventEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeMBean.class);

    private List<UsuarioPokerEventEntity> pokerEventEntities;
    private List<UsuarioPokerEventEntity> eventEntities;
    private List<PokerEventEntity> eventList;

    private List<UsuarioPokerEventEntity> punterEventEntitiesTab;

    private static final Integer RANKING_DEFAULT = 1500;

    @EJB
    private EventBC eventBC;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construtor padrão da classe.
     */
    public HomeMBean() {
        super();
        LOGGER.debug("... HomeMBean()");

        setIsCadastro(Boolean.TRUE);
        setItem(new UsuarioPokerEventEntity());
    }

    @Override
    public UsuarioPokerEventEntity salvar() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UsuarioPokerEventEntity excluir() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the pokerEventEntities
     */
    public List<UsuarioPokerEventEntity> getPokerEventEntities() {
        if (pokerEventEntities == null || pokerEventEntities.size() == 0) {
            pokerEventEntities = listarUsuarioPokerEventOrderRanking();
        }
        return pokerEventEntities;
    }

    private List<UsuarioPokerEventEntity> listarUsuarioPokerEventOrderRanking() {
        List<UsuarioPokerEventEntity> usuarioPokerEventEntities = new ArrayList<UsuarioPokerEventEntity>();

        try {

            for (PokerEventEntity event : eventBC.listarAllPokerEvent()) {
                if (event.getDataFinal() != null) {
                    UsuarioPokerEventEntityId usuarioPokerEventEntityId = new UsuarioPokerEventEntityId();
                    usuarioPokerEventEntityId.setPokerEventEntity(event);

                    UsuarioPokerEventEntity usuarioPokerEventEntity = new UsuarioPokerEventEntity();
                    usuarioPokerEventEntity.setUsuarioPokerEventEntityId(usuarioPokerEventEntityId);

                    List<UsuarioPokerEventEntity> entities = eventBC
                        .selectAllUsuarioPokerEventEntity(usuarioPokerEventEntity);

                    if (entities != null) {
                        for (UsuarioPokerEventEntity pokerEventEntity : entities) {
                            double rankingPoint = CalculoRankingHelper.calcRanking(entities, pokerEventEntity, event);

                            UsuarioPokerEventEntity temp = obterUsuarioPokerEventById(usuarioPokerEventEntities,
                                pokerEventEntity);

                            if (temp == null) {
                                pokerEventEntity.setRankingPoint(rankingPoint + RANKING_DEFAULT);
                                usuarioPokerEventEntities.add(pokerEventEntity);

                            } else {
                                temp.setRankingPoint(rankingPoint + temp.getRankingPoint());

                                temp.setCountBuy(pokerEventEntity.getCountBuy() + temp.getCountBuy());
                                temp.setCountRebuy(pokerEventEntity.getCountRebuy() + temp.getCountRebuy());
                                temp.setCountAddon(pokerEventEntity.getCountAddon() + temp.getCountAddon());

                                temp.setPrizeGain(pokerEventEntity.getPrizeGain() + temp.getPrizeGain());
                            }
                        }
                    }
                }
            }

        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o carregarListagem{}: ", e);
            lancarExcecao("Erro ao carregar o carregarListagem{}: " + e.getMessage());
        }

        if (usuarioPokerEventEntities.size() > 0) {
            orderUsuarioPokerEventByRanking(usuarioPokerEventEntities);
        }

        return usuarioPokerEventEntities;
    }

    private void orderUsuarioPokerEventByRanking(List<UsuarioPokerEventEntity> entities) {
        Collections.sort(entities, new Comparator<UsuarioPokerEventEntity>() {
            @Override
            public int compare(UsuarioPokerEventEntity o1, UsuarioPokerEventEntity o2) {
                return o2.getRankingPoint().compareTo(o1.getRankingPoint());
            }
        });
    }

    private UsuarioPokerEventEntity obterUsuarioPokerEventById(List<UsuarioPokerEventEntity> entities,
        UsuarioPokerEventEntity temp) {
        for (UsuarioPokerEventEntity entity : entities) {
            if (entity.getUsuarioPokerEventEntityId().getUsuarioEntity().getId() == temp.getUsuarioPokerEventEntityId()
                .getUsuarioEntity().getId()) {
                return entity;
            }
        }
        return null;
    }

    public void viewEvent(UsuarioPokerEventEntity entity) {
        try {
            setItem(entity);
            eventEntities = eventBC.selectAllPokerEventEntity(entity);

            for (UsuarioPokerEventEntity pokerEventRanking : eventEntities) {

                List<UsuarioPokerEventEntity> eventoUsuerEntities = eventBC
                    .selectAllUsuarioPokerEventEntity(pokerEventRanking);

                PokerEventEntity event = pokerEventRanking.getUsuarioPokerEventEntityId().getPokerEventEntity();
                pokerEventRanking.setRankingPoint(CalculoRankingHelper.calcRanking(eventoUsuerEntities,
                    pokerEventRanking, event));
            }
        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o viewEvent{}: ", e);
            lancarExcecao("Erro ao carregar o viewEvent{}: " + e.getMessage());
        }
    }

    /**
     * viewPunter
     * @param entity PokerEventEntity.
     */
    public void viewPunter(PokerEventEntity entity) {
        try {

            UsuarioPokerEventEntityId usuarioPokerEventEntityId = new UsuarioPokerEventEntityId();
            usuarioPokerEventEntityId.setPokerEventEntity(entity);

            UsuarioPokerEventEntity usuarioPokerEventEntity = new UsuarioPokerEventEntity();
            usuarioPokerEventEntity.setUsuarioPokerEventEntityId(usuarioPokerEventEntityId);

            punterEventEntitiesTab = eventBC.selectAllUsuarioPokerEventEntity(usuarioPokerEventEntity);

            if (punterEventEntitiesTab != null) {
                for (UsuarioPokerEventEntity pokerEventRanking : punterEventEntitiesTab) {
                    pokerEventRanking.setRankingPoint(CalculoRankingHelper.calcRanking(punterEventEntitiesTab,
                        pokerEventRanking, entity));
                }
                
                CalculoRankingHelper.orderUsuarioPokerEventByPosition(punterEventEntitiesTab);
            }
        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o viewPunter{}: ", e);
            lancarExcecao("Erro ao carregar o viewPunter{}: " + e.getMessage());
        }
    }

    /**
     * @return the eventList
     */
    public List<PokerEventEntity> getEventList() {
        try {
            if (eventList == null) {
                eventList = eventBC.listarAllPokerEvent();
            }
            return eventList;
        } catch (BCException e) {
            LOGGER.error("Erro nome metodo getEventList da class HomeMBean {}: ", e);
            return null;
        }
    }

    /**
     * @param pokerEventEntities the pokerEventEntities to set
     */
    public void setPokerEventEntities(List<UsuarioPokerEventEntity> pokerEventEntities) {
        this.pokerEventEntities = pokerEventEntities;
    }

    /**
     * @return the eventEntities
     */
    public List<UsuarioPokerEventEntity> getEventEntities() {
        return eventEntities;
    }

    /**
     * @param eventEntities the eventEntities to set
     */
    public void setEventEntities(List<UsuarioPokerEventEntity> eventEntities) {
        this.eventEntities = eventEntities;
    }

    /**
     * @param eventList the eventList to set
     */
    public void setEventList(List<PokerEventEntity> eventList) {
        this.eventList = eventList;
    }

    /**
     * @return the punterEventEntitiesTab
     */
    public List<UsuarioPokerEventEntity> getPunterEventEntitiesTab() {
        return punterEventEntitiesTab;
    }

    /**
     * @param punterEventEntitiesTab the punterEventEntitiesTab to set
     */
    public void setPunterEventEntitiesTab(List<UsuarioPokerEventEntity> punterEventEntitiesTab) {
        this.punterEventEntitiesTab = punterEventEntitiesTab;
    }
}
