package kysymyspankki.domain;

/**
 *
 * @author jonask
 */
public class Kurssi {
    private Integer id;
    private String nimi;
    private String opettaja;
    
    public Kurssi(Integer id, String nimi, String opettaja) {
        this.id = id;
        this.nimi = nimi;
        this.opettaja = opettaja;
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public String getOpettaja() {
        return opettaja;
    }
    
}
