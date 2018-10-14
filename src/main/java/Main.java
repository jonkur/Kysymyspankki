
import java.util.HashMap;
import kysymyspankki.database.Database;
import spark.Spark;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.util.List;
import kysymyspankki.dao.AiheDao;
import kysymyspankki.dao.KurssiDao;
import kysymyspankki.dao.KysymysDao;
import kysymyspankki.domain.Aihe;
import kysymyspankki.domain.Kurssi;
import kysymyspankki.domain.Kysymys;

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

        // TODO: Hoida kuntoon koko kysymyssivu
        Spark.get("/kysymys/:id", (req, res) -> {
            HashMap map = new HashMap();
            Kysymys kysymys = kysymykset.findOne(Integer.parseInt(req.params(":id")));
            Kurssi kurssi = kurssit.findOne(kysymys.getKurssi_id());
            Aihe aihe = aiheet.findOne(kysymys.getAihe_id());
            map.put("kysymys", kysymys.getTeksti());
            map.put("kurssi", kurssi.getNimi());
            map.put("aihe", aihe.getNimi());
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
            Kysymys kys = new Kysymys(-1, k.getId(), a.getId(), req.queryParams("teksti"), -1);
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

    }

}
