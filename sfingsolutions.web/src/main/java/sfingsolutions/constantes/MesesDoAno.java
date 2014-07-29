package sfingsolutions.constantes;

/**
 * Enum que representa os meses do ano.
 * @author Vinicius Braz
 *
 */
public enum MesesDoAno {

    JANEIRO(1, "Janeiro"), FEVEREIRO(2, "Fevereiro"), MARCO(3, "Março"), ABRIL(4, "Abril"), MAIO(5, "Maio"), JUNHO(6,
        "Junho"), JULHO(7, "Julho"), AGOSTO(8, "Agosto"), SETEMBRO(9, "Setembro"), OUTUBRO(10, "Outubro"), NOVEMBRO(11,
        "Novembro"), DEZEMBRO(12, "Dezembro");

    private MesesDoAno(Integer idTemp, String mesTemp) {
        this.id = idTemp;
        this.mes = mesTemp;
    }

    private Integer id;
    private String mes;

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
     * @return the mes
     */
    public String getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(String mes) {
        this.mes = mes;
    }

    /**
     * Retorna o mes do ano através do id.
     * @param id Integer.
     * @return MesesDoAno.
     */
    public static MesesDoAno getMesesDoAno(Integer id) {
        for (MesesDoAno object : values()) {
            if (object.getId() == id) {
                return object;
            }
        }
        return null;
    }

    /**
     * Equals
     * @param mesesDoAno MesesDoAno.
     * @return Boolean.
     */
    public Boolean equals(MesesDoAno mesesDoAno) {
        return this.id.intValue() == mesesDoAno.getId().intValue();
    }
}
