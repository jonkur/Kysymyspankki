
import java.util.HashMap;
import kysymyspankki.database.Database;
import spark.Spark;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.util.List;
import kysymyspankki.dao.AiheDao;
import kysymyspankki.dao.KurssiDao;
import kysymyspankki.dao.KysymysDao;
import kysymyspankki.dao.KysymysVastausDao;
import kysymyspankki.dao.VastausDao;
import kysymyspankki.domain.Aihe;
import kysymyspankki.domain.Kurssi;
import kysymyspankki.domain.Kysymys;
import kysymyspankki.domain.KysymysVastaus;
import kysymyspankki.domain.Vastaus;

/**
 *
 * @author jonkur
 */
public class Main {

    public static void main(String[] args) {

        Database db = new Database("jdbc:sqlite:testi.db");
        KurssiDao kurssit = new KurssiDao(db);
        AiheDao aiheet = new AiheDao(db);
        KysymysDao kysymykset = new KysymysDao(db);
        VastausDao vastaukset = new VastausDao(db);
        KysymysVastausDao kysymysVastaukset = new KysymysVastausDao(db);

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap();
            List<Kurssi> kurs = kurssit.findAll();
            List<Aihe> aih = aiheet.findAll();
            List<Kysymys> kyss = kysymykset.findAll();
            map.put("kurssit", kurs);
            map.put("aiheet", aih);
            map.put("kysymykset", kyss);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kysymys/:id", (req, res) -> {
            HashMap map = new HashMap();
            Kysymys kysymys = kysymykset.findOne(Integer.parseInt(req.params(":id")));
            Kurssi kurssi = kurssit.findOne(kysymys.getKurssi_id());
            Aihe aihe = aiheet.findOne(kysymys.getAihe_id());
            map.put("kysymys", kysymys.getTeksti());
            map.put("kysymys_id", kysymys.getId());
            map.put("kurssi", kurssi.getNimi());
            map.put("aihe", aihe.getNimi());
            map.put("vastaukset", vastaukset.findAllWithKysymysId(kysymys.getId()));
            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());

        Spark.post("/kysymys", (req, res) -> {
            final String kurssiNimi = req.queryParams("kurssi").toLowerCase();
            final String aiheNimi = req.queryParams("aihe").toLowerCase();
            Kurssi k = kurssit.findByName(kurssiNimi);
            Aihe a = null;
            if (k != null) {
                final int kid = k.getId();
                List<Aihe> aiheLista = aiheet.findAll();
                a = aiheLista.stream().filter(ah -> (ah.getNimi().equals(aiheNimi) && ah.getKurssi_id() == kid)).findFirst().orElse(null);
            }
            if (k == null) {
                k = new Kurssi(-1, kurssiNimi, "");
                k = kurssit.saveOrUpdate(k);
            }
            if (a == null || a.getKurssi_id() != k.getId()) {
                a = new Aihe(-1, aiheNimi, k.getId());
                a = aiheet.saveOrUpdate(a);
            }
            Kysymys kys = new Kysymys(-1, k.getId(), a.getId(), req.queryParams("teksti"));
            kysymykset.saveOrUpdate(kys);
            res.redirect("/");
            return "Done.";
        });

        Spark.post("/poistakysymys/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.params(":id"));
            Kysymys k = kysymykset.findOne(id);
            kysymykset.delete(id);
            aiheet.deleteIfUnused(k.getAihe_id());
            kurssit.deleteIfUnused(k.getKurssi_id());
            res.redirect("/");
            return "";
        });
        
        // Vastaukset
        
        Spark.post("/vastaus", (req, res) -> {
            boolean oikea = req.queryParams("vastausoikein") == null ? false : true;
            Vastaus v = new Vastaus(-1, req.queryParams("vastausteksti"), oikea);
            v = vastaukset.saveOrUpdate(v);
            int kId = Integer.parseInt(req.queryParams("kysymys_id"));
            KysymysVastaus kv = new KysymysVastaus(-1, kId, v.getId());
            kysymysVastaukset.saveOrUpdate(kv);
            res.redirect("/kysymys/" + kId);
            return "";
        });
        
        Spark.post("/poistavastaus", (req, res) -> {
            int vastaus_id = Integer.parseInt(req.queryParams("vastaus_id"));
            int kysymys_id = Integer.parseInt(req.queryParams("kysymys_id"));
            kysymysVastaukset.deleteByKysymysIdAndVastausId(kysymys_id, vastaus_id);
            vastaukset.deleteIfUnused(vastaus_id);
            res.redirect("/kysymys/" + kysymys_id);
            return "";
        });

    }

}
