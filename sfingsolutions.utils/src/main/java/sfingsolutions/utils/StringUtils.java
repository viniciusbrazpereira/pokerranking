package sfingsolutions.utils;

import java.util.Map;
import java.util.UUID;

/**
 * Classe utilitária contendo funções relacionadas a strings.
 * 
 * @author Vinicius Braz.
 * 
 */
public final class StringUtils {
    
    private static final int MIL = 1000;
    private static final int NUMBER11 = 11;
    private static final int NUMBER12 = 12;
    private static final int NUMBER2 = 2;

    private static final String ACENTUADO = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";

    private StringUtils() {
        super();
    }

    /**
     * Função que verifica se uma string é vazia (remove espaços vazios).
     * 
     * @param texto Texto.
     * @return Verdadeiro se for vazio.
     */
    public static boolean isVazio(String texto) {
        return (ObjectUtils.isNull(texto) || texto.trim().length() == 0);
    }

    /**
     * Retorna verdadeiro se alguma String estiver vazia.
     * @param strings Strings
     * @return Verdadeiro caso haja alguma string vazia.
     */
    public static boolean isVazio(String... strings) {
        boolean res = true;
        if (ObjectUtils.isReferencia((Object[]) strings)) {
            res = false;
            for (int idx = 0; idx < strings.length && !res; idx++) {
                res = isVazio(strings[idx]);
            }
        }
        return res;
    }

    /**
     * Retorna verdadeiro caso as duas strings forem iguais (ignorando
     * maiúscula/minúscula).
     * 
     * @param string0 String validada
     * @param string1 String validada
     * @return booleano
     */
    public static boolean isStringsIguais(String string0, String string1) {
        boolean resultado = false;
        if (!isVazio(string0, string1)) {
            resultado = string0.equalsIgnoreCase(string1);
        } else if ("".equals(string0) && "".equals(string1)) {
            resultado = true;
        }
        return resultado;
    }

    /**
     * Valida se a string possui uma formação de e-mail.
     * @param email String para verificação.
     * @return Verdadeiro caso esteja na formação.
     */
    public static boolean isFormatoEmailValido(String email) {
        return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }

    /**
     * Obtém nome do arquivo e extensão de um endereço completo.
     * @param enderecoCompleto Endereço completo.
     * @return Somente arquivo e extensão
     */
    public static String obterNomeArquivo(String enderecoCompleto) {
        String retorno = "";
        if (!isVazio(enderecoCompleto)) {
            int pos = enderecoCompleto.lastIndexOf("\\"); // Windows
            if (pos < 0) {
                pos = enderecoCompleto.lastIndexOf("/"); // Linux
            }
            if (pos < 0) {
                pos = -1;
            }
            retorno = enderecoCompleto.substring(++pos);
        }
        return retorno;
    }

    /**
     * Obtém a extensão do arquivo a partir do endereço completo.
     * @param enderecoCompleto Endereço completo.
     * @return Somente extensão
     */
    public static String obterExtensaoArquivo(String enderecoCompleto) {
        String retorno = "";
        if (!isVazio(enderecoCompleto)) {
            int pos = enderecoCompleto.lastIndexOf(".");
            if (pos > 0) {
                retorno = enderecoCompleto.substring(++pos);
            }
        }
        return retorno;
    }

    /**
     * Substitui tags por valores que constam em um texto.
     * @param texto Texto.
     * @param tags Conjunto de tags / valores.
     * @return Texto substituido.
     */
    public static String substituirTagPorValor(String texto, Map<String, String> tags) {
        String retorno = texto;
        if (!isVazio(retorno) && ObjectUtils.isReferencia(tags)) {
            for (String tag : tags.keySet()) {
                String valor = tags.get(tag);
                if (ObjectUtils.isReferencia(valor)) {
                    retorno = retorno.replaceAll(tag, valor);
                }
            }
        }
        return retorno;
    }

    /**
     * Retorna a string contendo apenas a primeira letra em maiúsculo.
     * @param s String.
     * @return String capitalize.
     */
    public static String capitalize(String s) {
        if (isVazio(s)) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * Substitui caracter com acento por inderline(_).
     * @param valor String
     * @return String
     */
    public static String substituirAcentoPorInderline(final String valor) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < valor.length(); ++i) {
            CharSequence caracter = valor.substring(i, i + 1);
            if (ACENTUADO.contains(caracter)) {
                sb.append("_");
            } else {
                sb.append(caracter);
            }
        }
        return sb.toString();
    }

    /**
     * Obtém um token randômico.
     * @return token.
     */
    public static String gerarUUID() {
        UUID token = UUID.randomUUID();
        return token.toString();
    }
    
    /**
     * @param cpf String
     * @return Boolean
     */
    public static Boolean validarCPF(String cpf) {
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d2 = 0;
        d1 = d2;
        resto = 0;
        digito2 = resto;
        digito1 = digito2;

        if (isVazio(cpf) || !cpf.matches("\\d{11}")) {
            return false;
        }

        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {

            digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

            // multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
            // e assim
            // por diante.
            d1 = d1 + (NUMBER11 - nCount) * digitoCPF;

            // para o segundo digito repita o procedimento incluindo o primeiro
            // digito
            // calculado no passo anterior.
            d2 = d2 + (NUMBER12 - nCount) * digitoCPF;

        }

        // Primeiro resto da divis„o por NUMBER11.
        resto = (d1 % NUMBER11);

        // Se o resultado for 0 ou 1 o digito È 0 caso contr·rio o digito È NUMBER11
        // menos o
        // resultado anterior.
        if (resto < NUMBER2){
            digito1 = 0;
        } else {
            digito1 = NUMBER11 - resto;
        }

        d2 += NUMBER2 * digito1;

        // Segundo resto da divis„o por NUMBER11.
        resto = (d2 % NUMBER11);

        // Se o resultado for 0 ou 1 o digito È 0 caso contr·rio o digito È NUMBER11
        // menos
        // o resultado anterior.
        if (resto < NUMBER2){
            digito2 = 0;
        } else {
            digito2 = NUMBER11 - resto;
        }

        // Digito verificador do CPF que est· sendo validado.
        String nDigVerific = cpf.substring(cpf.length() - NUMBER2, cpf.length());

        // Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        // comparar o digito verificador do cpf com o primeiro resto + o segundo
        // resto.
        return nDigVerific.equals(nDigResult);
    }

}
