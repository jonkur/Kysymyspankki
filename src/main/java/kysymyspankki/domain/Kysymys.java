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
    
    public Kysymys(Integer id, Integer kurssi_id, Integer aihe_id, String teksti) {
        this.id = id;
        this.kurssi_id = kurssi_id;
        this.aihe_id = aihe_id;
        this.teksti = teksti;
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
    
}
