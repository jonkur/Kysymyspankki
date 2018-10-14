
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
            return new Vastaus(rs.getInt("id"), rs.getString("teksti"));
        }
        return null;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        List<Vastaus> vastaukset = new ArrayList();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            vastaukset.add(new Vastaus(rs.getInt("id"), rs.getString("teksti")));
        }
        return vastaukset;
    }

    @Override
    public Vastaus saveOrUpdate(Vastaus v) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus (teksti) VALUES (?)");
        stmt.setString(1, v.getTeksti());
        if (stmt.execute()) {
            return v;
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
