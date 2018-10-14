package kysymyspankki.domain;

/**
 *
 * @author jonkur
 */
public class Vastaus {

    private Integer id;
    private String teksti;
    private Boolean oikea;

    public Vastaus(Integer id, String teksti, Boolean oikea) {
        this.id = id;
        this.teksti = teksti;
        this.oikea = oikea;
    }

    public Integer getId() {
        return id;
    }

    public String getTeksti() {
        return teksti;
    }

    public Boolean getOikea() {
        return oikea;
    }

}
