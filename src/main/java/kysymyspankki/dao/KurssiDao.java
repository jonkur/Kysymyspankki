package kysymyspankki.dao;

import java.sql.*;
import java.util.*;
import kysymyspankki.database.Database;
import kysymyspankki.domain.Kurssi;

/**
 *
 * @author jonask
 */
public class KurssiDao implements Dao<Kurssi, Integer> {

    Database db;

    public KurssiDao(Database db) {
        this.db = db;
    }

    @Override
    public Kurssi findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssi WHERE ID=?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Kurssi k = new Kurssi(rs.getInt("id"), rs.getString("nimi"), rs.getString("opettaja"));
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

    public Kurssi findByName(String name) throws SQLException {
        List<Kurssi> kurssit = findAll();
        if (kurssit.size() == 0) {
            return null;
        }
        return kurssit.stream().filter(k -> k.getNimi().equals(name)).findFirst().orElse(null);
    }

    @Override
    public List<Kurssi> findAll() throws SQLException {
        List<Kurssi> kurssit = new ArrayList();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssi");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            kurssit.add(new Kurssi(rs.getInt("id"), rs.getString("nimi"), rs.getString("opettaja")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return kurssit;
    }

    @Override
    public Kurssi saveOrUpdate(Kurssi k) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kurssi (nimi, opettaja) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, k.getNimi());
        stmt.setString(2, k.getOpettaja());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            Kurssi kurssi = new Kurssi((int) rs.getLong(1), k.getNimi(), k.getOpettaja());
            rs.close();
            stmt.close();
            conn.close();
            return kurssi;
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
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kurssi WHERE id=?");
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
        PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM Kysymys WHERE kurssi_id=?");
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
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kurssi");
        stmt.execute();
        stmt.close();
        conn.close();
        return true;
    }

}
