package kysymyspankki.dao;

import java.sql.*;
import java.util.*;
import kysymyspankki.database.Database;
import kysymyspankki.domain.Vastaus;

/**
 *
 * @author jonkur
 */
public class VastausDao implements Dao<Vastaus, Integer> {

    Database db;

    public VastausDao(Database db) {
        this.db = db;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE ID=?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Vastaus v = new Vastaus(rs.getInt("id"), rs.getString("teksti"), rs.getBoolean("oikea"));
            rs.close();
            stmt.close();
            conn.close();
            return v;
        }
        rs.close();
        stmt.close();
        conn.close();
        return null;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        List<Vastaus> vastaukset = new ArrayList();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            vastaukset.add(new Vastaus(rs.getInt("id"), rs.getString("teksti"), rs.getBoolean("oikea")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return vastaukset;
    }

    public List<Vastaus> findAllWithKysymysId(Integer kurssi_id) throws SQLException {
        List<Vastaus> vastaukset = new ArrayList();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM Vastaus v INNER JOIN KysymysVastaus kv\n"
                + "ON v.id = kv.vastaus_id\n"
                + "WHERE kv.kysymys_id = ?"
        );
        stmt.setInt(1, kurssi_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            vastaukset.add(new Vastaus(rs.getInt("id"), rs.getString("teksti"), rs.getBoolean("oikea")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return vastaukset;
    }

    @Override
    public Vastaus saveOrUpdate(Vastaus v) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus (teksti, oikea) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, v.getTeksti());
        stmt.setBoolean(2, v.getOikea());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            Vastaus vastaus = new Vastaus((int) rs.getLong(1), v.getTeksti(), v.getOikea());
            rs.close();
            stmt.close();
            conn.close();
            return vastaus;
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
        PreparedStatement stmt = conn.prepareStatement("DELERE FROM Vastaus WHERE id=?");
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
        PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM Vastaus\n"
                + "WHERE (SELECT count(*) FROM KysymysVastaus WHERE vastaus_id = ?) = 0\n"
                + "AND id = ?"
        );
        stmt.setInt(1, key);
        stmt.setInt(2, key);
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
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus");
        stmt.execute();
        stmt.close();
        conn.close();
        return true;
    }

}
