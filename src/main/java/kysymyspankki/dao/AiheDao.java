package kysymyspankki.dao;

import java.sql.*;
import java.util.*;
import kysymyspankki.database.Database;
import kysymyspankki.domain.Aihe;

/**
 *
 * @author jonkur
 */
public class AiheDao implements Dao<Aihe, Integer> {

    Database db;

    public AiheDao(Database db) {
        this.db = db;
    }

    @Override
    public Aihe findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe WHERE ID=?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Aihe a = new Aihe(rs.getInt("id"), rs.getString("nimi"), rs.getInt("kurssi_id"));
            rs.close();
            stmt.close();
            conn.close();
            return a;
        }
        rs.close();
        stmt.close();
        conn.close();
        return null;
    }

    public Aihe findByName(String name) throws SQLException {
        List<Aihe> aiheet = findAll();
        if (aiheet.size() == 0) {
            return null;
        }
        return aiheet.stream().filter(a -> a.getNimi().equals(name)).findFirst().orElse(null);
    }

    @Override
    public List<Aihe> findAll() throws SQLException {
        List<Aihe> aiheet = new ArrayList();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            aiheet.add(new Aihe(rs.getInt("id"), rs.getString("nimi"), rs.getInt("kurssi_id")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return aiheet;
    }

    @Override
    public Aihe saveOrUpdate(Aihe a) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Aihe (nimi, kurssi_id) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, a.getNimi());
        stmt.setInt(2, a.getKurssi_id());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            Aihe aihe = new Aihe((int) rs.getLong(1), a.getNimi(), a.getKurssi_id());
            rs.close();
            stmt.close();
            conn.close();
            return aihe;
        } else {
            rs.close();
            stmt.close();
            conn.close();
            return null;
        }
    }

    @Override
    public boolean delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Aihe WHERE id=?");
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

    public boolean deleteIfUnused(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM Kysymys WHERE aihe_id=?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        int count = -1;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        stmt.close();
        conn.close();
        if (count == 0) {
            delete(key);
        }
        return false;
    }

    public boolean deleteAll() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Aihe");
        stmt.execute();
        stmt.close();
        conn.close();
        return true;
    }

}
