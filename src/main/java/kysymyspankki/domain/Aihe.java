package kysymyspankki.domain;

/**
 *
 * @author jonask
 */
public class Aihe {
    private Integer id;
    private String nimi;
    private Integer kurssi_id;
    
    public Aihe(Integer id, String nimi, Integer kurssi_id) {
        this.id = id;
        this.nimi = nimi;
        this.kurssi_id = kurssi_id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getKurssi_id() {
        return kurssi_id;
    }

    public String getNimi() {
        return nimi;
    }
    
}
