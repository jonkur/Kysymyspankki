package kysymyspankki.dao;

import java.sql.*;
import java.util.List;
import kysymyspankki.database.Database;
import kysymyspankki.domain.KysymysVastaus;

/**
 *
 * @author jonkur
 */
public class KysymysVastausDao implements Dao<KysymysVastaus, Integer> {

    Database db;

    public KysymysVastausDao(Database db) {
        this.db = db;
    }

    @Override
    public KysymysVastaus findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KysymysVastaus> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public KysymysVastaus saveOrUpdate(KysymysVastaus obj) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO KysymysVastaus (kysymys_id, vastaus_id) VALUES (?,?)");
        stmt.setInt(1, obj.getKysymys_id());
        stmt.setInt(2, obj.getVastaus_id());
        if (stmt.execute()) {
            stmt.close();
            conn.close();
            return obj;
        } else {
            stmt.close();
            conn.close();
            return null;
        }
    }

    @Override
    public boolean delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean deleteByKysymysIdAndVastausId(Integer kysymys_id, Integer vastaus_id) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM KysymysVastaus WHERE kysymys_id = ? AND vastaus_id = ?");
        stmt.setInt(1, kysymys_id);
        stmt.setInt(2, vastaus_id);
        if (stmt.execute()) {
            stmt.close();
            conn.close();
            return true;
        }
        stmt.close();
        conn.close();
        return false;
    }

}
