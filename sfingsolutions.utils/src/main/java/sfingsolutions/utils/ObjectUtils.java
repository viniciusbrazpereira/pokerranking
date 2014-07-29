package sfingsolutions.utils;


/**
 * Classe utilit�ria contendo fun��es relacionadas a objetos.
 * 
 * @author viniciusbrazpereira.
 * 
 */
public final class ObjectUtils {

    private ObjectUtils() {
        super();
    }

    /**
     * Fun��o que verifica se um objeto � nulo.
     * 
     * @param objeto objeto.
     * @return Verdadeiro se for nulo.
     */
    public static boolean isNull(Object objeto) {
        return (objeto == null);
    }

    /**
     * Fun��o que verifica se um objeto possui refer�ncia.
     * 
     * @param objeto objeto.
     * @return Verdadeiro se possui refer�ncia.
     */
    public static boolean isReferencia(Object objeto) {
        return !ObjectUtils.isNull(objeto);
    }

    /**
     * Fun��o que retorna verdadeiro se todos os objetos possuem refer�ncia.
     * 
     * @param objetos Objetos
     * @return Verdadeiro se os objetos possuem refer�ncia.
     */
    public static boolean isReferencia(Object... objetos) {
        boolean res = false;

        if (isReferencia((Object) objetos)) {
            res = true;
            for (int idx = 0; idx < objetos.length && res; idx++) {
                res = isReferencia(objetos[idx]);
            }
        }
        return res;
    }

    /**
     * Valor maior que zero.
     * @param objeto Integer.
     * @return boolean.
     */
    public static boolean isMoreZero(Integer objeto) {
        return (objeto != null && objeto > 0);
    }
}
