package kysymyspankki.domain;

/**
 *
 * @author jonkur
 */
public class Kysymys {
    private Integer id;
    private Integer kurssi_id;
    private Integer aihe_id;
    private String teksti;
    private Integer oikeavastaus_id;
    
    public Kysymys(Integer id, Integer kurssi_id, Integer aihe_id, String teksti, Integer oikeavastaus_id) {
        this.id = id;
        this.kurssi_id = kurssi_id;
        this.aihe_id = aihe_id;
        this.teksti = teksti;
        this.oikeavastaus_id = oikeavastaus_id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getKurssi_id() {
        return kurssi_id;
    }

    public Integer getAihe_id() {
        return aihe_id;
    }

    public String getTeksti() {
        return teksti;
    }

    public Integer getOikeavastaus_id() {
        return oikeavastaus_id;
    }
    
}
