package sfingsolutions.constantes;

/**
 * Enum que represente os dias da semana.
 * @author Vinicius
 *
 */
public enum DiaSemana {
    DOMINGO(1, "DOMINGO", 1), SEGUNDA_FEIRA(2, "SEGUNDA-FEIRA", 2), TERÇA_FEIRA(3, "TERÇA-FEIRA", 3), QUARTA_FEIRA(4,
        "QUARTA-FEIRA", 4), QUINTA_FEIRA(5, "QUINTA-FEIRA", 5), 
        SEXTA_FEIRA(6, "SEXTA-FEIRA", 6), SABADO(7, "SÁBADO", 7);

    /**
     * Construtor default.
     * @param id Integer.
     * @param dia String.
     * @param sequencia Integer.
     */
    private DiaSemana(Integer idTemp, String diaTemp, Integer sequenciaTemp) {
        this.id = idTemp;
        this.dia = diaTemp;
        this.sequencia = sequenciaTemp;
    }

    private Integer id;
    private String dia;
    private Integer sequencia;

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
     * @return the dia
     */
    public String getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * @return the sequencia
     */
    public Integer getSequencia() {
        return sequencia;
    }

    /**
     * @param sequencia the sequencia to set
     */
    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    /**
     * Retorna o dia da semana através do id.
     * @param id Integer.
     * @return DiaSemana.
     */
    public static DiaSemana getDiaSemana(Integer id) {
        for (DiaSemana diaSemana : values()) {
            if (diaSemana.getId() == id) {
                return diaSemana;
            }
        }
        return null;
    }

    /**
     * Equals
     * @param diaSemana DiaSemana.
     * @return Boolean.
     */
    public Boolean equals(DiaSemana diaSemana) {
        return this.id.intValue() == diaSemana.getId().intValue();
    }
}
