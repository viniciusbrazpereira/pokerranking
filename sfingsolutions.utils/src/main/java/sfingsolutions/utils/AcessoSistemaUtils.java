package sfingsolutions.utils;

/**
 * Representa o acesso ao sistema.
 * @author Vinicius Braz.
 * 
 */
public enum AcessoSistemaUtils {
    ADMINISTRATIVO("A", "Administrativo");

    private AcessoSistemaUtils(String sistemaParam, String descricaoParam) {
        this.sistema = sistemaParam;
        this.descricao = descricaoParam;
    }

    private String sistema;
    private String descricao;

    /**
     * @return the sistema
     */
    public String getSistema() {
        return sistema;
    }

    /**
     * @param sistema the sistema to set
     */
    public void setSistema(String sistema) {
        this.sistema = sistema;
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
     * Retorna o acesso sistema com a chave.
     * @param chave String.
     * @return AcessoSistemaUtils.
     */
    public static AcessoSistemaUtils getAcessoSistema(String chave) {
        for (AcessoSistemaUtils acessoSistema : AcessoSistemaUtils.values()) {
            if (acessoSistema.getSistema().equals(chave)) {
                return acessoSistema;
            }
        }

        return null;
    }

    /**
     * Verifica se acesso é igual.
     * @param acessoSistemaUtils AcessoSistemaUtils.
     * @return Boolean.
     */
    public Boolean compare(AcessoSistemaUtils acessoSistemaUtils) {
        return this.getSistema().equals(acessoSistemaUtils.getSistema());
    }
}
