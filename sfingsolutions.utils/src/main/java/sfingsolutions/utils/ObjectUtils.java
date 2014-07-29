package sfingsolutions.utils;


/**
 * Classe utilitária contendo funções relacionadas a objetos.
 * 
 * @author viniciusbrazpereira.
 * 
 */
public final class ObjectUtils {

    private ObjectUtils() {
        super();
    }

    /**
     * Função que verifica se um objeto é nulo.
     * 
     * @param objeto objeto.
     * @return Verdadeiro se for nulo.
     */
    public static boolean isNull(Object objeto) {
        return (objeto == null);
    }

    /**
     * Função que verifica se um objeto possui referência.
     * 
     * @param objeto objeto.
     * @return Verdadeiro se possui referência.
     */
    public static boolean isReferencia(Object objeto) {
        return !ObjectUtils.isNull(objeto);
    }

    /**
     * Função que retorna verdadeiro se todos os objetos possuem referência.
     * 
     * @param objetos Objetos
     * @return Verdadeiro se os objetos possuem referência.
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
