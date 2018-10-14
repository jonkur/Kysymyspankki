package kysymyspankki.domain;

/**
 *
 * @author jonkur
 */
public class KysymysVastaus {

    private Integer id;
    private Integer kysymys_id;
    private Integer vastaus_id;

    public KysymysVastaus(Integer id, Integer kysymys_id, Integer vastaus_id) {
        this.id = id;
        this.kysymys_id = kysymys_id;
        this.vastaus_id = vastaus_id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getKysymys_id() {
        return kysymys_id;
    }

    public Integer getVastaus_id() {
        return vastaus_id;
    }

}
