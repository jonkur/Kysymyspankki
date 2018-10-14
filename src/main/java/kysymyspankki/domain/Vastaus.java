package kysymyspankki.domain;

/**
 *
 * @author jonask
 */
public class Vastaus {
    private Integer id;
    private String teksti;
    
    public Vastaus(Integer id, String teksti) {
        this.id = id;
        this.teksti = teksti;
    }

    public Integer getId() {
        return id;
    }

    public String getTeksti() {
        return teksti;
    }
    
}
