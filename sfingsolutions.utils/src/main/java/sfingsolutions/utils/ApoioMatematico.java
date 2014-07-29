package sfingsolutions.utils;

public abstract class ApoioMatematico {

    private static final Integer PONTO_CALC = 1;
    private static final Integer VALOR_DIVIDENDO = 25;

    public static Double calcRankingPoint(Integer quantidadePlayuer, Integer position, Double prizeWinner,
        Double somePrize) {
        return (quantidadePlayuer - position + PONTO_CALC) + ((prizeWinner - somePrize) / VALOR_DIVIDENDO);
    }

    public static Double calcPrimeiroPremio(Integer quantidadePlayuer, Double prizeWinner) {
        if (quantidadePlayuer >= 1 && quantidadePlayuer < 5) {
            return prizeWinner;
        }

        if (quantidadePlayuer == 5 || quantidadePlayuer == 6) {
            return prizeWinner * 0.70;
        }

        if (quantidadePlayuer >= 7) {
            return prizeWinner * 0.50;
        }

        return 0.d;
    }

    public static Double calcSegundoPremio(Integer quantidadePlayuer, Double prizeWinner) {
        if (quantidadePlayuer == 5 || quantidadePlayuer == 6) {
            return prizeWinner * 0.30;
        }

        if (quantidadePlayuer >= 7) {
            return prizeWinner * 0.30;
        }

        return 0.d;
    }

    public static Double calcTerceiroPremio(Integer quantidadePlayuer, Double prizeWinner) {
        if (quantidadePlayuer >= 7) {
            return prizeWinner * 0.20;
        }

        return 0.d;
    }

    public static Double calcPrize(Integer quantidadePlayuer, Integer position, Double prizeWinner) {
        if (position == 1) {
            return calcPrimeiroPremio(quantidadePlayuer, prizeWinner);
        }

        if (position == 2) {
            return calcSegundoPremio(quantidadePlayuer, prizeWinner);
        }

        if (position == 3) {
            return calcTerceiroPremio(quantidadePlayuer, prizeWinner);
        }

        return 0.d;
    }
    
    public static Double calcPrizeHome(Double prize, Double percentPrizeHome) {
        return (prize * percentPrizeHome) / 100;
    }
}
