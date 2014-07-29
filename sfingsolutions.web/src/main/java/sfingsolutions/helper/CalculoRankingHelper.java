package sfingsolutions.helper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sfingsolutions.dominio.PokerEventEntity;
import sfingsolutions.dominio.UsuarioPokerEventEntity;
import sfingsolutions.utils.ApoioMatematico;

public abstract class CalculoRankingHelper {

    /**
     * Faz o calculo do ponto do ranking.
     * @param entities List<UsuarioPokerEventEntity>.
     * @param pokerEventRanking UsuarioPokerEventEntity.
     * @param event PokerEventEntity.
     * @return double
     */
    public static double calcRanking(List<UsuarioPokerEventEntity> entities, UsuarioPokerEventEntity pokerEventRanking,
        PokerEventEntity event) {

        int quantidadePlayuer = entities.size();
        int position = pokerEventRanking.getPosition();

        double prizeWinner = pokerEventRanking.getPrizeGain();
        double buyin = event.getBuy() * pokerEventRanking.getCountBuy();
        double rebuyin = event.getRebuy() * pokerEventRanking.getCountRebuy();
        double addon = event.getAddon() * pokerEventRanking.getCountAddon();

        Double percentPrize = ApoioMatematico.calcPrizeHome(event.getBuy(), event.getPercentPrizeHome());

        if (event.getIsPercentRebuyin() && pokerEventRanking.getCountRebuy() > 0) {
            rebuyin = rebuyin - ApoioMatematico.calcPrizeHome(rebuyin, event.getPercentPrizeHome());
        }

        if (event.getIsPercentAddon() && pokerEventRanking.getCountAddon() > 0) {
            addon = addon - ApoioMatematico.calcPrizeHome(addon, event.getPercentPrizeHome());
        }

        double somePrize = (buyin - percentPrize) + rebuyin + addon;

        return ApoioMatematico.calcRankingPoint(quantidadePlayuer, position, prizeWinner, somePrize);
    }
    
    /**
     * Ordena os apostadores por valor de posicao.
     * @param entities List<UsuarioPokerEventEntity>.
     */
    public static void orderUsuarioPokerEventByPosition(List<UsuarioPokerEventEntity> entities) {
        Collections.sort(entities, new Comparator<UsuarioPokerEventEntity>() {
            @Override
            public int compare(UsuarioPokerEventEntity o1, UsuarioPokerEventEntity o2) {
                return o1.getPosition().compareTo(o2.getPosition());
            }

        });
    }
}
