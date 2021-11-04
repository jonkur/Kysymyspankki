package kysymyspankki.dao;

import java.sql.*;
import java.util.*;
import kysymyspankki.database.Database;
import kysymyspankki.domain.Kysymys;

/**
 *
 * @author jonkur
 */
public class KysymysDao implements Dao<Kysymys, Integer> {

    Database db;

    public KysymysDao(Database db) {
        this.db = db;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE ID=?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Kysymys k = new Kysymys(rs.getInt("id"), rs.getInt("kurssi_id"), rs.getInt("aihe_id"), rs.getString("teksti"));
            rs.close();
            stmt.close();
            conn.close();
            return k;
        }
        rs.close();
        stmt.close();
        conn.close();
        return null;
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {
        List<Kysymys> kysymykset = new ArrayList();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            kysymykset.add(new Kysymys(
                    rs.getInt("id"),
                    rs.getInt("kurssi_id"),
                    rs.getInt("aihe_id"),
                    rs.getString("teksti")
            ));
        }
        stmt.close();
        conn.close();
        return kysymykset;
    }

    @Override
    public Kysymys saveOrUpdate(Kysymys kys) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kurssi_id, aihe_id, teksti) VALUES (?, ?, ?)");
        stmt.setInt(1, kys.getKurssi_id());
        stmt.setInt(2, kys.getAihe_id());
        stmt.setString(3, kys.getTeksti());
        if (stmt.execute()) {
            stmt.close();
            conn.close();
            return kys;
        } else {
            stmt.close();
            conn.close();
            return null;
        }
    }

    @Override
    public boolean delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM KysymysVastaus WHERE kysymys_id=?");
        stmt.setInt(1, key);
        stmt.execute();
        stmt.close();
        stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id=?");
        stmt.setInt(1, key);
        if (stmt.execute()) {
            stmt.close();
            conn.close();
            return true;
        }
        stmt.close();
        conn.close();
        return false;
    }

    public boolean deleteAll() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys");
        stmt.execute();
        stmt.close();
        conn.close();
        return true;
    }

}
